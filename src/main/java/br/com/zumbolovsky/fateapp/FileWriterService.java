package br.com.zumbolovsky.fateapp;

import static java.nio.file.StandardOpenOption.APPEND;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.primitives.Longs;

public class FileWriterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileWriterService.class);

    /**
     * {@code Paths.get(String, String...)} works with absolute or relative paths:
     * <br>
     * - For absolute paths (Linux example): /home/<user>/test.txt
     * - For absolute paths (Windows example): C:\\Users\\<user>\\Documents\\test.txt
     * <br>
     * - For relative paths (project source directory): src/main/resources/test.txt
     * */
    public static void main(final String[] args) {
        try {
            final Path readPath = Paths.get("src/main/resources/test.txt");
            LOGGER.info("Text contained in the file is: {}", Files.readString(readPath));
            byte b = 1;
            long l = System.currentTimeMillis();
            //The resulting file won't be readable because it is a bunch of bytes
            final Path writePath = Paths.get("src/main/resources/result.txt");
            Files.deleteIfExists(writePath);
            Files.write(writePath, new byte[]{b});
            Files.write(writePath, Longs.toByteArray(l), APPEND);
            Files.write(writePath, "Text".getBytes(), APPEND);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
