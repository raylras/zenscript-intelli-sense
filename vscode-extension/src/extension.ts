import * as fs from "fs";
import * as net from 'net';
import * as path from 'path';
import * as vscode from 'vscode';

import {
	Executable,
	LanguageClient,
	LanguageClientOptions,
	ServerOptions,
	StreamInfo
} from 'vscode-languageclient/node';

const serverPort = 9865;
let client: LanguageClient | null;

export function activate(context: vscode.ExtensionContext) {
	// vscode.commands.registerCommand(
	// 	"zenscript.restartServer",
	// 	restartLanguageServer
	// );
	startLanguageServer(context);
}

export function deactivate(): Thenable<void> | undefined {
	client?.stop();
	client = null;
	return;
}

function startLanguageServer(context: vscode.ExtensionContext){
	const socket = net.connect({port:serverPort})
	.on('connect', () => {
		connectLanguageServer(context, socket);
	})
	.setTimeout(1)
	.on('error', () => {
		createLanguageServer(context);
	})
}

function restartLanguageServer(context: vscode.ExtensionContext) {
	let oldClient = client;
	client = null;
	oldClient?.stop().then(() => {
		startLanguageServer(context);
	});
}

function createLanguageServer(context: vscode.ExtensionContext) {
	const javaPath = findJavaExecutable('java');
	const args = [
		'-jar',
		path.resolve(context.extensionPath, "server", "zenserver-1.1.0.jar"),
		'-standard-io'
	];
	args.unshift("-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005,quiet=y")
	const clientOptions: LanguageClientOptions = {
		documentSelector: [{ scheme: 'file', language: 'zenscript' }],
		synchronize: {
			fileEvents: vscode.workspace.createFileSystemWatcher('**/.clientrc')
		},

		// Apache Licensed code from: https://github.com/GroovyLanguageServer/groovy-language-server
		uriConverters: {
            code2Protocol: (value: vscode.Uri) => {
              if (/^win32/.test(process.platform)) {
                //drive letters on Windows are encoded with %3A instead of :
                //but Java doesn't treat them the same
                return value.toString().replace("%3A", ":");
              } else {
                return value.toString();
              }
            },
            //this is just the default behavior, but we need to define both
            protocol2Code: (value) => vscode.Uri.parse(value),
		}

	};
	const executable: Executable = {
		command: javaPath,
		args: args,
	  };
	client = new LanguageClient(
		'zenscript',
		'ZenScript Language Client(built-in)', 
		executable,
		clientOptions
	);
	const disposable = client.start();
	context.subscriptions.push(disposable);
}

function connectLanguageServer(context: vscode.ExtensionContext, socket: net.Socket) {
	const serverOptions: ServerOptions = () => {
		return new Promise<StreamInfo>((resolve, reject) => {
			resolve({writer: socket, reader: socket});
		});
	};
	const clientOptions: LanguageClientOptions = {
		documentSelector: [{ scheme: 'file', language: 'zenscript' }],
		synchronize: {
			fileEvents: vscode.workspace.createFileSystemWatcher('**/.clientrc')
		},

		// Apache Licensed code from: https://github.com/GroovyLanguageServer/groovy-language-server
		uriConverters: {
            code2Protocol: (value: vscode.Uri) => {
              if (/^win32/.test(process.platform)) {
                //drive letters on Windows are encoded with %3A instead of :
                //but Java doesn't treat them the same
                return value.toString().replace("%3A", ":");
              } else {
                return value.toString();
              }
            },
            //this is just the default behavior, but we need to define both
            protocol2Code: (value) => vscode.Uri.parse(value),
		}

	};
	client = new LanguageClient(
		'zenscript',
		'ZenScript Language Client(remote)', 
		serverOptions,
		clientOptions
	);
	const disposable = client.start();
	context.subscriptions.push(disposable);
}

// MIT Licensed code from: https://github.com/georgewfraser/vscode-javac
function findJavaExecutable(binname: string) {
	binname = correctBinname(binname);

	// First search each JAVA_HOME bin folder
	if (process.env['JAVA_HOME']) {
		const workspaces = process.env['JAVA_HOME'].split(path.delimiter);
		for (let i = 0; i < workspaces.length; i++) {
			const binpath = path.join(workspaces[i], 'bin', binname);
			if (fs.existsSync(binpath)) {
				return binpath;
			}
		}
	}

	// Then search PATH parts
	if (process.env['PATH']) {
		const pathparts = process.env['PATH'].split(path.delimiter);
		for (let i = 0; i < pathparts.length; i++) {
			const binpath = path.join(pathparts[i], binname);
			if (fs.existsSync(binpath)) {
				return binpath;
			}
		}
	}

	// Else return the binary name directly (this will likely always fail downstream) 
	return "";
}

function correctBinname(binname: string) {
	if (process.platform === 'win32')
		return binname + '.exe';
	else
		return binname;
}
