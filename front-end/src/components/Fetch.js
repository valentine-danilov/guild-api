import { mapToHtmlType } from './Utils'

export async function fetchCatalogList() {
    const response = await fetch("http://localhost:8080/profile", {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        }
    });

    if (response.status === 200) {

        const json = await response.json();

        const values = Object.keys(json._links);
        const catalogs = values.filter(value => value !== 'self');

        console.log("Retrieved catalog names: " + catalogs);

        return catalogs;
    }

    return [];
}

export async function fetchCatalogMetadata(catalogName) {

    const endpoint = "http://localhost:8080/profile/" + catalogName;
    const response = await fetch(endpoint, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            "Accept": 'application/schema+json'
        }
    });

    const json = await response.json();

    if (response.status === 200) {
        const properties = json.properties;

        const catalogHeadings = Object.keys(properties).map(value => ({
            name: properties[value].title,
            type: mapToHtmlType(properties[value].type)
        }))

        return catalogHeadings;
    }
    return [];
}