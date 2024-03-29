import {ExtensionContext, window, workspace} from "vscode";
import {LanguageClient} from "vscode-languageclient/node";
import LocateJavaHome from "@viperproject/locate-java-home";
import {activateLanguageServer} from "./language-server"
import {registerGeneratedSourcesView} from "./view/generated-sources-view";

/** @type {LanguageClient} */
let languageClient = undefined;

/**
 * @param {ExtensionContext} context
 */
export async function activate(context) {
    registerGeneratedSourcesView();

    findJavaExecutable().then(javaBin => {
        activateLanguageServer(javaBin).then(client => {
            languageClient = client;
        })
    })
}

export async function deactivate() {
    return languageClient?.stop();
}

/**
 * @returns {Promise<string>}
 */
function findJavaExecutable() {
    return new Promise((resolve, reject) => {
        let javaBin = workspace.getConfiguration().get('zenscript.languageServer.javaBin');
        if (javaBin !== undefined && javaBin !== '') {
            resolve(javaBin);
        } else {
            LocateJavaHome({version: ">=11"}, (error, javaHomes) => {
                if (javaHomes.length === 0) {
                    window.showErrorMessage("No valid Java environment found, please install Java 11 or later");
                    reject();
                } else {
                    resolve(javaHomes[0].executables.java);
                }
            });
        }
    });
}
