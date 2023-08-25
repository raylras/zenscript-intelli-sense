require('fs').writeFileSync(
    require('path').resolve(__dirname, '../client/out/main.js'),
    `module.exports = require('../src/extension.js')`
)