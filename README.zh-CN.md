# ZenScript Language Server

一个给 ZenScript 语言提供 IDE 支持的 VS Code 插件

## 目录

- [构建](#构建)
- [开发](#开发)
- [调试](#调试)
  - [调试客户端插件](#调试客户端插件)
  - [调试语言服务器](#调试语言服务器)
  - [调试 TextMate 语法](#调试-textmate-语法)
- [额外部分](#额外部分)
- [参考](#参考)
- [如何贡献](#如何贡献)
- [使用许可](#使用许可)

## 构建

1. 安装 [JDK1.8](https://mirrors.tuna.tsinghua.edu.cn/Adoptium/8/jdk/) 和 [Node.js](https://nodejs.org/)

2. 克隆本项目

   ```shell
   git clone https://github.com/raylras/zenscript-language-server.git
   ```

3. 打开项目文件夹，终端执行以下命令构建语言服务器

   ```shell
   ./gradlew build
   ```
   > CMD 运行 `gradlew build`

4. 切换到插件目录

   ```shell
   cd vscode-extension
   ```

5. 安装插件开发环境

   ```shell
   npm install
   ```

6. 构建最终的插件文件，扩展名为`.vsix`

   ```shell
   npm run build
   ```
   >过程中可能会询问是否继续，输入`y`回车确认即可

## 开发

1. 工具
   - [IDEA](https://www.jetbrains.com/idea/) 服务端开发
   - [VS Code](https://code.visualstudio.com/) 插件端开发
   - [Node.js](https://nodejs.org/) 插件端开发环境
   - [JDK1.8](https://mirrors.tuna.tsinghua.edu.cn/Adoptium/8/jdk/) 服务端开发环境
   - [ANTLR v4](https://plugins.jetbrains.com/plugin/7358-antlr-v4) IDEA的插件，编写和调试 ANTLR4 的`.g4`语法文件

2. 克隆本项目

   ```shell
   git clone https://github.com/raylras/zenscript-language-server.git
   ```

3. 使用 IDE 打开项目目录`zenscript-language-server`

4. 运行 Gradle 任务`generateGrammarSource`生成cst包的代码

   ```shell
   ./gradlew generateGrammarSource
   ```
   > 每次修改`.g4`文件后都应该运行该任务重新生成java代码

## 调试

### 调试客户端插件

使用 VS Code 打开项目下的 `vscode-extension` 文件夹，点击 `运行`， `启动调试` 即可启动扩展开发宿主

> 在 VS Code 内打开`.zs`文件时才会激活本插件

### 调试语言服务器

在扩展开发宿主的设置里搜索 `ZenScript Language Server`，打开 `以调试模式运行语言服务器` 选项，扩展宿主重启后等待调试器连接。在`IDEA`，`运行`，`编辑配置`，`添加新配置` 创建一个 `远程 JVM 调试` 的新配置，名称可以为debug，选择该配置并点击调试按钮即可连接到调试实例

> 可在扩展开发宿主的`面板` ，`输出` ， `ZenScript Language Server` 通道上查看客户端插件和服务端的运行日志

### 调试 TextMate 语法

启用`VS Code`， `查看`， `命令面板`， `开发人员:检查编辑器标记和作用域` 选项即可调试作用域信息

## 额外部分

- TextMate 语法高亮基于正则匹配，其优先级比语言服务器要低且不需要语言服务器即可工作
    > `zenscript.tmLanguage.yaml` 为源文件，其对应的 json 文件不应该直接编辑，源文件修改后可以运行 npm 任务 `yaml2json` 生成
    > ```shell
    > npm run yaml2json
    > ```

## 参考

[A Practical Guide for Language Server Protocol](https://medium.com/ballerina-techblog/practical-guide-for-the-language-server-protocol-3091a122b750)

[language-server](https://github.com/lsp-and-implementation/language-server)

## 如何贡献

欢迎[提交Issue](https://github.com/raylras/zenscript-language-server/issues/new)或[提交PR](https://github.com/raylras/zenscript-language-server/pulls)

## 使用许可

[MIT](LICENSE) © raylras
