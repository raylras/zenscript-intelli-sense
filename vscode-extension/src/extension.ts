import { join } from 'node:path';
import { execSync } from 'node:child_process';
import { ExtensionContext, window, workspace } from 'vscode';
import { LanguageClient, LanguageClientOptions, ServerOptions } from 'vscode-languageclient/node';

export function activate(context: ExtensionContext) {
	const logChannel = window.createOutputChannel('ZenScript Language Server', "log");

	logChannel.appendLine('[info] [Extension] Starting ZenScript Language Server');
	getJavaHome().then(javahome => {
		const config = workspace.getConfiguration();
		const javabin: string = join(javahome, 'bin', 'java');
		const classpath: string = join(__dirname, '..', 'libs', '*');
		const args: string[] = ['-cp', classpath];
		const main = 'raylras.zen.langserver.StandardIOLauncher';
		let debug = '-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005,quiet=y';
		logChannel.appendLine(`[info] [Extension] Java home: ${javahome}`);
		logChannel.appendLine(`[info] [Extension] Class path: ${classpath}`)
		logChannel.appendLine(`[info] [Extension] Main class: ${main}`)

		if (config.get('zenscript.languageServer.debug')) {
			logChannel.appendLine('[info] [Extension] Debug mode is enabled for the language server');
			if (config.get('zenscript.languageServer.suspend')) {
				debug = debug.replace(/suspend=n/, "suspend=y");
			}
			logChannel.appendLine(`[info] [Extension] Debug arguments: ${debug}`);
			args.push(debug);
		}

		if (debug.indexOf("suspend=y") > -1) {
			logChannel.appendLine('[info] [Extension] Waiting for the debugger to attach...');
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
		logChannel.appendLine('[info] [Extension] Failed to start ZenScript Language Server');
		logChannel.appendLine(error?.message || error);
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
