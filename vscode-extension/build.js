const env = process.env.NODE_ENV

if (env === 'development') {
  require('fs').writeFileSync(
    require('path').resolve(__dirname, './client/out/main.js'),
    `const { activate, deactivate } = require('../src/extension.js'); module.exports = { activate, deactivate }`
  )
} else {
  require('rollup')
    .rollup({
      input: './client/src/extension.js',
      plugins: [
        require('@rollup/plugin-node-resolve').nodeResolve(),
        require('@rollup/plugin-commonjs')(),
        require('@rollup/plugin-terser')()
      ],
      external: ['vscode']
    })
    .then((bundle) => {
      bundle.write({
        file: './client/out/main.js',
        format: 'cjs'
      })
    })
}
