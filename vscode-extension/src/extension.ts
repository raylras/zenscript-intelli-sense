import * as path from 'path';
import * as child_process from 'child_process';
import {ExtensionContext, window, workspace} from 'vscode';
import {LanguageClient, LanguageClientOptions, ServerOptions} from 'vscode-languageclient/node';

export function activate(context: ExtensionContext) {
	let logChannel = window.createOutputChannel('ZenScript Language Server', "log");

	logChannel.appendLine('[Extension] Starting ZenScript language server')
	getJavaHome().then(javahome => {
		let javabin: string = path.join(javahome, 'bin', 'java');
		let classpath: string = path.join(__dirname, '..', '*');
		let args: string[] = ['-cp', classpath];
		let main = 'raylras.zen.langserver.StandardIOLauncher';
		let debug = '-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005,quiet=y';

		logChannel.appendLine(`[Extension] Java home: ${javahome}`);
		logChannel.appendLine(`[Extension] Class path: ${classpath}`)
		logChannel.appendLine(`[Extension] Main class: ${main}`)

		if(workspace.getConfiguration().get('zenscript.languageServer.debug') == true) {
			logChannel.appendLine('[Extension] Language server is starting on debug mode');
			logChannel.appendLine(`[Extension] Arguments: ${debug}`);
			args.push(debug);
		}

		args.push('-Dfile.encoding=UTF-8');
		args.push(main);

		let serverOptions: ServerOptions = {
			command: javabin,
			args: [...args],
			options: {}
		};

		let clientOptions: LanguageClientOptions = {
			documentSelector: [{ scheme: 'file', language: 'zenscript' }],
			synchronize: { configurationSection: ['zenscript'] },
			outputChannel: logChannel
		};

		let client = new LanguageClient('ZenScript Language Client', serverOptions, clientOptions);
		let disposable = client.start();
		// context.subscriptions.push(disposable);
	})

}

export function deactivate() {}

function getJavaHome() {
	let cmd: string;
	if (process.platform == 'win32') {
		cmd = 'java -XshowSettings:properties -version 2>&1 | findstr "java.home"';
	} else {
		cmd = 'java -XshowSettings:properties -version 2>&1 > /dev/null | grep "java.home"';
	}

	return new Promise<string>((resolve, reject) => {
		try {
			let response = child_process.execSync(cmd).toString();
			resolve(response.split('java.home =')[1].trim());
		} catch (error) {
			reject(error)
		}
	});
}
