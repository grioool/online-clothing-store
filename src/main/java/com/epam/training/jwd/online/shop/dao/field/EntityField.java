package com.epam.training.jwd.online.shop.dao.field;

import com.epam.training.jwd.online.shop.dao.entity.AbstractEntity;

/**
 * The common interface for entities fields
 * @author Olga Grigorieva
 * @version 1.0.0
 */

public interface EntityField<T extends AbstractEntity<Integer>>{
    String getField();
}
