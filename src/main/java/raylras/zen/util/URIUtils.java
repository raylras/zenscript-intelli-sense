package raylras.zen.util;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;

public class URIUtils {

    public static URI decode(String uri) {
        try {
            return URI.create(URLDecoder.decode(uri, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

}
