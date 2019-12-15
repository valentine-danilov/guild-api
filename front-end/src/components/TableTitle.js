import React from 'react'
import { Link } from 'react-router-dom'

const TableTitle = ({ catalogName, onSubmit, onAddNewRow, returnToSelection }) => (
    <div className="table-title">
        <div className="row">
            <div className="col-sm-6">
                <div className="row">
                    <div className="col-sm-12">
                        <h2>Catalog: <b>{catalogName}</b></h2>
                    </div>
                </div>

            </div>
            <div className="col-sm-6 ">
                <div className="row">
                    <div className="col-sm-12 text-right">
                        <button
                            onClick={() => onAddNewRow()}
                            type="button"
                            className="btn btn-info add-new">
                            <i className="fa fa-plus"></i>
                            Add New </button>
                    </div>
                </div>
                <div className="row">
                    <div className="col-sm-12">
                        <button
                            onClick={() => onSubmit()}
                            type="button"
                            className="btn btn-info add-new">
                            <i class="fas fa-upload"></i>
                            Submit catalog </button>
                    </div>
                </div>
                <div className="row">
                    <div className="col-sm-12">
                        <Link to="/" className="btn btn-info add-new" >
                            <i class="fas fa-list"></i>
                            Select another catalog
                        </Link>
                    </div>
                </div>
            </div>
        </div>
    </div>
);

export default TableTitle;