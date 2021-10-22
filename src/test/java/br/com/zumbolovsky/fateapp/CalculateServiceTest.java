package br.com.zumbolovsky.fateapp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;


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
     * describe the test in a comprehensive and succinct way, as much as possible.
     *
     * For ease of understanding and organization, some tests have been separated into different methods,
     * but for some cases that use more extensive mocking, it might be more useful to reuse the already
     * mocked executions in a single test method which tests various possible execution flows or different
     * scenarios of a single tested method.*/

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
        //Service call
        boolean returnedValue = calculateService.isValid(0);
        //Assertion
        Assertions.assertTrue(returnedValue);

        //Service call
        returnedValue = calculateService.isValid(10);
        //Assertion
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
         * There is a great variety of ways that mocked executions can be declared. Some of them will be displayed below.
         *
         * It should be noted that it is only possible to mock the execution of mocked instances, either via
         * '@Mock' annotation or static method 'mock()', and '@Spy' annotation or static method 'spy()'.*/
        final int testValue = 2;
        final int testSum = 1;
        //Mocks
        //1.
        //Mockito.when(calculateUtil.calculateSum(2))
        //        .thenReturn(1);
        //2.
        Mockito.doReturn(testSum)
                .when(calculateUtil)
                .calculateSum(testValue);

        //Service call
        Integer result = calculateService.calculateWithoutValidation(testValue);

        //Assertion
        Assertions.assertEquals(result, testSum);

        /* In addition to this example, another great tool is the use of matchers to simulate an unknown or
         * unimportant parameter. Note, however, that when you use matchers to mock method calls, you must
         * specify a matcher for each parameter, because matchers and raw parameters are not allowed together by the
         * Mockito API.
         *
         * There are various matchers available in the API.
         * */
        //Mocks
        //1.
        //Mockito.doReturn(testSum)
        //        .when(calculateUtil)
        //        .calculateSum(Mockito.anyInt());
        //2.
        Mockito.doReturn(testSum)
                .when(calculateUtil)
                .calculateSum(Mockito.eq(testValue));

        //Service call
        result = calculateService.calculateWithoutValidation(testValue);

        //Assertion
        Assertions.assertEquals(result, testSum);
    }

    /**
     * Now for the second execution flow of the <code>calculateWithoutValidation</code> method of the
     * <code>CalculateService</code> class.
     *
     * For this test, it is needed to mock an exception being thrown and ensure that it is correctly handled.
     * Since this is a very simple test, there is also going to be a new element of Mockito for future reference.
     *
     * Here you can see that we can mock the exception in 2 main ways: if the exception that has been caught
     * is not going to be used again in the method, you can simply specify <code>doThrow(RuntimeException.class)</code>;
     * otherwise you can declare or even mock an Exception and specify it as <code>doThrow(runtimeException)</code>.
     *
     * Note that RuntimeException can be replaced with other exceptions depending on the catch clause, as long as it
     * extends Throwable.*/
    @Test
    public void calculateWithoutValidationShouldThrowExceptionAndReturnZero() {
        final int testValue = 2;
        //Mocks
        //1.
        //final RuntimeException runtimeException = new RuntimeException();
        //Mockito.doThrow(runtimeException)
        //        .when(calculateUtil)
        //        .calculateSum(testValue);
        //2.
        Mockito.doThrow(RuntimeException.class)
                .when(calculateUtil)
                .calculateSum(testValue);

        //Service execution
        final Integer testResult = calculateService.calculateWithoutValidation(testValue);

        /* Another type of assertion that can be made with Mockito is verification.
         * There is a variety of ways to verify executions.
         * In here will be demonstrated a few ways to verify the execution.
         * Verification only asserts if the execution has been called once, by default.
         * Verification can only be executed on mocked objects.*/
        //Assertion
        Mockito.verify(calculateUtil).calculateSum(testValue);
        Mockito.verify(calculateUtil, Mockito.times(1)).calculateSum(testValue);
        Mockito.verify(calculateUtil, Mockito.atLeastOnce()).calculateSum(testValue);
        Mockito.verify(calculateUtil, Mockito.atLeast(1)).calculateSum(testValue);
        Mockito.verify(calculateUtil, Mockito.atMostOnce()).calculateSum(testValue);
        Mockito.verify(calculateUtil, Mockito.atMost(1)).calculateSum(testValue);
        Assertions.assertEquals(testResult, 0);
    }

    /**
     * The next test will cover executions for the <code>calculate</code> method of the
     * <code>CalculateService</code> class.
     *
     * This method will require a new tool: a spy.
     *
     * Spies are normally used to spy on the execution of a specific method. This tool is greatly useful on methods
     * with calls on other inner methods of the same service. This example will further explain this concept.
     *
     * In order to run this test, it is needed to spy on the execution of the <code>isValid</code> method of the
     * <code>CalculateService</code> class, but the already declared 'calculateService' instance is not a mocked object.
     * Since it will need the <code>CalculateUtil</code> class mocked as a dependency,
     * a <code>@InjectMock</code> annotation will be added as well.*/
    @Spy
    @InjectMocks
    private CalculateService calculateServiceSpy;

    /**
     * To help in this situation we can create a spy of the very <code>CalculateService</code> class.
     * The spy will ensure that the class has its real method calls by default, but is also a mocked object.
     * In that way, the call for the <code>isValid</code> can be mocked.
     *
     * To provide further examples, all execution flows for this method will be created on this single test.*/
    @Test
    public void calculateTests() {
        final Integer testValue = 1;
        final Integer testResult = 2;
        //Mocks
        Mockito.doReturn(testResult)
                .when(calculateUtil)
                .calculateSum(testValue);
        Mockito.doReturn(true)
                .when(calculateServiceSpy)
                .isValid(testResult);

        /* Note that here, the service execution call will be made using the spy.
         * That is needed because the inner method we spied on is only mocked for the spy instance,
         * and not for the original 'calculateService' instance.
         * The other methods that have not been mocked will be their real method calls.*/
        //Service execution
        Integer result = calculateServiceSpy.calculate(testValue);

        //Assertion
        Assertions.assertEquals(result, testResult);

        //Mocks
        Mockito.doReturn(false)
                .when(calculateServiceSpy)
                .isValid(testResult);

        //Service execution
        result = calculateServiceSpy.calculate(testValue);

        //Assertion
        Assertions.assertEquals(result, 0);

        //Mocks
        Mockito.doThrow(RuntimeException.class)
                .when(calculateUtil)
                .calculateSum(testValue);

        //Service execution
        result = calculateServiceSpy.calculate(testValue);

        //Assertion
        Assertions.assertEquals(result, 0);
    }

    /**
     * This test cover a more advanced tool.
     * Note that this tool indicates a bad behaviour on the tested method, and as such, should be avoided
     * by writing better code (more testable code).
     *
     * In order to use the <code>Answers.RETURNS_DEEP_STUBS</code> in mocked objects, here's an example of
     * code in which that tool would be useful.*/
    @Test
    public void getPropertyShouldGetDeepStubExampleProperty() {
        /*To deep stub a mocked object simply write code as the below: */
        final Deep mockedDeepStubExample = Mockito.mock(Deep.class, Answers.RETURNS_DEEP_STUBS);

        /*This instance can now be used, and all internal objects will be stubbed.
        * In the stubs, all non-final fields are non-null and only final fields are null.
        * For example: the objects 'Stub' inside 'Deep', and 'Example' inside 'Stub' will be mocked objects,
        * but the property 'property' inside of 'Example' is null, unless some value is programmed to it.
        *
        * In order to follow with the test, 'property' will be programmed below: */
        final String test = "test";
        //Mocks
        Mockito.when(mockedDeepStubExample.getStub()
                .getExample()
                .getProperty())
                .thenReturn(test);

        //Service call
        final String property = calculateService.getProperty(mockedDeepStubExample);

        //Assetion
        Assertions.assertEquals(property, test);
    }

    @Test
    public void testingClassWithStaticTest() {
        try (final MockedStatic<ClassWithStaticMethod> mockedStatic = Mockito.mockStatic(ClassWithStaticMethod.class)) {
            final String testValue = null;
            mockedStatic.when(() -> ClassWithStaticMethod.getFirstLetterCapitalized(testValue)).thenReturn(testValue);
            String test = calculateService.staticTest(testValue);
            Assertions.assertNull(test);

            final String testValue2 = "test";
            mockedStatic.when(() -> ClassWithStaticMethod.getFirstLetterCapitalized(testValue2)).thenReturn(testValue2);
            test = calculateService.staticTest(testValue2);
            Assertions.assertEquals(test, testValue2);
        }
    }
}