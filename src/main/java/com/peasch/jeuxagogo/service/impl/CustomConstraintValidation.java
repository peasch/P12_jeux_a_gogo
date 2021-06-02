package com.peasch.jeuxagogo.service.impl;

import com.peasch.jeuxagogo.model.dtos.GameDto;
import com.peasch.jeuxagogo.service.misc.Text_FR;
import org.mortbay.log.Log;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.util.Set;

public class CustomConstraintValidation<T> {



    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public void validate(T var) {

        Set<ConstraintViolation<T>> constraintViolations = validator.validate(var);

        if (!constraintViolations.isEmpty()) {
            for (ConstraintViolation<T> contraintes : constraintViolations) {
                Log.info(contraintes.getRootBeanClass().getSimpleName() +
                        "." + contraintes.getPropertyPath() + " " + contraintes.getMessage());
            }
            throw new ValidationException(Text_FR.INCORRECT_INFORMATION);
        }
    }
}
