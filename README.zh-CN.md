# ZenScript Language Server

一个给 ZenScript 语言提供 IDE 支持的 VS Code 插件

## 目录

- [插件运行要求](#插件运行要求)
- [构建](#构建)
- [开发](#开发)
- [调试](#调试)
- [额外部分](#额外部分)
- [参考](#参考)
- [如何贡献](#如何贡献)
- [使用许可](#使用许可)

## 插件运行要求

系统需要有 Java 8+ 环境。

## 构建

1. 安装 JDK 11+ 和 Node.js 18.14.*+

2. 克隆本项目

   - `git clone https://github.com/raylras/zenscript-language-server.git`

3. 打开项目根目录，构建server，扩展名为`.jar`

   - `./gradlew build`

4. 构建 VS Code 插件，扩展名为`.vsix`

   - `cd vscode-extension`
   - `npm install`
   - `npm run package`

## 开发

  - IDEA 需要安装 [ANTLR v4](https://plugins.jetbrains.com/plugin/7358-antlr-v4) 插件才能有`*.g4`文件支持
  - 本插件为了能兼容 Java 8 环境，server的语言级需要设置为 Java 8
  - 修改`.g4`文件后，需要运行 Gradle 任务 `./gradlew generateGrammarSource` 生成parser包的代码

## 调试
插件
  - 使用 VS Code 打开项目下的 `vscode-extension` 目录，点击 `运行`， `启动调试` 启动一个插件调试实例

语言服务器
  - 打开插件调试实例的设置，找到扩展 `ZenScript Language Server`，打开 `以调试模式运行语言服务器` 选项，重启调试实例
  - 在 `IDEA` 的 `运行/调试配置` 里添加创建一个远程 JVM 调试
  - 在插件实例的 `面板` ，`输出` ， `ZenScript Language Server` 上查看日志

TextMate 语法结构
  - 启用 `VS Code`， `查看`， `命令面板`， `开发人员:检查编辑器标记和作用域` 选项
  - 修改 `*.tmLanguage.yaml` 后，运行 `npm run yaml2json` 生成对应的 json 文件

## 额外部分

- 如果IDEA打开`*.g4`后显示很多错误，右键`*.g4`, `Configure ANTLR...`, `Location of imported grammars`，在项目下`build/generated-src/`里找到有`*.tokens`文件的目录，重新打开`*.g4`即可
- 运行Gradle任务`./gradlew generateGrammarSource` 可以重新生成这些文件。

## 参考

[A Practical Guide for Language Server Protocol](https://medium.com/ballerina-techblog/practical-guide-for-the-language-server-protocol-3091a122b750)

[language-server](https://github.com/lsp-and-implementation/language-server)

## 如何贡献

欢迎[提交Issue](https://github.com/raylras/zenscript-language-server/issues/new)
或[提交PR](https://github.com/raylras/zenscript-language-server/pulls)

## 使用许可

[MIT](LICENSE) © raylras
