package com.veljkobogdan.jauss.service;

import com.veljkobogdan.jauss.document.Url;
import com.veljkobogdan.jauss.util.Base62Encoder;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.regex.Pattern;

@Service
public class UrlShortenerService {
    private static final Pattern URL_PATTERN = Pattern.compile(
            "^(https?|ftp)://[\\w.-]+(?:\\.[\\w.-]+)+[/\\w%&=?.-]*$",
        Pattern.CASE_INSENSITIVE
    );

    public Url shorten(String url) throws Exception {
        String trimmedUrl = url.trim();
        assertUrlCorrect(trimmedUrl);

        String hash = Base62Encoder.generateShortCode(url);

        return new Url(trimmedUrl, hash);
    }

    private void assertUrlCorrect(String url) {
        if (!URL_PATTERN.matcher(url).matches()) {
            throw new IllegalArgumentException("Invalid URL format: " + url);
        }

        try {
            //noinspection ResultOfMethodCallIgnored
            URI.create(url);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid URL structure: " + url);
        }
    }
}
