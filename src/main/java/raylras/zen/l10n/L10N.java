package raylras.zen.l10n;

import java.util.Locale;
import java.util.ResourceBundle;

public class L10N {

    private static ResourceBundle bundle = getDefaultResourceBundle();

    public static String getString(String key) {
        return bundle.getString(key);
    }

    public static void setLocale(String locale) {
        L10N.bundle = ResourceBundle.getBundle("l10n", new Locale(locale), new UTF8Control());
    }

    public static ResourceBundle getDefaultResourceBundle() {
        return ResourceBundle.getBundle("l10n", new UTF8Control());
    }

}
