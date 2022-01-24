package br.com.zumbolovsky.fateapp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class YamlTestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(YamlTestService.class);

    public static void main(final String[] args) {
        final YamlReader reader = new YamlReader();
        try {
            LOGGER.info("File read outputs class as: \n{}", reader.read(new FileInputStream("src/main/resources/test.yml")));
        } catch(final FileNotFoundException e) {
            LOGGER.info("File not found");
            throw new RuntimeException(e);
        }
    }
}
