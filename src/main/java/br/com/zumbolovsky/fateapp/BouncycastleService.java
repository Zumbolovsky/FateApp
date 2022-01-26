package br.com.zumbolovsky.fateapp;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
import org.apache.tomcat.util.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BouncycastleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BouncycastleService.class);

    public static void main(final String[] args) {
        Security.addProvider(new BouncyCastleProvider());
        final KeyPair keyPair = generateKeyPair();
        final String base64EncodedText = encodeBase64String("testText");
        LOGGER.info("Base 64 encoded value: {}", base64EncodedText);

        final byte[] signature = sign(keyPair, base64EncodedText);
        final boolean verified = verify(keyPair, base64EncodedText, signature);
        if(verified) {
            LOGGER.info("Sign successful! Verified!");
        } else {
            LOGGER.info("Sign unsuccessful! Not verified!");
        }
    }

    private static String encodeBase64String(final String testText) {
        return new String(Base64.encodeBase64(testText.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
    }

    private static KeyPair generateKeyPair() {
        final KeyPairGenerator generator = getKeyPairGenerator();
        generator.initialize(2048, new SecureRandom());
        return generator.generateKeyPair();
    }

    private static KeyPairGenerator getKeyPairGenerator() {
        try {
            return KeyPairGenerator.getInstance("RSA", "BC");
        } catch(final Exception ex) {
            throw new RuntimeException("No such algorithm or provider for key pair generation!", ex);
        }
    }

    private static byte[] sign(final KeyPair keyPair, final String base64EncodedText) {
        final Signature signer = getSigner();
        try {
            signer.initSign(keyPair.getPrivate());
            signer.update(base64EncodedText.getBytes(StandardCharsets.UTF_8));
            return signer.sign();
        } catch (final Exception ex) {
            throw new RuntimeException("Cannot generate RSA sigature.", ex);
        }
    }

    private static boolean verify(final KeyPair keyPair, final String base64EncodedText, final byte[] signature) {
        final Signature signer = getSigner();
        try {
            signer.initVerify(keyPair.getPublic());
            signer.update(base64EncodedText.getBytes(StandardCharsets.UTF_8));
            return signer.verify(signature);
        } catch (final Exception ex) {
            throw new RuntimeException("Cannot verify RSA sigature.", ex);
        }
    }

    private static Signature getSigner() {
        try {
            return Signature.getInstance("SHA512withRSA", "BC");
        } catch(final Exception ex) {
            throw new RuntimeException("No such algorithm or provider for signer!", ex);
        }
    }
}
