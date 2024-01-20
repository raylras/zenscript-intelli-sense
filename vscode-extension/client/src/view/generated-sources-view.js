import vscode from "vscode"
import {existsSync, readdirSync, readFileSync, statSync} from "node:fs"
import {isAbsolute, relative, resolve} from "node:path"

export function registerGeneratedSourcesView() {
    vscode.window.registerTreeDataProvider("zenscript-generated-sources", new GeneratedSourcesProvider())
}

/**
 * @returns {string|undefined}
 */
function findGeneratedRoot() {
    const workspacePath = vscode.workspace.rootPath
    if (!workspacePath) return undefined
    const userHome = process.env.HOME || process.env.USERPROFILE
    const probezsPath = resolve(userHome, ".probezs")
    for (const entry of readdirSync(probezsPath)) {
        const generatedPath = resolve(probezsPath, entry, "generated")
        const envJsonPath = resolve(generatedPath, "env.json")
        if (existsSync(envJsonPath) && statSync(envJsonPath).isFile()) {
            const envJson = JSON.parse(readFileSync(envJsonPath, 'utf-8'))
            if (typeof envJson.scriptPath === "string") {
                if (isSubPath(envJson.scriptPath, workspacePath)) {
                    return generatedPath
                }
            }
        }
    }
}

function isSubPath(parent, path) {
    const relativePath = relative(path, parent);
    return relativePath && !relativePath.startsWith('..') && !isAbsolute(relativePath)
}

/**
 * @extends {vscode.TreeDataProvider}
 */
class GeneratedSourcesProvider {
    getTreeItem(element) {
        return Promise.resolve(element)
    }

    getChildren(element) {
        if (element === undefined) {
            const generatedRoot = findGeneratedRoot()
            if (generatedRoot) {
                return Promise.resolve(new DirectoryItem(generatedRoot).children)
            } else {
                return Promise.resolve([])
            }
        } else {
            return Promise.resolve(element.children)
        }
    }
}

class FileItem extends vscode.TreeItem {
    constructor(path) {
        super(vscode.Uri.file(path))
        this.command = {
            command: "vscode.open",
            title: "open",
            arguments: [vscode.Uri.file(path)]
        }
    }
}

class DirectoryItem extends vscode.TreeItem {
    constructor(path) {
        super(vscode.Uri.file(path))
        const dirs = []
        const files = []
        for (const entry of readdirSync(path)) {
            const entryPath = resolve(path, entry)
            if (statSync(entryPath).isDirectory()) {
                dirs.push(new DirectoryItem(entryPath))
            } else {
                files.push(new FileItem(entryPath))
            }
        }
        this.children = dirs.concat(files)
        this.collapsibleState = (this.children.length > 0)
            ? vscode.TreeItemCollapsibleState.Collapsed
            : vscode.TreeItemCollapsibleState.None
    }
}
