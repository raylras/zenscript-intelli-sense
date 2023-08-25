# ZenScript Language Server

一个 ZenScript 的语言服务器（LSP）实现，可以在 VS Code 上提供代码高亮、代码补全等功能

## 目录

- [插件运行要求](#插件运行要求)
- [开发](#构建)
- [调试](#调试)
- [额外部分](#额外部分)
- [使用许可](#使用许可)

## 插件运行要求

系统需要有 Java 17+ 环境。

## 构建

1. 安装 JDK 17 (LTS) 和 Node.js 18 (LTS)
2. 克隆本项目
   - `git clone https://github.com/raylras/zenscript-language-server.git`
3. 构建 language server
   - `cd zenscript-language-server`
   - `./gradlew dist`
4. 构建 vscode extension
   - `cd vscode-extension`
   - `npm install`
   - `npm run release`

## 调试
   - 调试 VS Code 插件
     - 使用 VS Code 打开 `vscode-extension` 目录，点击 `运行`， `启动调试`

   - 调试语言服务器
     - 打开 VS Code 设置，找到扩展 `ZenScript Language Server`，打开 `以调试模式运行语言服务器` 选项，重启调试实例
     - 在 `IDEA` 的 `运行/调试配置`， `远程 JVM 调试` 创建一个新的运行配置
     - 在插件实例的 `面板` ，`输出` ， `ZenScript Language Server` 上查看日志
   - 调试 TextMate 语法
     - 启用 `VS Code`， `查看`， `命令面板`， `开发人员:检查编辑器标记和作用域` 选项

## 额外部分
   - IDEA 安装 [ANTLR v4](https://plugins.jetbrains.com/plugin/7358-antlr-v4) 以提供`*.g4`文件支持
   - 本插件为了能兼容 Java 8 环境，Java 语言级别需要设置为 8
   - 修改`*.g4`文件后，需要运行 Gradle 任务 `./gradlew generateGrammarSource` 重新生成 parser 的代码
   - 修改 `*.tmLanguage.yaml` 后，运行 `npm run yaml2json` 重新生成对应的 json 文件
   - 如果IDEA打开`*.g4`后显示很多错误，右键`*.g4`, `Configure ANTLR...`, `Location of imported grammars`，在项目下`build/generated-src/`里找到有`*.tokens`文件的目录，重新打开`*.g4`即可
     - 运行Gradle任务`./gradlew generateGrammarSource` 可以重新生成这些文件。

欢迎[提交Issue](https://github.com/raylras/zenscript-language-server/issues/new)
或[提交PR](https://github.com/raylras/zenscript-language-server/pulls)

## 使用许可

[MIT](LICENSE) © raylras
