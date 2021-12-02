package com.epam.training.jwd.online.shop.service.validator;

/**
 * The abstract class of application validator that uses chain of responsibility pattern
 *
 * @author Olga Grigorieva
 * @version 1.0.0
 */

public abstract class AbstractValidator implements Validator {
    protected final Validator nextValidator;

    public AbstractValidator(Validator nextValidator) {
        this.nextValidator = nextValidator;
    }

    public AbstractValidator() {
        nextValidator = null;
    }
}
