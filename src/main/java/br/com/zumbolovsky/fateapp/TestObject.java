package br.com.zumbolovsky.fateapp;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@lombok.Getter
@lombok.Setter
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Builder
@lombok.ToString
public class TestObject implements Serializable {

    private String name;
    private Integer value;
    private InternalTestObject internalTestObject;
    private List<String> otherNames;

    @lombok.Getter
    @lombok.Setter
    @lombok.AllArgsConstructor
    @lombok.NoArgsConstructor
    @lombok.Builder
    @lombok.ToString
    public static class InternalTestObject implements Serializable {

        private Boolean isValid;
        private LocalDate date;
    }
}
