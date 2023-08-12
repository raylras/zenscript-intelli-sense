import { join } from 'node:path';
import { execSync } from 'node:child_process';
import { ExtensionContext, LogOutputChannel, OutputChannel, window, workspace } from 'vscode';
import { LanguageClient, LanguageClientOptions, ServerOptions } from 'vscode-languageclient/node';
import dayjs = require('dayjs');

let logger: SimpleLogger;

export function activate(context: ExtensionContext) {
	const logChannel = window.createOutputChannel('ZenScript Language Server', "log");
	logger = new SimpleLogger(logChannel);

	logger.info('Starting the language Server');
	getJavaHome().then(javahome => {
		const config = workspace.getConfiguration();
		const javabin: string = join(javahome, 'bin', 'java');
		const classpath: string = join(__dirname, '..', 'libs', '*');
		const args: string[] = ['-cp', classpath];
		const main = 'raylras.zen.langserver.StandardIOLauncher';
		let debug = '-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005,quiet=y';
		logger.info(`Java home: ${javahome}`)
		logger.info(`Class path: ${classpath}`)
		logger.info(`Main class: ${main}`)

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

		const serverOptions: ServerOptions = {
			command: javabin,
			args: [...args],
			options: {}
		};

		const clientOptions: LanguageClientOptions = {
			documentSelector: [{ scheme: 'file', language: 'zenscript' }],
			synchronize: { configurationSection: ['zenscript'] },
			outputChannel: logChannel
		};

		const client = new LanguageClient('ZenScript Language Client', serverOptions, clientOptions);
		client.start();
		// const disposable = client.start();
		// context.subscriptions.push(disposable);
	}).catch(error => {
		logger.error(`Failed to start the Language Server: ${error?.message || error}`);
	})

}

export function deactivate() { }

function getJavaHome() {
	const javaHome = process.env?.['JAVA_HOME']
	if (javaHome) return Promise.resolve(javaHome)

	let cmd: string;
	if (process.platform == 'win32') {
		cmd = 'java -XshowSettings:properties -version 2>&1 | findstr "java.home"';
	} else {
		cmd = 'java -XshowSettings:properties -version 2>&1 > /dev/null | grep "java.home"';
	}

	return new Promise<string>((resolve, reject) => {
		try {
			const response = execSync(cmd).toString();
			response ? resolve(response.split('java.home =')?.[1].trim()) : reject(new Error('Failed to get java home'));
		} catch (error) {
			reject(error)
		}
	});
}

class SimpleLogger {
	logChannel: OutputChannel

	constructor(logChannel: OutputChannel) {
		this.logChannel = logChannel;
	}

	info(message: string): void {
		this.appendLine('INFO', message)
	}

	error(message: string): void {
		this.appendLine("ERROR", message)
	}

	appendLine(level: string, message: string): void {
		const currentTime =  dayjs().format('YYYY-MM-DD HH:mm:ss.SSS')
		this.logChannel.appendLine(`${currentTime} [${level}] extension - ${message}`)
	}
}
