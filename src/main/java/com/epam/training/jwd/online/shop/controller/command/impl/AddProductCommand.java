package com.epam.training.jwd.online.shop.controller.command.impl;

import com.epam.training.jwd.online.shop.controller.command.*;
import com.epam.training.jwd.online.shop.controller.command.marker.AdminCommand;
import com.epam.training.jwd.online.shop.controller.constants.PageConstant;
import com.epam.training.jwd.online.shop.controller.constants.RequestConstant;
import com.epam.training.jwd.online.shop.controller.handler.Handler;
import com.epam.training.jwd.online.shop.controller.handler.impl.DescriptionHandler;
import com.epam.training.jwd.online.shop.controller.handler.impl.ImgFileHandler;
import com.epam.training.jwd.online.shop.controller.handler.impl.PriceHandler;
import com.epam.training.jwd.online.shop.controller.handler.impl.ProductNameHandler;
import com.epam.training.jwd.online.shop.dao.entity.Product;
import com.epam.training.jwd.online.shop.dao.entity.ProductCategory;
import com.epam.training.jwd.online.shop.dao.exception.ServiceException;
import com.epam.training.jwd.online.shop.service.dto.ProductCategoryServiceImpl;
import com.epam.training.jwd.online.shop.service.dto.ProductServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Part;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;



public class AddProductCommand implements Command, AdminCommand {
    private static final Logger logger = LogManager.getLogger(AddProductCommand.class);
    private static final ProductServiceImpl productService = ProductServiceImpl.getInstance();
    private static final ProductCategoryServiceImpl productCategoryService = ProductCategoryServiceImpl.getInstance();
    private static final Handler productHandler = new ProductNameHandler(new PriceHandler(
            new DescriptionHandler(new ImgFileHandler())));

    @Override
    public ResponseContext execute(RequestContext request) {
        Set<String> errorMessages = productHandler.handleRequest(request);
        Map<String, Object> requestMap = new HashMap<>();

        if (errorMessages.isEmpty()) {
            try {
                Part imgFile = request.getRequestParts().get(RequestConstant.IMG_FILE);
                String imgFileName = UUID.randomUUID() + "-" + imgFile.getSubmittedFileName();
                int productTypeId = Integer.parseInt(request.getRequestParameters().get(RequestConstant.TYPE_ID));
                String productName = request.getRequestParameters().get(RequestConstant.PRODUCT_NAME);
                String productDescription = request.getRequestParameters().get(RequestConstant.PRODUCT_DESCRIPTION);
                Double productPrice = new Double(request.getRequestParameters().get(RequestConstant.PRODUCT_PRICE));
                Optional<ProductCategory> productTypeOptional = productCategoryService.findProductCategoryById(productTypeId);

                if (productTypeOptional.isPresent()) {
                    Product product = Product.builder()
                            .withId(productTypeId)
                            .withProductName(productName)
                            .withProductCategory(productTypeOptional.get())
                            .withProductDescription(productDescription)
                            .withPrice(productPrice)
                            .withNameOfImage(imgFileName)
                            .build();

                    Optional<String> serverMessage = productService.saveProduct(product);

                    if (!serverMessage.isPresent()) {
                        imgFile.write(imgFileName);
                        requestMap.put(RequestConstant.REDIRECT_COMMAND, CommandManager.TO_MENU_ITEM.getCommandName());
                    } else {
                        requestMap.put(RequestConstant.SERVER_MESSAGE,
                                LocalizationMessage.localize(request.getLocale(), serverMessage.get()));
                    }
                    return new ResponseContext(new RestResponseType(), requestMap, new HashMap<>());
                }
            } catch (ServiceException | IOException e) {
                logger.error("Filed to add product", e);
            }
        } else {
            requestMap.put(RequestConstant.ERROR_MESSAGE, errorMessages);
            return new ResponseContext(new RestResponseType(), requestMap, new HashMap<>());
        }
        return new ResponseContext(new ForwardResponseType(PageConstant.ERROR_PAGE), new HashMap<>(), new HashMap<>());
    }

}
