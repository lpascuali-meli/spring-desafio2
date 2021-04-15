package com.sprint.challenge2.utils;

import java.text.Normalizer;
import java.util.Locale;

public class StringUtils {
    public static String normalize(String word) {
        word = word.toUpperCase(Locale.ROOT);
        word = Normalizer.normalize(word, Normalizer.Form.NFD)
            .replaceAll("[^\\p{ASCII}]", "")
            .replace("/[\u0300-\u036f]/g", "")
            .replaceAll("\\s+","")
            .replace("/[^A-Z]/g", "");
        return word;
    }
}
