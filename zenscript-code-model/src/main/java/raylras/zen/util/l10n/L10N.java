package raylras.zen.util.l10n;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class L10N {

    private static final Logger logger = LoggerFactory.getLogger(L10N.class);

    private static ResourceBundle bundle = getDefaultResourceBundle();

    public static String getString(String key) {
        try {
            return bundle.getString(key);
        } catch (Exception e) {
            logger.error("Failed to retrieve localized string for key: {}", key, e);
            return "";
        }
    }

    public static String format(String key, Object... args) {
        return MessageFormat.format(getString(key), args);
    }

    public static void setLocale(String locale) {
        try {
            bundle = ResourceBundle.getBundle("l10n", new Locale(locale), new UTF8Control());
        } catch (Exception e) {
            logger.error("Failed to set locale: {}", locale, e);
        }
    }

    private static ResourceBundle getDefaultResourceBundle() {
        return ResourceBundle.getBundle("l10n", new UTF8Control());
    }

}
