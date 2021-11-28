package com.epam.training.jwd.online.shop.controller.command.impl;


import com.epam.training.jwd.online.shop.controller.command.Command;
import com.epam.training.jwd.online.shop.controller.command.ForwardResponseType;
import com.epam.training.jwd.online.shop.controller.command.RequestContext;
import com.epam.training.jwd.online.shop.controller.command.ResponseContext;
import com.epam.training.jwd.online.shop.controller.constants.PageConstant;
import com.epam.training.jwd.online.shop.controller.constants.RequestConstant;
import com.epam.training.jwd.online.shop.dao.entity.Product;
import com.epam.training.jwd.online.shop.dao.entity.ProductCategory;
import com.epam.training.jwd.online.shop.dao.exception.ServiceException;
import com.epam.training.jwd.online.shop.service.impl.ProductCategoryServiceImpl;
import com.epam.training.jwd.online.shop.service.impl.ProductServiceImpl;
import com.epam.training.jwd.online.shop.util.PaginationContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ToCatalogItemCommand implements Command {
    private static final Logger logger = LogManager.getLogger(ToCatalogItemCommand.class);
    private static final ProductServiceImpl productService = ProductServiceImpl.getInstance();
    private static final ProductCategoryServiceImpl productCategoryService = ProductCategoryServiceImpl.getInstance();

    @Override
    public ResponseContext execute(RequestContext request) {
        ResponseContext responseContext;
        Map<String, Object> requestMap = new HashMap<>();
        try {
            int page = Integer.parseInt(request.getRequestParameters().get(RequestConstant.CURRENT_PAGE));
            Integer categoryId = Integer.parseInt(request.getRequestParameters().get(RequestConstant.TYPE_ID));
            Optional<ProductCategory> productCategoryOptional = productCategoryService.findProductCategoryById(categoryId);

            List<Product> products = productService.findProductsByCategoryId(categoryId);
            if ((products.size() > 0) && (productCategoryOptional.isPresent())) {
                PaginationContext<Product> paginationContext = new PaginationContext<>(products, page);

                requestMap.put(RequestConstant.PAGINATION_CONTEXT, paginationContext);
                requestMap.put(RequestConstant.PRODUCT_CATEGORY, productCategoryOptional.get());

                responseContext = new ResponseContext(new ForwardResponseType(PageConstant.PRODUCTS_PAGE), requestMap, new HashMap<>());
            } else {
                responseContext = new ResponseContext(new ForwardResponseType(PageConstant.PRODUCTS_PAGE), new HashMap<>(), new HashMap<>());
            }
        } catch (ServiceException | NumberFormatException e) {
            logger.error("Moving to catalog item has failed", e);
            responseContext = new ResponseContext(new ForwardResponseType(PageConstant.ERROR_PAGE), new HashMap<>(), new HashMap<>());
        }
        return responseContext;
    }

}
