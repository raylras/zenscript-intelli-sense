# ZenScript IntelliSense
![GitHub License](https://img.shields.io/github/license/raylras/zenscript-intelli-sense)
![GitHub repo size](https://img.shields.io/github/repo-size/raylras/zenscript-intelli-sense)
![Static Badge](https://img.shields.io/badge/demo-native_image-orange)

## Native image branch
Technical testing of GraalVM native image.  
_NOTICE_: `proxy-config.json`, `reflect-config.json`, `resource-config.json` is machine generated, see [Tracing Agent](https://www.graalvm.org/latest/reference-manual/native-image/metadata/AutomaticMetadataCollection/#tracing-agent).

## Quick Start

1. Install GraalVM 22, Visual Studio 2022 Build Tools, and Node.js 20
2. Clone this project
    - `git clone -b native-image https://github.com/raylras/zenscript-intelli-sense`
3. Build the language server
    - `cd zenscript-intelli-sense`
    - `./gradlew distNative`
4. Build the VSCode extension
    - `cd vscode-extension`
    - `npm install`
    - `npm run package`
