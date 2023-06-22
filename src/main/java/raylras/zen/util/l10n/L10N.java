package raylras.zen.util.l10n;

import raylras.zen.util.Logger;

import java.util.Locale;
import java.util.ResourceBundle;

public class L10N {

    private static final Logger logger = Logger.getLogger("l10n");

    private static ResourceBundle bundle = getDefaultResourceBundle();

    public static String getString(String key) {
        try {
            return bundle.getString(key);
        } catch (Exception e) {
            logger.error(e, "Failed to get string for key: {0} ", key);
            return "";
        }
    }

    public static void setLocale(String locale) {
        try {
            bundle = ResourceBundle.getBundle("l10n", new Locale(locale), new UTF8Control());
        } catch (Exception e) {
            logger.error(e, "Failed to set locale: {0}", locale);
        }
    }

    private static ResourceBundle getDefaultResourceBundle() {
        return ResourceBundle.getBundle("l10n", new UTF8Control());
    }

}
