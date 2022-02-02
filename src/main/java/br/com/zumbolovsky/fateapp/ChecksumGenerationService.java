package br.com.zumbolovsky.fateapp;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChecksumGenerationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChecksumGenerationService.class);

    public static void main(final String[] args) {
        try(final FileInputStream inputStream = new FileInputStream("src/main/resources/test.txt")) {
            final MessageDigest shaDigest = MessageDigest.getInstance("SHA-256");
            final String checksum = generateChecksum(inputStream, shaDigest);
            LOGGER.info("Generated checksum: {}", checksum );
        } catch(final IOException e) {
            LOGGER.info("File not found!");
            throw new RuntimeException(e);
        } catch(final NoSuchAlgorithmException e) {
            LOGGER.info("The algorithm used for the digest was not found!");
            throw new RuntimeException(e);
        }
    }

    /**
     * Implementation found in <a href="https://howtodoinjava.com/java/java-security/sha-md5-file-checksum-hash/">this website</a>.
     * */
    private static String generateChecksum(final FileInputStream inputStream,
                                           final MessageDigest digest) throws IOException {
        //Create byte array to read data in chunks
        final byte[] byteArray = new byte[1024];
        int bytesCount;

        //Read file data and update in message digest
        while((bytesCount = inputStream.read(byteArray)) != -1) {
            digest.update(byteArray, 0, bytesCount);
        }

        //Get the hash's bytes
        final byte[] bytes = digest.digest();

        //This bytes[] has bytes in decimal format
        //Convert it to hexadecimal format
        final StringBuilder sb = new StringBuilder();
        for(byte aByte : bytes) {
            sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
        }

        //return complete hash
        return sb.toString();
    }
}
