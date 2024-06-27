# ZenScript IntelliSense

## Kolasu Branch
This project is being refactored, using [Kolasu](https://github.com/Strumenta/kolasu)

## Quick Start
1. Install JDK 11+ (LTS recommended) and Node.js 20+ (LTS recommended).
2. Clone this project
   - `git clone -b kolasu https://github.com/raylras/zenscript-intelli-sense`
3. Build the server
   - `./gradlew dist`
4. Build the vscode extension
   - `cd vscode-extension`
   - `npm package`

## Useful commands
- Generate the ANTLR parser source
    - `./gradlew generateGrammarSource`
- Delete the ANTLR parser source
    - `./gradlew cleanGrammarSource`
- Generate the server jar files in the `vscode-extension/server` directory
    - `./gradlew dist`
- Delete the server jar files from the `vscode-extension/server` directory
    - `./gradlew cleanDist`

## License

[MIT](LICENSE) © raylras
