import {join} from "node:path";
import {window, workspace} from "vscode";
import {LanguageClient, LanguageClientOptions, ServerOptions} from "vscode-languageclient/node";

/**
 * @param {string} javaBin
 */
export async function activateLanguageServer(javaBin) {
    const logChannel = window.createOutputChannel('ZenScript Language Server', "log");
    logChannel.appendLine('Starting ZensScript language server');

    const config = workspace.getConfiguration();
    const classpath = join(__dirname, '..', '..', 'server', '*');
    const main = 'raylras.zen.lsp.StandardIOLauncher';

    /** @type {string[]} */
    const args = [];

    args.push("-Dfile.encoding=UTF-8")

    if (config.get('zenscript.languageServer.enableJavaArguments')) {
        args.push(config.get('zenscript.languageServer.javaArguments'));
    }

    args.push('-classpath', classpath);
    args.push(main);

    logChannel.appendLine(`Launch command: "${[javaBin, ...args].join(' ')}"`)

    /** @type {ServerOptions} */
    const serverOptions = {
        command: javaBin,
        args: args,
        options: {}
    };

    /** @type {LanguageClientOptions} */
    const clientOptions = {
        documentSelector: [{ scheme: 'file', language: 'zenscript' }],
        synchronize: { configurationSection: ['zenscript'] },
        outputChannel: logChannel
    };

    const client = new LanguageClient('ZenScript Language Client', serverOptions, clientOptions);
    await client.start();
    return client;
}
