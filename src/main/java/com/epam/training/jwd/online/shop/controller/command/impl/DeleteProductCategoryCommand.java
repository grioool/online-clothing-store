package com.epam.training.jwd.online.shop.controller.command.impl;


import com.epam.training.jwd.online.shop.controller.command.*;
import com.epam.training.jwd.online.shop.controller.command.marker.AdminCommand;
import com.epam.training.jwd.online.shop.controller.constants.PageConstant;
import com.epam.training.jwd.online.shop.controller.constants.RequestConstant;
import com.epam.training.jwd.online.shop.dao.entity.ProductCategory;
import com.epam.training.jwd.online.shop.dao.exception.ServiceException;
import com.epam.training.jwd.online.shop.service.impl.ProductCategoryServiceImpl;
import com.epam.training.jwd.online.shop.service.impl.ProductServiceImpl;
import com.epam.training.jwd.online.shop.util.IOUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DeleteProductCategoryCommand implements Command, AdminCommand {
    private static final Logger logger = LogManager.getLogger(DeleteProductCategoryCommand.class);
    private static final ProductCategoryServiceImpl productCategoryService = ProductCategoryServiceImpl.getInstance();

    @Override
    public ResponseContext execute(RequestContext request) {
        try {
            int typeId = Integer.parseInt(request.getRequestParameters().get(RequestConstant.ID));
            Optional<ProductCategory> productTypeOptional = productCategoryService.findProductCategoryById(typeId);
            if (productTypeOptional.isPresent()) {
                ProductCategory productCategory = productTypeOptional.get();
                ProductServiceImpl.getInstance().deleteAllProductsByCategoryId(productCategory.getId());
                productCategoryService.deleteProductCategory(productCategory);
                IOUtil.deleteData(productCategory.getImgFilename());

                Map<String, Object> requestMap = new HashMap<>();
                requestMap.put(RequestConstant.REDIRECT_COMMAND, CommandManager.TO_MENU.getCommandName());
                return new ResponseContext(new RestResponseType(), requestMap, new HashMap<>());
            }

        } catch (ServiceException | NumberFormatException e) {
            logger.error("Failed to delete product type or incorrect type id", e);
        }
        return new ResponseContext(new ForwardResponseType(PageConstant.ERROR_PAGE), new HashMap<>(), new HashMap<>());
    }

}
