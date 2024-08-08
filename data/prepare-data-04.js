//  stage 4 - create sql init files: factions, champions

var factionsArray = [];
factionsArray.push({
        slug: 'unaffiliated',
        uuid: null
    }
);

function sqlReplace(str) {
    return str.replace("''", "'").replace("’", `''`).replace(/'/g, "''").replace("''''", "''");//  .replace("''", `'`) 1     .replace("'", `''`) 2
}

// factions

const factionsJson = require('./factions.json');
const factionsInitFs = require('fs');
var factionsInit = '';

for (const faction of factionsJson) {
    factionsArray.push({
            slug: faction.slug,
            uuid: faction.uuid
        }
    );
    const insert = `INSERT INTO faction (id, "name", name_pl, overview, overview_pl, slug, image_uri)
                    VALUES ('${faction.uuid}', '${faction.name}', '${faction.namePl}', '${faction.overview}',
                            '${faction.overviewPl}', '${faction.slug}', '${faction.imageUri}');`;
    factionsInit += insert;
    factionsInit += '\n';

}


factionsInitFs.writeFile('./init-factions.sql', factionsInit, (err) => {
    if (err) {
        throw new Error('Something went wrong.')
    }
    console.log('SQL written to file init-factions.sql');
})

// champions

const championsJson = require('./champions.json');
const championsInitFs = require('fs');
var championsInit = '';

for (const champion of championsJson) {
    const faction = factionsArray.find((faction) => faction.slug === champion.associatedFactionSlug);
    var insert;
    if (faction.slug === 'unaffiliated') {
        insert = `INSERT INTO champion (id, biography_full, biography_full_pl, biography_short,
                                        biography_short_pl, "name", "quote", quote_pl, slug, title, title_pl, image_uri,
                                        faction_id)
                  VALUES ('${champion.uuid}', '${sqlReplace(champion.biographyFull)}',
                          '${sqlReplace(champion.biographyFullPl)}',
                          '${sqlReplace(champion.biographyShort)}', '${sqlReplace(champion.biographyShortPl)}',
                          '${sqlReplace(champion.name)}',
                          '${sqlReplace(champion.quote)}', '${sqlReplace(champion.quotePl)}',
                          '${sqlReplace(champion.slug)}', '${sqlReplace(champion.title)}',
                          '${sqlReplace(champion.titlePl)}', '${champion.imageUri}',
                          null);`;
    } else {
        insert = `INSERT INTO champion (id, biography_full, biography_full_pl, biography_short, biography_short_pl,
                                        "name", "quote", quote_pl, slug, title, title_pl, image_uri, faction_id)
                  VALUES ('${champion.uuid}', '${sqlReplace(champion.biographyFull)}',
                          '${sqlReplace(champion.biographyFullPl)}',
                          '${sqlReplace(champion.biographyShort)}', '${sqlReplace(champion.biographyShortPl)}',
                          '${sqlReplace(champion.name)}',
                          '${sqlReplace(champion.quote)}', '${sqlReplace(champion.quotePl)}',
                          '${sqlReplace(champion.slug)}', '${sqlReplace(champion.title)}',
                          '${sqlReplace(champion.titlePl)}', '${champion.imageUri}',
                          '${faction.uuid}');`;
    }

    championsInit += insert;
    championsInit += '\n';

}


championsInitFs.writeFile('./init-champions.sql', championsInit, (err) => {
    if (err) {
        throw new Error('Something went wrong.')
    }
    console.log('SQL written to file init-champions.sql');
})







// save to JSON

// const factionsJson = require('./factions.json');
// const factionsInitFs = require('fs');
//
// for (const faction of factionsJson) {
//     factionsArray.push({
//             slug: faction.slug,
//             uuid: faction.uuid
//         }
//     );
// }
//
// factionsInitFs.writeFile('./init-factions.json', JSON.stringify(factionsJson), (err) => {
//     if (err) {
//         throw new Error('Something went wrong.')
//     }
//     console.log('JSON written to file init-factions.json');
// })
//
// const championsJson = require('./champions.json');
// const championsInitFs = require('fs');
//
// for (const champion of championsJson) {
//     const faction = factionsArray.find((faction) => faction.slug === champion.associatedFactionSlug);
//     champion.factionId = faction.uuid;
// }
//
// championsInitFs.writeFile('./init-champions.json', JSON.stringify(championsJson), (err) => {
//     if (err) {
//         throw new Error('Something went wrong.')
//     }
//     console.log('JSON written to file init-champions.json');
// })


// save to CSV

// function jsonToCsv(jsonData) {
//     let csv = '';
//
//     // Extract headers
//     const headers = Object.keys(jsonData[0]);
//     csv += headers.join(',') + '\n';
//
//     // Extract values
//     jsonData.forEach(obj => {
//         const values = headers.map(header => obj[header]);
//         csv += values.join(',') + '\n';
//     });
//
//     return csv;
// }
//
// const factionsJson = require('./factions.json');
// const factionsCsvFs = require('fs');
//
// for (const faction of factionsJson) {
//     factionsArray.push({
//             slug: faction.slug,
//             uuid: faction.uuid
//         }
//     );
// }
//
// const factionsCsvContent = jsonToCsv(factionsJson);
//
// factionsCsvFs.writeFile('./factions.csv', factionsCsvContent, (err) => {
//     if (err) {
//         throw new Error('Something went wrong.')
//     }
//     console.log('CSV written to file factions.csv');
// })
//
// //
//
// const championsJson = require('./champions.json');
// const championsCsvFs = require('fs');
// let championsConvertedJson = [];
//
// for (const champion of championsJson) {
//     const faction = factionsArray.find((faction) => faction.slug === champion.associatedFactionSlug);
//     champion.factionId = faction.uuid;
//     // championsConvertedJson.push();
// }
//
// const championsCsvContent = jsonToCsv(championsJson);
//
// championsCsvFs.writeFile('./champions.csv', championsCsvContent, (err) => {
//     if (err) {
//         throw new Error('Something went wrong.')
//     }
//     console.log('CSV written to file champions.csv');
// })