package br.com.zumbolovsky.fateapp;

import java.io.Serializable;

public class Example implements Serializable {
    private static final long serialVersionUID = -6443558218616730606L;
    private String property;

    public Example() {
    }

    public Example(String property) {
        this.property = property;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }
}
