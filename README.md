# ZenScript IntelliSense

## PSI Branch
This project is being refactored using stand-alone [IntelliJ PSI](https://central.sonatype.com/artifact/org.aya-prover.upstream/ij-parsing-core) publishing by [Aya](https://github.com/aya-prover/aya-dev).

## Quick Start
1. Install JDK 17+ (LTS recommended) and Node.js 20+ (LTS recommended).
2. Clone this project
   - `git clone -b psi https://github.com/raylras/zenscript-intelli-sense`
3. Build the server
   - `./gradlew dist`
4. Build the vscode extension
   - `cd vscode-extension`
   - `npm package`

## Useful commands
- Generate the server jar files in the `vscode-extension/server` directory
    - `./gradlew dist`
- Delete the server jar files from the `vscode-extension/server` directory
    - `./gradlew cleanDist`

## License

[MIT](LICENSE) © raylras
