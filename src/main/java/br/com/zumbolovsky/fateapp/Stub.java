package br.com.zumbolovsky.fateapp;

import java.io.Serializable;

public class Stub implements Serializable {
    private static final long serialVersionUID = 8056214074392222442L;
    private Example example;

    public Stub() {
    }

    public Stub(Example example) {
        this.example = example;
    }

    public Example getExample() {
        return example;
    }

    public void setExample(Example example) {
        this.example = example;
    }
}
