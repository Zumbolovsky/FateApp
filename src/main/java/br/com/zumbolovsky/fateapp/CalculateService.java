package br.com.zumbolovsky.fateapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalculateService {

    @Autowired
    private CalculateUtil calculateUtil;

    public Integer calculateWithoutValidation(final Integer value) {
        final Integer calculateSum;
        try {
            calculateSum = calculateUtil.calculateSum(value);
        } catch (final RuntimeException e) {
            return 0;
        }
        return calculateSum;
    }

    public Integer calculate(final Integer value) {
        final Integer calculateSum;
        try {
            calculateSum = calculateUtil.calculateSum(value);
        } catch (final RuntimeException e) {
            return 0;
        }
        if (!isValid(calculateSum)) {
            return 0;
        }
        return calculateSum;
    }

    protected boolean isValid(final Integer value) {
        return value >= 0;
    }
}
