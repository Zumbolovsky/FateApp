package br.com.zumbolovsky.fateapp;

public class ClassWithStaticMethod {
    public static String getFirstLetterCapitalized(final String x) {
        return x.substring(0, 1).toUpperCase();
    }
}
