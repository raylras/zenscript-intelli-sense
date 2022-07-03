import * as fs from "fs";
import * as net from 'net';
import * as path from 'path';
import {workspace, ExtensionContext} from 'vscode';

import {
	Executable,
	LanguageClient,
	LanguageClientOptions,
	ServerOptions,
	StreamInfo
} from 'vscode-languageclient/node';

const serverPort = 9865;
let client: LanguageClient;

export function activate(context: ExtensionContext) {
	// vscode.commands.registerCommand(
	// 	"zenscript.restartServer",
	// 	restartLanguageServer
	// );
	startLanguageServer(context);
}

export function deactivate(): Thenable<void> | undefined {
	if (!client) {
		return undefined;
	}
	return client.stop();
}

function startLanguageServer(context: ExtensionContext){
	const socket = net.connect({port:serverPort})
	.on('connect', () => {
		connectLanguageServer(context, socket);
	})
	.setTimeout(1)
	.on('error', () => {
		createLanguageServer(context);
	})
}

function restartLanguageServer(context: ExtensionContext) {
	client?.stop().then(() => {
		startLanguageServer(context);
	});
}

function createLanguageServer(context: ExtensionContext) {
	const javaPath = findJavaExecutable('java');
	const args = [
		'-jar',
		path.resolve(context.extensionPath, "server", "lsp4zen.jar"),
		'--stdio'
	];
	args.unshift("-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005,quiet=y")
	const clientOptions: LanguageClientOptions = {
		documentSelector: [{ scheme: 'file', language: 'zenscript' }],
		synchronize: {
			fileEvents: workspace.createFileSystemWatcher('**/.clientrc')
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
	client.start();
}

function connectLanguageServer(context: ExtensionContext, socket: net.Socket) {
	const serverOptions: ServerOptions = () => {
		return new Promise<StreamInfo>((resolve, reject) => {
			resolve({writer: socket, reader: socket});
		});
	};
	const clientOptions: LanguageClientOptions = {
		documentSelector: [{ scheme: 'file', language: 'zenscript' }],
		synchronize: {
			fileEvents: workspace.createFileSystemWatcher('**/.clientrc')
		}
	};
	client = new LanguageClient(
		'zenscript',
		'ZenScript Language Client(remote)', 
		serverOptions,
		clientOptions
	);
	client.start();
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
