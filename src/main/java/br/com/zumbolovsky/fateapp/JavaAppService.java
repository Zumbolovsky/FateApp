package br.com.zumbolovsky.fateapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Service
public class JavaAppService {

    @Autowired
    private JavaOtherService javaOtherService;

    public Integer calculateAll(final Integer[] valores) {
        if (valores == null || valores.length == 0) {
            throw new RuntimeException("Valores inválidos");
        }
        var sumOf = Arrays.stream(valores)
                .map(valor -> javaOtherService.calculate(valor, 2))
                .reduce(0, Integer::sum);
        if (sumOf == 0) {
            throw new RuntimeException("Erro de cálculo");
        }
        return sumOf;
    }

    public void javaVersion16and17test() {
        final Stream<String> stream = Stream.of("A", "B", "C");
        final List<String> strings = stream.toList();
        System.out.println(strings);
        final Info info1 = new Info("", 0);
        final Info info2 = new Info();
        System.out.println(info1);
        System.out.println(info2);
        System.out.println(
            """
            test
            1
            2
            3
            """);
    }

    public record Info(String name, Integer number) {
        public Info() {
            this(null, null);
        }
    }
}
