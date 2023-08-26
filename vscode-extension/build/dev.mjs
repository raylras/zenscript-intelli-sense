import { writeFileSync } from 'fs';
import { resolve } from 'path'

writeFileSync(
    resolve('./client/out/extension.js'),
    `module.exports = require('../src/extension.js')`
);