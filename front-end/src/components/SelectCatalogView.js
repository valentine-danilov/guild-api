import React from 'react'

const SelectCatalogView = ({ options, onChange }) => (

    <div className="container custom-select">
        <h2 className="text-center">Select catalog to work with:</h2>
        <select onChange={(event) => onChange(event)} className="form-control">
            <option selected disabled>...</option>
            {
                options.map(value => (
                    <option>
                        {value}
                    </option>
                ))
            }
        </select>
    </div>
);

export default SelectCatalogView;