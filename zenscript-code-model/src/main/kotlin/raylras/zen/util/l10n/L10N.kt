package raylras.zen.util.l10n

import java.text.MessageFormat
import java.util.*

object L10N {
    private lateinit var bundle: ResourceBundle

    fun localize(key: String, vararg args: Any?): String {
        return MessageFormat.format(localize(key), *args)
    }

    fun localize(key: String): String {
        if (::bundle.isInitialized.not()) {
            setLocale()
        }
        return bundle.getString(key)
    }

    fun setLocale(locale: String? = null) {
        return if (locale != null) {
            bundle = ResourceBundle.getBundle("l10n", Locale(locale), UTF8Control())
        } else {
            bundle = ResourceBundle.getBundle("l10n", UTF8Control())
        }
    }
}
