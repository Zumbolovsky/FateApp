package br.com.zumbolovsky.fateapp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import br.com.zumbolovsky.fateapp.JunitSampleService.TestException;

class JunitTest {

    private final JunitSampleService junitSampleService = new JunitSampleService();

    @Test
    void testException() {
        Assertions.assertThrows(
            TestException.class,
            () -> junitSampleService.testException(null));
    }

    @Test
    void testInnerException() {
        Assertions.assertThrows(
            TestException.class,
            () -> junitSampleService.testInnerMethodException(null));
    }
}
