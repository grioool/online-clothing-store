package com.epam.training.jwd.online.shop.dao.field;

import com.epam.training.jwd.online.shop.dao.entity.AbstractEntity;

public interface EntityField<T extends AbstractEntity<Integer>>{
    String getField();
}
