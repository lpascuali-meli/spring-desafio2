package com.sprint.challenge2.utils;

import org.junit.Test;
import com.sprint.challenge2.exceptions.ApiException;
import org.junit.jupiter.api.DisplayName;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("Unit Test | Date utils")
class DateUtilsTest {
    @Test
    public void testMockStaticMethods() throws ApiException {
        assertEquals(10, DateUtils.calculateDaysBetweenDates("01/01/2021", "10/01/2021"));
    }
}