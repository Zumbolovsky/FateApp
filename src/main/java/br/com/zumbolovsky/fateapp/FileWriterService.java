package br.com.zumbolovsky.fateapp;

import static java.nio.file.StandardOpenOption.APPEND;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import com.google.common.primitives.Longs;

public class FileWriterService {

    public static void main(final String[] args) {
        try {
            byte b = 1;
            long l = System.currentTimeMillis( );
            Path path = Paths.get("test.du");
            Files.write(path, new byte[]{b});
            Files.write(path, Longs.toByteArray(l), APPEND);
            Files.write(path, "Text".getBytes(StandardCharsets.UTF_8), APPEND);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
