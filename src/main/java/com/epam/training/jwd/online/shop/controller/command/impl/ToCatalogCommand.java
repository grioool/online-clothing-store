package com.epam.training.jwd.online.shop.controller.command.impl;

import com.epam.training.jwd.online.shop.controller.command.Command;
import com.epam.training.jwd.online.shop.controller.command.ForwardResponseType;
import com.epam.training.jwd.online.shop.controller.command.RequestContext;
import com.epam.training.jwd.online.shop.controller.command.ResponseContext;
import com.epam.training.jwd.online.shop.controller.constants.PageConstant;
import com.epam.training.jwd.online.shop.controller.constants.RequestConstant;
import com.epam.training.jwd.online.shop.dao.entity.ProductCategory;
import com.epam.training.jwd.online.shop.dao.exception.ServiceException;
import com.epam.training.jwd.online.shop.service.impl.ProductCategoryServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The class provides moving to catalog page
 *
 * @author Olga Grigorieva
 * @version 1.0.0
 */

public class ToCatalogCommand implements Command {
    private static final Logger logger = LogManager.getLogger(ToCatalogCommand.class);

    @Override
    public ResponseContext execute(RequestContext request) {
        Map<String, Object> requestMap = new HashMap<>();
        ResponseContext responseContext;
        try {
            List<ProductCategory> productCategories = ProductCategoryServiceImpl.getInstance().findAllProductCategories();
            requestMap.put(RequestConstant.PRODUCT_CATEGORIES, productCategories);
            responseContext = new ResponseContext(new ForwardResponseType(PageConstant.CATALOG_PAGE), requestMap, new HashMap<>());
        } catch (ServiceException e) {
            logger.error("Failed to find all product types", e);
            responseContext = new ResponseContext(new ForwardResponseType(PageConstant.ERROR_PAGE));
        }
        return responseContext;
    }
}
