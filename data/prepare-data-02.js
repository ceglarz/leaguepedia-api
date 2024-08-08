//  stage 2 - create factions.json

const shortDataJson = require('./shortData.json');
const factionsJsonFs = require('fs');
const factionsJsonArray = [];


for (const faction of shortDataJson.factions) {
    var fetchResponse = fetch('https://universe-meeps.leagueoflegends.com/v1/en_pl/factions/' + faction.slug + '/index.json')
        .then((response) => response.json())
        .then((data) => {
            return data.faction
        })
        .catch(console.error);

    var fetchResponsePl = fetch('https://universe-meeps.leagueoflegends.com/v1/pl_pl/factions/' + faction.slug + '/index.json')
        .then((response) => response.json())
        .then((data) => {
            return data.faction
        })
        .catch(console.error);


    const factionObject = Promise.all([fetchResponse, fetchResponsePl])
        .then((values) => {
                var fetchResponseValue = values[0];
                var fetchResponsePlValue = values[1];

                const uuid = crypto.randomUUID();

                return {
                    uuid: uuid,
                    slug: fetchResponseValue.slug,
                    name: fetchResponseValue.name,
                    overview: fetchResponseValue.overview.short,
                    imageUri: fetchResponseValue.image.uri,
                    namePl: fetchResponsePlValue.name,
                    overviewPl: fetchResponsePlValue.overview.short
                };
            }
        );

    factionsJsonArray.push(factionObject);

}

Promise.all(factionsJsonArray).then((values) => {
    console.log(values);
    factionsJsonFs.writeFile('./factions.json', JSON.stringify(values), (err) => {
        if (err) {
            throw new Error('Something went wrong.')
        }
        console.log('JSON written to file factions.json');
    })
});
