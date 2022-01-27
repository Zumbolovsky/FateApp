package br.com.zumbolovsky.fateapp;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class YamlTestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(YamlTestService.class);

    public static void main(final String[] args) {
        final YamlReader reader = new YamlReader();
        readAndExecute(inputStream -> LOGGER.info("File read outputs class as: \n{}", reader.read(inputStream)));
        readAndExecute(inputStream -> LOGGER.info("File read outputs string as: \n{}", reader.readAsString(inputStream)));
    }

    private static void readAndExecute(final Consumer<InputStream> consumer) {
        try (FileInputStream inputStream = new FileInputStream("src/main/resources/test.yml")) {
            consumer.accept(inputStream);
        } catch(final IOException e) {
            LOGGER.info("File not found");
            throw new RuntimeException(e);
        }
    }
}
