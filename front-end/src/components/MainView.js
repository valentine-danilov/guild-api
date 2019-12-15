import React from 'react'
import { fetchCatalogList } from './Fetch'
import SelectCatalogView from './SelectCatalogView'
import { Route, Redirect, Switch } from 'react-router-dom'
import TableView from './TableView';
import { array } from 'prop-types';
import { isArrayEmpty } from './Utils'

export default class Table extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            catalogs: undefined,
            selectedCatalog: null,
        }
    }

    async componentDidMount() {
        const catalogs = await fetchCatalogList();
        this.setState({
            catalogs: catalogs
        })
    }

    onCatalogSelect = async event => {

        event.persist();
        const value = event.target.value;

        if (value) {

            this.setState({
                selectedCatalog: value
            })

        } else {
            this.setState({
                selectedCatalog: null,
                columnNames: [],
                columnNumber: 0
            })
        }
    }

    getCatalogForName = () => {
        const currentUrl = window.location.href;
        if (!currentUrl.includes('catalog')) {
            return "";
        }
        const urlLevels = currentUrl.split('/');
        if (urlLevels[urlLevels.length - 1]) {
            return <Table catalogName={urlLevels[urlLevels.length - 1]} />;
        }
        return "";
    }


    render() {

        if (typeof this.state.catalogs === 'undefined') {
            return (
                <div className="table-wrapper" >
                    <h2 className="text-center">Looks like we were not able to load list of catalogs ;(</h2>
                </div>
            )
        }

        return (
            <div className="table-wrapper" >
                <Switch>
                    <Route exact path="/">
                        {
                            !this.state.selectedCatalog ?
                                <SelectCatalogView
                                    options={this.state.catalogs}
                                    onChange={this.onCatalogSelect} /> :
                                <Redirect to={"/catalog/" + this.state.selectedCatalog} />
                        }
                    </Route>
                    {
                        this.state.catalogs ?
                            this.state.catalogs.map(value => {
                                return (
                                    <Route path={"/catalog/" + value}>
                                        <TableView catalogName={value} />
                                    </Route>
                                )
                            })
                            : this.getCatalogForName()
                    }
                    <Route>
                        <h1 catalogName="text-center">404 Not Found</h1>
                        <h2 className="text-center">Sorry, we were not able to identify this page :(</h2>
                    </Route>
                </Switch>

            </div>
        )

    }
}