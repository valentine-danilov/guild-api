import React from 'react'
import TableCell from './TableCell'

const TableRow = (props) => {
    const record = props.record;
    return (

        <tr>
            <td className={props.deleted ? "deleted" : ""}>
                {record.index + 1}
            </td>
            {
                record.columns.map((col, i) =>
                    <TableCell
                        key={i}
                        rowIndex={record.index}
                        columnName={col.name}
                        render={props.renderCell}
                        deleted={props.deleted}
                    />
                )
            }

            <td>
                <a onClick={() => props.deleteRow(props.record.index)} className="delete" title="Delete" data-toggle="tooltip"><i className="material-icons">&#xE872;</i></a>
                <a onClick={() => props.revert(props.record.index)} title="Reset" data-toggle="tooltip"><i className="material-icons">undo</i></a>
            </td>
        </tr>
    )
}

export default TableRow;