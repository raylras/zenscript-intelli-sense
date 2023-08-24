const { OutputChannel } = require("vscode")
const dayjs = require("dayjs")

class SimpleLogger {
	#logChannel;
	/**
	 * @param {OutputChannel} logChannel 
	 */
	constructor(logChannel) {
		this.#logChannel = logChannel;
	}
	/**
	 * @param {string} message
	 * @return {void}
	 */
	info(message) {
		this.#appendLine('INFO', message);
	}
	/**
	 * @param {string} message
	 * @return {void}
	 */
	error(message) {
		this.#appendLine("ERROR", message);
	}
	/**
	 * @param {string} level
	 * @param {string} message
	 * @return {void}
	 */
	#appendLine(level, message) {
		const currentTime = dayjs().format('YYYY-MM-DD HH:mm:ss.SSS');
		this.#logChannel.appendLine(`${currentTime} [${level}] extension - ${message}`);
	}
}

module.exports = {
	SimpleLogger
}