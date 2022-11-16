import * as path from 'path';
import * as child_process from 'child_process';
import * as vscode from 'vscode';
import {
	LanguageClient,
	LanguageClientOptions,
	ServerOptions
} from 'vscode-languageclient/node';

export function activate(context: vscode.ExtensionContext) {
	let logChannel = vscode.window.createOutputChannel('ZenScript Language Server');

	logChannel.appendLine('Starting ZenScript Language Server')
	getJavaHome().then(javahome => {
		let javabin: string = path.join(javahome, 'bin', 'java');
		let classpath: string = path.join(__dirname, '..', '*');
		let args: string[] = ['-cp', classpath];
		let main = 'raylras.zen.Main';
		let debug = '-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5005,quiet=y';

		logChannel.appendLine(`Java Bin: ${javabin}`);
		logChannel.appendLine(`Class Path: ${classpath}`)

		if(vscode.workspace.getConfiguration().get('zenscript.languageServer.debug') == true) {
			logChannel.appendLine('Debug mod is set to true. Language Server is starting on debug mode');
			logChannel.appendLine(`Debug Arguments: ${debug}`);
			args.push(debug);
		}

		args.push('-Dfile.encoding=utf8');
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
