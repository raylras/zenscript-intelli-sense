
import { join } from "node:path";
import { ExtensionContext, window, workspace, debug, DebugAdapterExecutable } from "vscode";
import { SimpleLogger } from "./simple-logger";

/**
 * @param {string} javaBin
 * @param {ExtensionContext} context
 */
export async function activateDebugAdapter(javaBin, context) {
    const logChannel = window.createOutputChannel('ZenScript Debug Adapter', "log");
    const logger = new SimpleLogger(logChannel);
    logger.info('Initializing Debug Adapter');
    const config = workspace.getConfiguration();
    // start debug adapter protocol
    const classpath = join(__dirname, '..', '..', 'server', '*');
    let dapDebug = '-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5006,quiet=y';
    const dapMain = 'raylras.zen.dap.debugserver.StandardIOLauncher';
    const dapArgs = ['-cp', classpath];
    if (config.get('zenscript.debugAdapter.debug')) {
        logger.info(`Language server is running in debug mode.`);
        if (config.get('zenscript.debugAdapter.suspend')) {
            dapDebug = dapDebug.replace(/suspend=n/, "suspend=y");
        }
        logger.info(`DAP Debug arguments: ${dapDebug}`);
        dapArgs.push(dapDebug);
    }
    const isSuspendDAP = dapDebug.indexOf("suspend=y") > -1;
    dapArgs.push('-Dfile.encoding=UTF-8');
    dapArgs.push(dapMain);
    const dapOptions = {
        env: process.env
    };
    context.subscriptions.push(debug.registerDebugAdapterDescriptorFactory('zenscript', {
        createDebugAdapterDescriptor(session, executable) {
            logger.info('Starting ZensScript Debug Adapter server');
            if (isSuspendDAP) {
                logger.info('Waiting for debugger attachment for DAP...');
            }
            return new DebugAdapterExecutable(javaBin, dapArgs, dapOptions);
        }
    }));


    context.subscriptions.push(debug.registerDebugConfigurationProvider('zenscript', {
        resolveDebugConfiguration(folder, config, token) {
            // if launch.json is missing or empty
            if (!config.type && !config.request && !config.name) {
                const editor = window.activeTextEditor;
                if (editor && editor.document.languageId === 'zenscript') {
                    config.type = 'zenscript';
                    config.name = 'Attach';
                    config.request = 'attach';
                    config.hostName = 'localhost';
                    config.port = 8000;
                    config.timeout = 1000;
                }
            }
            return config;
        }
    }));

    debug.onDidReceiveDebugSessionCustomEvent((e) => {
        if(e.event === "outputLog") {
            logChannel.appendLine(e.body);
        }
    });
}
