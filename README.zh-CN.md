## 快速构建

- 安装 [JDK17](https://mirrors.tuna.tsinghua.edu.cn/Adoptium/17/jdk/) 和 [Node.js](https://nodejs.org/)
- 克隆本项目`git clone https://github.com/raylras/zenscript-language-server.git`
- 打开项目文件夹，命令行运行`./gradlew build`构建语言服务器（CMD为`gradlew build`）
- 切换到插件目录`cd vscode-extension`
- 运行`npm install`安装插件开发环境
- 运行`npm run build`构建最终的插件文件，扩展名为`.vsix`

## 开发环境

[IDEA](https://www.jetbrains.com/idea/)

[VS Code](https://code.visualstudio.com/)

[Node.js](https://nodejs.org/)

[JDK17](https://mirrors.tuna.tsinghua.edu.cn/Adoptium/17/jdk/)

## 调试

#### 调试客户端插件

使用 VS Code 打开项目下的 `vscode-extension` 文件夹，点击 `运行`， `启动调试` 即可启动扩展开发宿主

- 你需要在 VS Code 内打开`.zs`文件才会激活本插件

#### 调试语言服务器

启动扩展开发宿主，在其设置里搜索 `ZenScript Language Server`，打开 `以调试模式运行语言服务器` 选项，重启扩展宿主后其将等待调试器连接。你需要在`IDEA`， `运行`，`编辑配置`，`添加新配置` 创建一个 `远程 JVM 调试` 的新配置，名称可以为debug，创建后选择该配置并点击调试按钮即可连接到调试实例

- 日志输出都在扩展开发宿主的 `ZenScript Language Server` 通道上

## 开发

- CST 解析使用 ANTLR4 库实现，修改`.g4`文件后可以运行 Gradle 任务 `generateGrammarSource` 重新生成源码（`./gradlew generateGrammarSource`）

- zenscript.tmLanguage.yaml 为源文件，其对应的 json 文件不应该直接编辑，源文件修改后可以运行 npm 任务 yaml2json 生成（`npm run yaml2json`）

- 构建时以上任务会自动运行

