import nodeResolve from '@rollup/plugin-node-resolve';
import commonjs from '@rollup/plugin-commonjs';
import terser from '@rollup/plugin-terser';

export default {
    input: 'client/src/extension.js',
    output: {
        file: 'client/out/extension.js',
        format: 'cjs'
    },
    plugins: [
        nodeResolve(),
        commonjs(),
        terser()
    ],
    external: ['vscode']
};
