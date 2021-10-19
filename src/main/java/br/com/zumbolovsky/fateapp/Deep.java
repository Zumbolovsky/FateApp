package br.com.zumbolovsky.fateapp;

import java.io.Serializable;

public class Deep implements Serializable {
    private static final long serialVersionUID = 7869970278407051256L;
    private Stub stub;

    public Deep() {
    }

    public Deep(Stub stub) {
        this.stub = stub;
    }

    public Stub getStub() {
        return stub;
    }

    public void setStub(Stub stub) {
        this.stub = stub;
    }
}
