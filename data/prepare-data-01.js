// stage 1 - create shortData.json

fetch('https://universe-meeps.leagueoflegends.com/v1/en_pl/search/index.json')
    .then((response) => response.json())
    .then((data) => {

        const fs = require('fs');
        fs.writeFile('./shortData.json', JSON.stringify(data), (err) => {
            if (err) {
                throw new Error('Something went wrong.')
            }
            console.log('JSON written to file shortData.json');
        })


        // for (const product of data.products) {
        // }
    })
    .catch(console.error);
