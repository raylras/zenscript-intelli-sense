const { join } = require("node:path");
const { ExtensionContext, window, workspace } = require("vscode");
const { LanguageClient } = require("vscode-languageclient/node");
const { SimpleLogger } = require("./simple-logger");
const { getJavaHome } = require("./get-java-home");

/**
 * @param {ExtensionContext} context
*/
function activate(context) {
    const logChannel = window.createOutputChannel('ZenScript Language Server', "log");
    const logger = new SimpleLogger(logChannel);
    logger.info('Starting the language Server');
    getJavaHome().then(javahome => {
        const config = workspace.getConfiguration();
        const javabin = join(javahome, 'bin', 'java');
        const classpath = join(__dirname, '..', 'server', '*');
        const args = ['-cp', classpath];
        const main = 'raylras.zen.langserver.StandardIOLauncher';
        let debug = '-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005,quiet=y';
        logger.info(`Java home: ${javahome}`);
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
        const serverOptions = {
            command: javabin,
            args: [...args],
            options: {}
        };
        const clientOptions = {
            documentSelector: [{ scheme: 'file', language: 'zenscript' }],
            synchronize: { configurationSection: ['zenscript'] },
            outputChannel: logChannel
        };
        const client = new LanguageClient('ZenScript Language Client', serverOptions, clientOptions);
        client.start();
    }).catch(error => {
        logger.error(`Failed to start the language server: ${error}`);
    });
}

function deactivate() {}

module.exports = {
    activate,
    deactivate
}
