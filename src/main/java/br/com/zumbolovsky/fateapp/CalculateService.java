package br.com.zumbolovsky.fateapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalculateService {

    @Autowired
    private CalculateUtil calculateUtil;

    protected final Logger logger = LoggerFactory.getLogger(CalculateService.class);

    protected boolean isValid(final Integer value) {
        return value >= 0;
    }

    public Integer calculateWithoutValidation(final Integer value) {
        final Integer calculatedSum;
        try {
            calculatedSum = calculateUtil.calculateSum(value);
        } catch (final RuntimeException e) {
            logger.info("This execution has thrown an exception!");
            return 0;
        }
        return calculatedSum;
    }

    public Integer calculate(final Integer value) {
        final Integer calculatedSum;
        try {
            calculatedSum = calculateUtil.calculateSum(value);
        } catch (final RuntimeException e) {
            return 0;
        }
        if (!isValid(calculatedSum)) {
            return 0;
        }
        return calculatedSum;
    }

    public String getProperty(final Deep deep) {
        return deep.getStub().getExample().getProperty();
    }
}
