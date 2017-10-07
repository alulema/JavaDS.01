package com.alexisalulema.utils;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class UrlUtils {

    public static String request(String url) throws IOException {
        try (InputStream is = new URL(url).openStream()) {
            return IOUtils.toString(is, StandardCharsets.UTF_8);
        }
    }
}
