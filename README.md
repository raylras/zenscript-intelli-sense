# ZenScript IntelliSense

## Quick Start

1. Install JDK 11+ (JDK 21 LTS recommended) and Node.js (Node.js 20 LTS recommended).
2. Clone this project
    - `git clone https://github.com/raylras/zenscript-intelli-sense`
3. Build the language server
    - `cd zenscript-intelli-sense`
    - `./gradlew dist`
4. Build the VSCode extension
    - `cd vscode-extension`
    - `npm install`
    - `npm run package`

## Development

- Install VSCode, IntelliJ IDEA
> It is recommended to install the IntelliJ IDEA plugin [ANTLR v4](https://plugins.jetbrains.com/plugin/7358-antlr-v4).
- Debugging the language server
  1. Open VSCode settings, find `ZenScript`, check the `Enable Java Arguments` option, and restart VSCode.
  2. Create a `Remote JVM Debug` configuration in `IntelliJ IDEA`.
    > Check the logs in the VSCode `Panel`, `Output`, `ZenScript Language Server`.
- Debugging the VSCode extension
  1. Use VSCode to open the `vscode-extension` directory,
  2. click `Run`, `Start Debugging`, or press the shortcut key `F5`.
- Debugging TextMate syntax
  1. Enable `VSCode`, `View`, `Command Palette`, `Developer: Inspect Editor Tokens and Scopes` option.

## License

[MIT](LICENSE) © raylras
