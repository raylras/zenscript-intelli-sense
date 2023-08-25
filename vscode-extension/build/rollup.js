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