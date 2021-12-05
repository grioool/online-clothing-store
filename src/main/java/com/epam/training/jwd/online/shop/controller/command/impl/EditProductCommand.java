package com.epam.training.jwd.online.shop.controller.command.impl;

import com.epam.training.jwd.online.shop.controller.command.*;
import com.epam.training.jwd.online.shop.controller.command.marker.AdminCommand;
import com.epam.training.jwd.online.shop.controller.constants.PageConstant;
import com.epam.training.jwd.online.shop.controller.constants.RequestConstant;
import com.epam.training.jwd.online.shop.service.validator.Validator;
import com.epam.training.jwd.online.shop.service.validator.impl.DescriptionValidator;
import com.epam.training.jwd.online.shop.service.validator.impl.NumberValidator;
import com.epam.training.jwd.online.shop.service.validator.impl.PriceValidator;
import com.epam.training.jwd.online.shop.service.validator.impl.ProductNameValidator;
import com.epam.training.jwd.online.shop.dao.entity.Brand;
import com.epam.training.jwd.online.shop.dao.exception.ServiceException;
import com.epam.training.jwd.online.shop.service.impl.ProductCategoryServiceImpl;
import com.epam.training.jwd.online.shop.service.impl.ProductServiceImpl;
import com.epam.training.jwd.online.shop.util.LocalizationMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * The class provides editing {@link com.epam.training.jwd.online.shop.dao.entity.User} profile
 * @author Olga Grigorieva
 * @version 1.0.0
 */

public class EditProductCommand implements Command, AdminCommand {
    private static final Logger logger = LogManager.getLogger(EditProductCommand.class);
    private static final ProductServiceImpl productService = ProductServiceImpl.getInstance();
    private static final Validator editProductPrice = new PriceValidator(new ProductNameValidator(new DescriptionValidator()));
    private static final ProductCategoryServiceImpl productCategoryService = ProductCategoryServiceImpl.getInstance();

    @Override
    public ResponseContext execute(RequestContext request) {
        ResponseContext responseContext;
        Map<String, Object> requestMap = new HashMap<>();

        String id = request.getRequestParameters().get(RequestConstant.ID);
        Set<String> errorMessage = new NumberValidator(editProductPrice, id).validateRequest(request);

        if (errorMessage.isEmpty()) {
            Integer productId = Integer.parseInt(id);
            String price = request.getRequestParameters().get(RequestConstant.PRODUCT_PRICE);
            String productName = request.getRequestParameters().get(RequestConstant.PRODUCT_NAME);
            Double productPrice = Double.parseDouble(price);
            String productDescription = request.getRequestParameters().get(RequestConstant.PRODUCT_DESCRIPTION);
            Brand brand = Brand.valueOf(request.getRequestParameters().get(RequestConstant.PRODUCT_BRAND));
            String article = request.getRequestParameters().get(RequestConstant.PRODUCT_ARTICLE);
            Integer productArticle = Integer.parseInt(article);

            try {
                Optional<String> serverMessage = productService.editProduct(productId, productName, productDescription, brand, productPrice, productArticle);

                if (!serverMessage.isPresent()) {
                    requestMap.put(RequestConstant.REDIRECT_COMMAND, CommandManager.TO_CATALOG_ITEM.getCommandName());
                } else {
                    requestMap.put(RequestConstant.SERVER_MESSAGE, LocalizationMessage.localize(request.getLocale(), serverMessage.get()));
                }
                responseContext = new ResponseContext(new RestResponseType(), requestMap, new HashMap<>());

            } catch (ServiceException e) {
                logger.error("Failed to edit product", e);
                responseContext = new ResponseContext(new ForwardResponseType(PageConstant.ERROR_PAGE), new HashMap<>(), new HashMap<>());
            }
        } else {
            requestMap.put(RequestConstant.ERROR_MESSAGE, errorMessage);
            responseContext = new ResponseContext(new RestResponseType(), requestMap, new HashMap<>());
        }
        return responseContext;
    }
}
