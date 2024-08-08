//  stage 3 - create champions.json

// const shortDataJson = require('./shortData.json');
//
// const championsJsonFs = require('fs');
// const championsJsonArray = [];
//
// // for (const champion of shortDataJson.champions) {
// for (let i = 0; i < 168; i++) {
//     const champion = shortDataJson.champions[i];
//
//     var fetchResponse = fetch('https://universe-meeps.leagueoflegends.com/v1/en_pl/champions/' + champion.slug + '/index.json')
//         .then((response) => response.json())
//         .then((data) => {
//             return data.champion
//         })
//         .catch(console.error);
//
//     var fetchResponsePl = fetch('https://universe-meeps.leagueoflegends.com/v1/pl_pl/champions/' + champion.slug + '/index.json')
//         .then((response) => response.json())
//         .then((data) => {
//             return data.champion
//         })
//         .catch(console.error);
//
//
//     const championObject = Promise.all([fetchResponse, fetchResponsePl])
//         .then((values) => {
//                 var fetchResponseValue = values[0];
//                 var fetchResponsePlValue = values[1];
//                 return {
//                     uuid: crypto.randomUUID(),
//                     slug: fetchResponseValue.slug,
//                     name: fetchResponseValue.name,
//                     title: fetchResponseValue.title,
//                     biographyFull: fetchResponseValue.biography.full,
//                     biographyShort: fetchResponseValue.biography.short,
//                     quote: fetchResponseValue.biography.quote,
//                     imageUri: fetchResponseValue.image.uri,
//                     associatedFactionSlug: fetchResponseValue["associated-faction-slug"],
//                     titlePl: fetchResponsePlValue.title,
//                     biographyFullPl: fetchResponsePlValue.biography.full,
//                     biographyShortPl: fetchResponsePlValue.biography.short,
//                     quotePl: fetchResponsePlValue.biography.quote,
//                     roles: fetchResponsePlValue.roles
//                 };
//             }
//         );
//
//     championsJsonArray.push(championObject);
//
// }
//
// Promise.all(championsJsonArray).then((values) => {
//     console.log(values);
//     championsJsonFs.writeFile('./champions.json', JSON.stringify(values), (err) => {
//         if (err) {
//             throw new Error('Something went wrong.')
//         }
//         console.log('JSON written to file champions.json');
//     })
// });

