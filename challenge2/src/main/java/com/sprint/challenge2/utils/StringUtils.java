package com.sprint.challenge2.utils;

import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    /**
     * Normalize some word, putting off the special characters
     * @param word String to normalize
     * @return String the word normalized
     */
    public static String normalize(String word) {
        word = word.toUpperCase(Locale.ROOT);
        word = Normalizer.normalize(word, Normalizer.Form.NFD) // Normalize the string
            .replaceAll("[^\\p{ASCII}]", "") // Normalize the string
            .replace("/[\u0300-\u036f]/g", "") // Replace special characters
            .replaceAll("\\s+","") // Remove blank space
            .replace("/[^A-Z]/g", ""); // Keep only letters
        return word;
    }

    /**
     * Validate string with email format
     * @param email String to validate
     * @return Boolean true if is valid, false if is not valid
     */
    public static Boolean validateEmailFormat(String email) {
        String regex = "^[\\w!#$%&'+/=?`{|}~^-]+(?:\\.[\\w!#$%&'+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
