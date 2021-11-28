package com.epam.training.jwd.online.shop.controller.command.impl;

import com.epam.training.jwd.online.shop.controller.command.*;
import com.epam.training.jwd.online.shop.controller.command.marker.AdminCommand;
import com.epam.training.jwd.online.shop.controller.constants.PageConstant;
import com.epam.training.jwd.online.shop.controller.constants.RequestConstant;
import com.epam.training.jwd.online.shop.controller.handler.Handler;
import com.epam.training.jwd.online.shop.controller.handler.impl.ImgFileHandler;
import com.epam.training.jwd.online.shop.controller.handler.impl.ProductNameHandler;
import com.epam.training.jwd.online.shop.dao.entity.ProductCategory;
import com.epam.training.jwd.online.shop.dao.exception.ServiceException;
import com.epam.training.jwd.online.shop.service.dto.ProductCategoryServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Part;
import java.io.IOException;
import java.util.*;


public class AddProductCategoryCommand implements Command, AdminCommand {
    private static final Logger logger = LogManager.getLogger(AddProductCategoryCommand.class);
    private static final ProductCategoryServiceImpl productCategoryService = ProductCategoryServiceImpl.getInstance();
    private static final Handler productCategoryHandler = new ImgFileHandler(new ProductNameHandler());

    @Override
    public ResponseContext execute(RequestContext request) {
        Set<String> errorMessages = productCategoryHandler.handleRequest(request);
        Map<String, Object> requestMap = new HashMap<>();
        ResponseContext responseContext;

        if (errorMessages.isEmpty()) {
            Part imgFile = request.getRequestParts().get(RequestConstant.IMG_FILE);
            String imgFileName = UUID.randomUUID() + "-" + imgFile.getSubmittedFileName();
            String productTypeName = request.getRequestParameters().get(RequestConstant.PRODUCT_NAME);

            ProductCategory productCategory = ProductCategory.builder()
                    .withCategoryName(productTypeName)
                    .withImgFileName(imgFileName)
                    .build();

            try {
                Optional<String> serverMessage = productCategoryService.saveProductCategory(productCategory);
                if (!serverMessage.isPresent()) {
                    imgFile.write(imgFileName);
                    requestMap.put(RequestConstant.REDIRECT_COMMAND, CommandManager.TO_MENU.getCommandName());
                } else {
                    requestMap.put(RequestConstant.SERVER_MESSAGE, LocalizationMessage.localize(request.getLocale(), serverMessage.get()));
                }
                responseContext = new ResponseContext(new RestResponseType(), requestMap, new HashMap<>());
            } catch (ServiceException | IOException e) {
                logger.error("Failed to add product type", e);
                responseContext = new ResponseContext(new ForwardResponseType(PageConstant.ERROR_PAGE));
            }
        } else {
            requestMap.put(RequestConstant.ERROR_MESSAGE, errorMessages);
            responseContext = new ResponseContext(new RestResponseType(), requestMap, new HashMap<>());
        }
        return responseContext;
    }
}
