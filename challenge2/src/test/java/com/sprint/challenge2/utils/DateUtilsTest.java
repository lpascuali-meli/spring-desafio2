package com.sprint.challenge2.utils;

import org.junit.Test;
import com.sprint.challenge2.exceptions.ApiException;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;


class DateUtilsTest {
    @Test
    public void testMockStaticMethods() throws ApiException {
        assertEquals(10, DateUtils.calculateDaysBetweenDates("01/01/2021", "10/01/2021"));

        try (MockedStatic<DateUtils> theMock = Mockito.mockStatic(DateUtils.class)) {
            theMock.when(() -> DateUtils.calculateDaysBetweenDates("01/01/2021", "10/01/2021"))
                    .thenReturn(10);

            assertEquals(10, DateUtils.calculateDaysBetweenDates("01/01/2021", "10/01/2021"));
        }

        assertEquals(1, DateUtils.calculateDaysBetweenDates("01/01/2021", "02/01/2021"));
    }
}