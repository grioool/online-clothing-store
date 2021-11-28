package com.epam.training.jwd.online.shop.controller.command.impl;


import com.epam.training.jwd.online.shop.controller.command.*;
import com.epam.training.jwd.online.shop.controller.command.marker.AdminCommand;
import com.epam.training.jwd.online.shop.controller.constants.PageConstant;
import com.epam.training.jwd.online.shop.controller.constants.RequestConstant;
import com.epam.training.jwd.online.shop.service.validator.Validator;
import com.epam.training.jwd.online.shop.service.validator.impl.ImgFileValidator;
import com.epam.training.jwd.online.shop.service.validator.impl.ProductNameValidator;
import com.epam.training.jwd.online.shop.dao.exception.ServiceException;
import com.epam.training.jwd.online.shop.service.impl.ProductCategoryServiceImpl;
import com.epam.training.jwd.online.shop.util.LocalizationMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Part;
import java.io.IOException;
import java.util.*;


public class EditProductCategoryCommand implements Command, AdminCommand {
    private static final Logger logger = LogManager.getLogger(EditProductCategoryCommand.class);
    private static final ProductCategoryServiceImpl productCategoryService = ProductCategoryServiceImpl.getInstance();
    private static final Validator EDIT_VALIDATOR = new ProductNameValidator(new ImgFileValidator());

    @Override
    public ResponseContext execute(RequestContext request) {
        Set<String> errorMessages = EDIT_VALIDATOR.validateRequest(request);
        Map<String, Object> requestMap = new HashMap<>();

        if (errorMessages.isEmpty()) {
            Part editedImg = request.getRequestParts().get(RequestConstant.IMG_FILE);
            String newFileName = UUID.randomUUID() + editedImg.getSubmittedFileName();
            String newProductName = request.getRequestParameters().get(RequestConstant.PRODUCT_NAME);
            int type_id = Integer.parseInt(request.getRequestParameters().get(RequestConstant.ID));

            try {
                Optional<String> serverMessage = productCategoryService.editProductCategory(type_id, newFileName, newProductName);

                if (!serverMessage.isPresent()) {
                    editedImg.write(newFileName);
                    requestMap.put(RequestConstant.REDIRECT_COMMAND, CommandManager.TO_MENU.getCommandName());
                } else {
                    requestMap.put(RequestConstant.SERVER_MESSAGE,
                            LocalizationMessage.localize(request.getLocale(), serverMessage.get()));
                }
                return new ResponseContext(new RestResponseType(), requestMap, new HashMap<>());

            } catch (ServiceException | IOException e) {
                logger.error("Failed to edit product type", e);
                return new ResponseContext(new ForwardResponseType(PageConstant.ERROR_PAGE), new HashMap<>(), new HashMap<>());
            }
        } else {
            requestMap.put(RequestConstant.ERROR_MESSAGE, errorMessages);
            return new ResponseContext(new RestResponseType(), requestMap, new HashMap<>());
        }
    }
}
