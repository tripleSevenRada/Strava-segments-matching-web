package com.matching.segmentsmatching.validators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SmallEnoughValidator
        implements ConstraintValidator<SmallEnough, Object> {

    //private static final Logger LOG = LoggerFactory.getLogger(SmallEnoughValidator.class);

    private static final Double SMALL_ENOUGH_DEGREES = 3.0;

    private String field1;
    private String field2;

    @Override
    public void initialize(SmallEnough constraintAnnotation) {
        this.field1 = constraintAnnotation.field1();
        this.field2 = constraintAnnotation.field2();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Object fieldValue1 = new BeanWrapperImpl(value)
                .getPropertyValue(field1);
        Object fieldValue2 = new BeanWrapperImpl(value)
                .getPropertyValue(field2);
        if (fieldValue1 instanceof Double
                && fieldValue2 instanceof Double) {
            //LOG.info("fieldValues " + fieldValue1.toString() + ", " + fieldValue2.toString());
            return Math.abs((Double) fieldValue1 - (Double) fieldValue2) < SMALL_ENOUGH_DEGREES;
        } else {
            //LOG.info("not instance of Double");
            return false;
        }
    }
}
