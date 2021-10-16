package br.com.zumbolovsky.fateapp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * When using Mockito together with JUnit 5, it is recommended to use the <code>@ExtendWith</code> annotation
 * and to specify <code>MockitoExtension.class</code> as its value.
 *
 * Conventionally, test classes are created with the name of the targeted class for testing followed by 'Test'.*/
@ExtendWith(MockitoExtension.class)
class CalculateServiceTest {

    /**
     * Normally for unit tests, dependencies are mocked, because their execution shouldn't matter.
     * To do that, all the autowired dependencies receive the <code>@Mock</code> annotation.*/
    @Mock
    private CalculateUtil calculateUtil;

    /**
     * In order to inject the mocked dependencies into the tested service for future use,
     * the <code>@InjectMocks</code> annotation is used.*/
    @InjectMocks
    private CalculateService calculateService;

    /* Before the presentation of the test itself, it should be noted that test method names should
     * describe the test in a comprehensive and succinct way, as much as possible.*/

    /**
     * In this test there will be a demonstration of basic assertion of results.
     * For that the <code>isValid</code> method of our <code>CalculateService</code> class will be tested.
     * Since it has only one simple execution flow, and does not rely on any other method call, mocks won't be needed here.
     *
     * In this test, the objective is to simply assert if the logic of the method returns true for positive values.*/
    @Test
    public void isValidShouldReturnTrueForPositiveValues() {
        /* A test may have one or more assertions, in order to execute the same logic with different inputs,
         * or simply assert more specifically the information returned.
         * Both 0 and 10 values are being used for this assertion, because in this method 0 is
         * also considered a positive number.*/
        boolean returnedValue = calculateService.isValid(0);
        Assertions.assertTrue(returnedValue);

        returnedValue = calculateService.isValid(10);
        Assertions.assertTrue(returnedValue);
    }

    /**
     * This time, the test will cover execution for the <code>calculateWithoutValidation</code> method of the
     * <code>CalculateService</code> class.
     *
     * For this test, there are 2 main problems.
     * One of problems is the fact that this method has 2 possible execution flows that need to be validated.
     * The other problem is that the <code>calculate</code> method from the <code>CalculateUtil</code> class
     * must be mocked in order to get to the end of the method execution.
     *
     * To solve the first problem, the tests will be separated into different possible execution flows:
     * - One to validate the normal execution flow of the method, and;
     * - The other to validate the exception execution flow of the method.
     * That leaves us with the second problem which will be solved by using Mockito.*/
    @Test
    public void calculateWithoutValidationShouldReturnCalculatedValue() {
        /* First, the execution of the inner method call will need to be simulated using Mockito.
         * To do that, Mockito has a fluent API that specifies what should happen, and when it should happen.
         *
         * To specify what should happen, there are static methods such as 'doReturn()', 'doThrow()' and 'doNothing()',
         * and fluent methods such as 'thenReturn()' and 'thenThrow()'.
         *
         * To specify when it should happen, there are static and fluent versions of 'when()'.
         *
         * They are interchangeable and for the mock there's no difference.
         * There is a great variety of ways that a mocked executions can be declared. Some of them will be displayed below.
         *
         * It should be noted that it is only possible to mock the execution of mocked instances, either via
         * '@Mock' annotation or static method 'mock()', and '@Spy' annotation or static method 'spy()'.
         * */

        Mockito.when(calculateUtil.calculateSum(2))
                .thenReturn(1);
        Mockito.doReturn(1)
                .when(calculateUtil)
                .calculateSum(2);
        Mockito.doReturn(1)
                .when(calculateUtil.calculateSum(2));

    }
}