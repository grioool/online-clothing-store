package com.epam.training.jwd.online.shop.dao.entity;

import java.io.Serializable;

public abstract class AbstractEntity<T> implements Serializable {

    private T id;

    public AbstractEntity(){}

    public AbstractEntity(T id){
        this.id = id;
    }

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }
}
