package com.epam.training.jwd.online.shop.controller.command.impl;

import com.epam.training.jwd.online.shop.controller.command.*;
import com.epam.training.jwd.online.shop.controller.command.marker.AdminCommand;
import com.epam.training.jwd.online.shop.controller.constants.PageConstant;
import com.epam.training.jwd.online.shop.controller.constants.RequestConstant;
import com.epam.training.jwd.online.shop.service.validator.Validator;
import com.epam.training.jwd.online.shop.service.validator.impl.ImgFileValidator;
import com.epam.training.jwd.online.shop.service.validator.impl.ProductNameValidator;
import com.epam.training.jwd.online.shop.dao.entity.ProductCategory;
import com.epam.training.jwd.online.shop.dao.exception.ServiceException;
import com.epam.training.jwd.online.shop.service.impl.ProductCategoryServiceImpl;
import com.epam.training.jwd.online.shop.util.LocalizationMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Part;
import java.io.IOException;
import java.util.*;

/**
 * The class provides for adding new {@link ProductCategory} by admin
 * @author Olga Grigorieva
 * @version 1.0.0
 */

public class AddProductCategoryCommand implements Command, AdminCommand {
    private static final Logger logger = LogManager.getLogger(AddProductCategoryCommand.class);
    private static final ProductCategoryServiceImpl productCategoryService = ProductCategoryServiceImpl.getInstance();
    private static final Validator PRODUCT_CATEGORY_VALIDATOR = new ImgFileValidator(new ProductNameValidator());

    @Override
    public ResponseContext execute(RequestContext request) {
        Set<String> errorMessages = PRODUCT_CATEGORY_VALIDATOR.validateRequest(request);
        Map<String, Object> requestMap = new HashMap<>();
        ResponseContext responseContext;

        if (errorMessages.isEmpty()) {
            Part imgFile = request.getRequestParts().get(RequestConstant.IMG_FILE);
            String imgFileName = UUID.randomUUID() + "-" + imgFile.getSubmittedFileName();
            String productCategoryName = request.getRequestParameters().get(RequestConstant.PRODUCT_NAME);

            ProductCategory productCategory = ProductCategory.builder()
                    .withCategoryName(productCategoryName)
                    .withImgFileName(imgFileName)
                    .build();

            try {
                Optional<String> serverMessage = productCategoryService.saveProductCategory(productCategory);
                if (!serverMessage.isPresent()) {
                    imgFile.write(imgFileName);
                    requestMap.put(RequestConstant.REDIRECT_COMMAND, CommandManager.TO_CATALOG.getCommandName());
                } else {
                    requestMap.put(RequestConstant.SERVER_MESSAGE, LocalizationMessage.localize(request.getLocale(), serverMessage.get()));
                }
                responseContext = new ResponseContext(new RestResponseType(), requestMap, new HashMap<>());
            } catch (ServiceException | IOException e) {
                logger.error("Failed to add product category", e);
                responseContext = new ResponseContext(new ForwardResponseType(PageConstant.ERROR_PAGE));
            }
        } else {
            requestMap.put(RequestConstant.ERROR_MESSAGE, errorMessages);
            responseContext = new ResponseContext(new RestResponseType(), requestMap, new HashMap<>());
        }
        return responseContext;
    }
}
