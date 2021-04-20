package com.sprint.challenge2.utils;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Unit Test | String utils")
public class StringUtilsTest {
    @Test
    @DisplayName("String normalize OK")
    public void normalizeStringOk() {
        assertEquals("MEDELLIN", StringUtils.normalize("MedeLlíN"));
    }

    @Test
    @DisplayName("Email validation OK")
    public void emailValidationOk() {
        assertEquals(true, StringUtils.validateEmailFormat("prueba@gmail.com"));
    }

    @Test
    @DisplayName("Email validation FAIL")
    public void emailValidationFail() {
        assertEquals(false, StringUtils.validateEmailFormat("awfawgaw"));
    }
}
