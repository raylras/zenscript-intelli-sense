import { ExtensionContext, window } from "vscode";
import { LanguageClient } from "vscode-languageclient/node";
import LocateJavaHome  from "@viperproject/locate-java-home";
import { activateLanguageServer } from "./language-server"
import { activateDebugAdapter } from "./debug-adapter"

/** @type {LanguageClient} */
let languageClient = undefined;

/**
 * @param {ExtensionContext} context
 */
export async function activate(context) {
    LocateJavaHome({ version: ">=17" }, async (error, javaHomes) => {
        if (javaHomes.length === 0) {
            window.showErrorMessage("No valid Java environment found, please install Java 17 or later");
        } else {
            const javaBin = javaHomes[0].executables.java;
            
            activateDebugAdapter(javaBin, context);
            activateLanguageServer(javaBin)
                .then(client => {
                    languageClient = client;
                });
        }
    });
}

export async function deactivate() {
    return languageClient?.stop();
}
