const { join } = require("node:path");
const { ExtensionContext, window, workspace } = require("vscode");
const { LanguageClient, LanguageClientOptions, ServerOptions } = require("vscode-languageclient/node");
const { SimpleLogger } = require("./simple-logger");
const locateJavaHome = require("@viperproject/locate-java-home").default

/**
 * @param {ExtensionContext} context
 */
function activate(context) {
    const logChannel = window.createOutputChannel('ZenScript Language Server', "log");
    const logger = new SimpleLogger(logChannel);
    logger.info('Starting the language Server');
    locateJavaHome({ version: ">=17" }, (error, javaHomes) => {
        if (javaHomes.length === 0) {
            logger.error('Unable to locate Java installation path. Make sure Java 17 or later is installed and added to the PATH environment variable');
        }
        const config = workspace.getConfiguration();
        const java = javaHomes[0].executables.java;
        const classpath = join(__dirname, '..', '..', 'server', '*');
        const args = ['-cp', classpath];
        const main = 'raylras.zen.langserver.StandardIOLauncher';
        let debug = '-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005,quiet=y';
        logger.info(`Java: ${java}`);
        logger.info(`Class path: ${classpath}`);
        logger.info(`Main class: ${main}`);
        if (config.get('zenscript.languageServer.debug')) {
            logger.info(`Language server is running in debug mode.`);
            if (config.get('zenscript.languageServer.suspend')) {
                debug = debug.replace(/suspend=n/, "suspend=y");
            }
            logger.info(`Debug arguments: ${debug}`);
            args.push(debug);
        }
        if (debug.indexOf("suspend=y") > -1) {
            logger.info('Waiting for debugger attachment...');
        }
        args.push('-Dfile.encoding=UTF-8');
        args.push(main);
        /** @type {ServerOptions} */
        const serverOptions = {
            command: java,
            args: [...args],
            options: {}
        };
        /** @type {LanguageClientOptions} */
        const clientOptions = {
            documentSelector: [{ scheme: 'file', language: 'zenscript' }],
            synchronize: { configurationSection: ['zenscript'] },
            outputChannel: logChannel
        };
        const client = new LanguageClient('ZenScript Language Client', serverOptions, clientOptions);
        client.start();
    })
}

function deactivate() {}

module.exports = {
    activate,
    deactivate
};