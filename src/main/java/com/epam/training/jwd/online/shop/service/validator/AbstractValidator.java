package com.epam.training.jwd.online.shop.service.validator;


public abstract class AbstractValidator implements Validator {
    protected final Validator nextValidator;

    public AbstractValidator(Validator nextValidator) {
        this.nextValidator = nextValidator;
    }

    public AbstractValidator() {
        nextValidator = null;
    }
}
