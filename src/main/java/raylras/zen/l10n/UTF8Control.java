package raylras.zen.l10n;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

// https://stacktuts.com/how-to-use-utf-8-in-resource-properties-with-resourcebundle
public class UTF8Control extends ResourceBundle.Control {

    @Override
    public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload) throws IOException {
        String bundleName = toBundleName(baseName, locale);
        String resourceName = toResourceName(bundleName, "properties");
        try (InputStream stream = loader.getResourceAsStream(resourceName)){
            if (stream != null) {
                // Read properties files as UTF-8
                return new PropertyResourceBundle(new InputStreamReader(stream, StandardCharsets.UTF_8));
            } else {
                return null;
            }
        }
    }

}
