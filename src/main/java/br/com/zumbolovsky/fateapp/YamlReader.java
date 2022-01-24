package br.com.zumbolovsky.fateapp;

import java.io.IOException;
import java.io.InputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class YamlReader {
    
    private final Logger logger = LoggerFactory.getLogger(YamlReader.class);

    private final ObjectMapper objectMapper;

    public YamlReader() {
        objectMapper = new ObjectMapper(new YAMLFactory());
        objectMapper.findAndRegisterModules();
    }

    public TestObject read(final InputStream inputStream) {
        try {
            return objectMapper.readValue(inputStream, TestObject.class);
        } catch(final IOException e) {
            logger.info("Invalid read on YAML file!");
            throw new RuntimeException(e);
        }
    }
}
