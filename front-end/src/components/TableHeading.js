import React from 'react';

const TableHeading = (props) => (
    <thead>
        <tr>
            <th>
                <p>#</p>
            </th>
            {
                props.headings ? props.headings.map((value, i) =>
                    <th key={i}>
                        <p>{value.name}</p>
                    </th>) : ""
            }
            <th>
                <p>Action</p>
            </th>
        </tr>
    </thead>
);

export default TableHeading;