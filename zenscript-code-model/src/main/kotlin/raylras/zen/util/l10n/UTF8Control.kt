package raylras.zen.util.l10n

import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.util.*

// https://stacktuts.com/how-to-use-utf-8-in-resource-properties-with-resourcebundle
class UTF8Control : ResourceBundle.Control() {
    override fun newBundle(
        baseName: String,
        locale: Locale,
        format: String,
        loader: ClassLoader,
        reload: Boolean
    ): ResourceBundle {
        val bundleName = toBundleName(baseName, locale)
        val resourceName = toResourceName(bundleName, "properties")
        loader.getResourceAsStream(resourceName).use { stream ->
            // Read properties files as UTF-8
            return PropertyResourceBundle(InputStreamReader(stream!!, StandardCharsets.UTF_8))
        }
    }
}
