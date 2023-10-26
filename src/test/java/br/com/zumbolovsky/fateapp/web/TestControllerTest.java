package br.com.zumbolovsky.fateapp.web;

import br.com.zumbolovsky.fateapp.service.TestEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class TestControllerTest extends EndToEndAutoConfiguration {

    @ParameterizedTest
    @CsvSource({
            "TEST_A",
            "TEST_B",
            "TEST_C",
    })
    public void shouldCallTestPluginEndpointSuccessfully(String processor) {
        final ResponseEntity<String> response = testPluginProcessor(TestEnum.valueOf(processor));
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assertions.assertNotNull(response.getBody());
        Assertions.assertTrue(response.getBody().contains("I'm implementation %s".formatted(processor.split("_")[1])));
    }
}
