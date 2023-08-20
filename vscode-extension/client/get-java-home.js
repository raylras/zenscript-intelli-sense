const { execSync } = require("node:child_process");

/**
 *
 * @return {Promise<string>}
 */
function getJavaHome() {
	const javaHome = process.env?.['JAVA_HOME']
	if (javaHome) return Promise.resolve(javaHome)

	let cmd;
	if (process.platform == 'win32') {
		cmd = 'java -XshowSettings:properties -version 2>&1 | findstr "java.home"';
	} else {
		cmd = 'java -XshowSettings:properties -version 2>&1 > /dev/null | grep "java.home"';
	}

	return new Promise((resolve, reject) => {
		try {
			const response = execSync(cmd).toString();
			response ? resolve(response.split('java.home =')?.[1].trim()) : reject(new Error('Failed to get java home'));
		} catch (error) {
			reject(error)
		}
	});
}

module.exports = { 
	getJavaHome
};
