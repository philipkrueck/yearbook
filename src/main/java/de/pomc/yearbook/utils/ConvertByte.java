package de.pomc.yearbook.utils;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class ConvertByte {

    public String ToBase64(byte[] bytes) throws UnsupportedEncodingException {
        if (bytes != null && bytes.length > 0) {
            byte[] encodeBase64 = Base64.getEncoder().encode(bytes);
            String base64Encoded = new String(encodeBase64, "UTF-8");
            return base64Encoded;
        }
        return null;
    }
}
