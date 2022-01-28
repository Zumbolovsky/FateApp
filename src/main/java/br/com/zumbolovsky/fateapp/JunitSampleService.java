package br.com.zumbolovsky.fateapp;

public class JunitSampleService {

    protected void testException(final String s) {
        if (s == null) {
            throw new TestException();
        }
    }

    protected void testInnerMethodException(final String s) {
        validate(s);
    }

    private void validate(final Object s) {
        if (s == null) {
            throw new TestException();
        }
    }

    public static class TestException extends RuntimeException {
        private static final long serialVersionUID = 3823220998419852073L;

        public TestException() {
            super();
        }
    }
}
