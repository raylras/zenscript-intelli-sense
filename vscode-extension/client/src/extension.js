import {ExtensionContext, window} from "vscode";
import {LanguageClient} from "vscode-languageclient/node";
import LocateJavaHome from "@viperproject/locate-java-home";
import {activateLanguageServer} from "./language-server"

/** @type {LanguageClient} */
let languageClient = undefined;

/**
 * @param {ExtensionContext} context
 */
export async function activate(context) {
    LocateJavaHome({ version: ">=11" }, async (error, javaHomes) => {
        if (javaHomes.length === 0) {
            window.showErrorMessage("No valid Java environment found, please install Java 11 or later");
        } else {
            const javaBin = javaHomes[0].executables.java;
            languageClient = await activateLanguageServer(javaBin);
        }
    });
}

export async function deactivate() {
    return languageClient?.stop();
}
