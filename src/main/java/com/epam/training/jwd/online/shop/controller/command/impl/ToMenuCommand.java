package com.epam.training.jwd.online.shop.controller.command.impl;


import com.epam.training.jwd.online.shop.controller.command.Command;
import com.epam.training.jwd.online.shop.controller.command.ForwardResponseType;
import com.epam.training.jwd.online.shop.controller.command.RequestContext;
import com.epam.training.jwd.online.shop.controller.command.ResponseContext;
import com.epam.training.jwd.online.shop.controller.constants.PageConstant;
import com.epam.training.jwd.online.shop.controller.constants.RequestConstant;
import com.epam.training.jwd.online.shop.dao.entity.ProductCategory;
import com.epam.training.jwd.online.shop.dao.exception.ServiceException;
import com.epam.training.jwd.online.shop.service.dto.ProductCategoryServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ToMenuCommand implements Command {
    private static final Logger logger = LogManager.getLogger(ToMenuCommand.class);

    @Override
    public ResponseContext execute(RequestContext request) {
        Map<String, Object> requestMap = new HashMap<>();
        ResponseContext responseContext;
        try {
            List<ProductCategory> productCategories = ProductCategoryServiceImpl.getInstance().findAllProductCategories();
            requestMap.put(RequestConstant.PRODUCT_TYPES, productCategories);
            responseContext = new ResponseContext(new ForwardResponseType(PageConstant.MENU_PAGE), requestMap, new HashMap<>());
        } catch (ServiceException e) {
            logger.error("Failed to find all product types", e);
            responseContext = new ResponseContext(new ForwardResponseType(PageConstant.ERROR_PAGE));
        }
        return responseContext;
    }
}
