package com.epam.training.jwd.online.shop.service.validator;

public abstract class AbstractValidator implements Validator {
    protected Validator next;

    public AbstractValidator(final Validator next) {
        this.next = next;
    }
}
