import React from 'react'
import TableRow from './TableRow'
import TableHeading from './TableHeading'
import TableTitle from './TableTitle'
import {
    getInputName,
    getCellAlignment,
    catalogMetadataToTableHeading,
    catalogContentToTableRows,
    tableToRequestBody,
    postSubmit
} from './Utils'

export default class TableView extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            records: [],
            headings: [],
            columnNames: null,
            columnNumber: null,
            sortDesc: true
        }

    }

    async componentDidMount() {
        await this.loadCatalog(this.props.catalogName);
    }

    addEmptyRow = () => {

        let records = this.state.records;

        const columns = this.state.columnNames.map(heading => {

            const column = {
                name: heading.name,
                value: null,
                isEditable: heading.isEditable,
                type: heading.type,
                isEditing: false
            }
            return column;
        })

        const record = {
            index: records.length,
            deleted: false,
            modified: false,
            created: true,
            columns: columns
        }

        records.push(record);
        this.setState({
            records: records,
        });
    }


    loadMetadata = async catalogName => {

        const endpoint = "http://10.160.44.215:8080/profile/" + catalogName;
        const response = await fetch(endpoint, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                "Accept": 'application/schema+json'
            }
        });

        const json = await response.json();

        if (response.status === 200) {


            const catalogHeadings = catalogMetadataToTableHeading(json);

            this.setState({
                columnNames: catalogHeadings,
                columnNumber: catalogHeadings.length
            })

            return catalogHeadings;
        }

        return null;
    }

    loadCatalog = async catalogName => {

        const columnNames = await this.loadMetadata(catalogName);

        if (!columnNames) {
            alert("Unable to load catalog metadata ;(")
            return;
        }

        const catalogEndpoint = "http://10.160.44.215:8080/" + catalogName + "?projection=withId";

        const catalogResponse = await fetch(catalogEndpoint, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (catalogResponse.status === 200) {

            const catalogJson = await catalogResponse.json();

            const records = catalogContentToTableRows(
                catalogJson,
                catalogName,
                columnNames
            );

            this.setState({
                records: records
            })
        }
    }

    deleteRow = index => {
        let records = this.state.records;

        records[index].deleted = true;

        this.setState({
            records: records
        })

    }

    onCatalogSubmit = async () => {
        const body = tableToRequestBody(this.state.records);
        const json = JSON.stringify(body);
        console.log(json)
        const submitEndpoint = "http://10.160.44.215:8080/catalog/" + this.props.catalogName + "/submit";

        const submitResponse = await fetch(submitEndpoint, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(body)
        });

        if (submitResponse.status === 200) {
            alert("Hooray! Catalog was succesfully submitted")
            this.setState({
                records: postSubmit(this.state.records)
            })
        } else {
            alert("Smth has gone wrong while submitting catalog ;(")
        }
    }

    returnToSelection = () => {
        this.setState({
            selectedCatalog: null
        })
    }

    renderCell = (rowIndex, colName, deleted) => {

        const record = this.state.records[rowIndex];

        const column = record.columns.find(col => col.name === colName);
        const isEditing = column.isEditing;

        if (deleted) {
            return (
                <td className="deleted">
                    <p className={getCellAlignment(column.type) + " " + "text-ellipsis"} >{column.value}</p>
                </td>
            );
        }

        if (!isEditing) {
            return (
                <td onClick={() => this.onCellClick(rowIndex, colName)}>
                    <p className={getCellAlignment(column.type) + " " + "text-ellipsis"} >{column.value}</p>
                </td>
            );
        } else {
            return (
                <td>
                    <input
                        className="form-control"
                        onBlur={this.onCellBlur.bind(this)}
                        onKeyPress={this.onEnterPressed.bind(this)}
                        name={getInputName(rowIndex, colName)}
                        type={column.type}
                        defaultValue={column.value}
                        autoFocus
                    />
                </td>
            );
        }
    }

    /* --- Handle cell events --- */
    onCellBlur = event => {
        this.handleTableValueChange(event);
    }

    onEnterPressed = event => {
        if (event.key === "Enter") {
            this.handleTableValueChange(event);
        }
    }

    onCellClick = (rowIndex, colName) => {

        let records = this.state.records;

        const record = records[rowIndex];
        const column = record.columns.findIndex(col => col.name === colName);
        const isEditing = record.columns[column].isEditing;

        if (!isEditing) {
            record.columns[column].isEditing = true;
            records[rowIndex] = record;
        }

        this.setState({
            records: records,
        })
    }

    handleTableValueChange = event => {
        event.preventDefault();
        const inputNameSplit = event.target.name.split("-");
        const rowIndex = inputNameSplit[1];
        const colName = inputNameSplit[0];

        let records = this.state.records;

        const record = records[rowIndex];
        const column = record.columns.findIndex(col => col.name === colName);
        record.columns[column].isEditing = false;

        const newValue = event.target.value;
        const initialValue = record.columns[column].initialValue;

        if (newValue !== initialValue) {
            record.columns[column].value = newValue;
            if (!record.created) {
                record.modified = true;
            }
            records[rowIndex] = record;
            this.setState({
                records: records
            })
        }
    }

    revertRow = index => {
        let records = this.state.records;
        records[index].deleted = false;
        records[index].columns.forEach(column => column.value = column.initialValue);
        records[index].modified = false;
        this.setState({
            records: records
        })
    }

    render() {
        return (
            <div>
                <TableTitle
                    catalogName={this.props.catalogName}
                    onSubmit={this.onCatalogSubmit}
                    onAddNewRow={this.addEmptyRow}
                />
                <table className="table table-bordered">
                    <TableHeading
                        headings={this.state.columnNames} />
                    <tbody>

                        {

                            this.state.records.map((row, i) =>
                                <TableRow
                                    key={row.index}
                                    deleteRow={this.deleteRow}
                                    renderCell={this.renderCell}
                                    record={row}
                                    deleted={row.deleted}
                                    revert={this.revertRow} />
                            )

                        }

                    </tbody>
                </table>
            </div>
        )
    }

}