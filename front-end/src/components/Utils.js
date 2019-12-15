import { array } from "prop-types";
import { get } from "http";

function numberCompare(a, b, desc) {
    if (desc) {
        return a - b;
    } else {
        return b - a;
    }
}

function stringCompare(a, b, desc) {
    if (a > b) {
        return desc ? -1 : 1;
    } else if (a > b) {
        return desc ? 1 : -1;
    }
    return 0;
}

function dateCompare(a, b, desc) {
    const date1 = new Date(a);
    const date2 = new Date(b);
    if (desc) {
        return date1 - date2;
    } else {
        return date2 - date1;
    }
}

export function sortRecords(array, sortBy, desc) {
    return array.sort((r1, r2) => recordCompare(r1, r2, sortBy, desc))
}

function recordCompare(r1, r2, sortBy, desc) {
    const column1 = r1.columns.find(col => col.name === sortBy);
    const column2 = r2.columns.find(col => col.name === sortBy);

    const type = column1.type;

    switch (type) {
        case "text":
            return stringCompare(column1.value, column2.value, desc);
        case "number":
            return numberCompare(column1.value, column2.value, desc);
        case "date":
            return dateCompare(column1.value, column2.value, desc);
    }
}

export function getCellAlignment(type) {
    switch (type) {
        case "number":
            return "text-right";
        case "text":
            return "text-left";
        case "date":
            return "text-center";
        default:
            return ""
    }
}

export function getInputName(rowIndex, colName) {
    return colName + "-" + rowIndex;
}

export function getCurrentDay() {
    let today = new Date();
    let dd = String(today.getDate()).padStart(2, '0');
    let mm = String(today.getMonth() + 1).padStart(2, '0');
    let yyyy = today.getFullYear();

    today = mm + '.' + dd + '.' + yyyy;

    return today;
}

export function mapToHtmlType(prop) {

    if (prop.format) {
        switch (prop.format) {
            case 'uri':
                return 'uri';
            case 'date-time':
                return 'date'
            default:
                return 'text';
        }
    }

    switch (prop.type) {
        case 'string':
            return 'text';
        case 'integer':
            return 'number';
        default:
            return 'text';
    }
}

export function getCellTitle(record, field) {
    if (field.format === 'uri') {
        const value = record[camelize(field.name)];

        if (Array.isArray(value)) {

            return value.map(item => {
                const nameFields = guessNameFields(item);
                return getInfo(item, nameFields);
            })

        }

        const nameFields = guessNameFields(value);
        return getInfo(value, nameFields);
    }
    return record[camelize(field.name)];
}

function getInfo(value, fields) {
    let info = "";
    for (let i = 0; i < fields.length; i++) {
        info += fields[i];
        info += ": ";
        info += value[fields[i]];
        info += "\n";
    }

    return info;
}

function guessNameFields(value) {
    const nameFields = [];
    Object.keys(value)
        .forEach(key => {
            if (key.toLowerCase().includes('name')) {
                nameFields.push(key);
            }
        })
    return nameFields;
}

export function isExpandable(prop) {
    if (prop.format && prop.format === 'uri') {
        return true;
    }
    return false;
}

function camelize(str) {
    return str.replace(/(?:^\w|[A-Z]|\b\w|\s+)/g, function (match, index) {
        if (+match === 0) return ""; // or if (/\s+/.test(match)) for white spaces
        return index == 0 ? match.toLowerCase() : match.toUpperCase();
    });
}

export function catalogMetadataToTableHeading(response) {

    const properties = response.properties;

    return Object.keys(properties).map(value => ({
        name: properties[value].title,
        type: mapToHtmlType(properties[value]),
        format: properties[value].format,
    }))
}

export function catalogContentToList(response, catalogName) {
    return response._embedded[catalogName];
}

export function catalogContentToTableRows(response, catalogName, columnNames) {

    const catalogContent = response._embedded[catalogName];

    return catalogContent.map((record, index) => {
        const columns = columnNames.map(field => ({
            name: field.name,
            initialValue: getCellTitle(record, field),
            value: getCellTitle(record, field),
            type: field.type,
            isEditing: false
        }))
        return {
            index: index,
            id: record.id,
            modified: false,
            deleted: false,
            created: false,
            columns: columns
        }
    })
}

export function tableToRequestBody(records) {
    return records.map(record => singleRowToRequestBody(record));
}

export function singleRowToRequestBody(record) {
    const content = record.columns.reduce(
        (obj, item) => {
            const key = camelize(item.name);
            const value = item.value;
            const type = item.type;
            if (type === 'uri') {
                return {
                    ...obj,
                    [key]: null
                }
            }
            return {
                ...obj,
                [key]: value
            }
        },
        {}
    );
    return {
        content: {
            ...content,
            id: record.id
        },
        deleted: record.deleted,
        created: record.created,
        modified: record.modified
    }
}

export function postSubmit(records) {

    return records.map(record => {
        record.columns.forEach(column => column.initialValue = column.value);
        return {
            ...record,
            modified: false,
            created: false,
        };
    }).filter(record => !record.deleted);

}

export function isArrayEmpty(array) {
    if (typeof array === 'undefined') {
        return true;
    }
    if (typeof array !== 'undefined' && array.length === 0) {
        return true;
    }
    return false;
}