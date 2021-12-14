package com.epam.training.jwd.online.shop.controller.command.impl;

import com.epam.training.jwd.online.shop.controller.command.*;
import com.epam.training.jwd.online.shop.controller.command.marker.AdminCommand;
import com.epam.training.jwd.online.shop.controller.constants.PageConstant;
import com.epam.training.jwd.online.shop.controller.constants.RequestConstant;
import com.epam.training.jwd.online.shop.dao.entity.Product;
import com.epam.training.jwd.online.shop.dao.exception.ServiceException;
import com.epam.training.jwd.online.shop.service.impl.OrderServiceImpl;
import com.epam.training.jwd.online.shop.service.impl.ProductServiceImpl;
import com.epam.training.jwd.online.shop.util.IOUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * The class provides delete {@link com.epam.training.jwd.online.shop.dao.entity.Product} by admin
 * @author Olga Grigorieva
 * @version 1.0.0
 */

public class DeleteProductCommand implements Command, AdminCommand {
    private static final Logger logger = LogManager.getLogger(DeleteProductCommand.class);
    private static final ProductServiceImpl productService = ProductServiceImpl.getInstance();
    private static final OrderServiceImpl orderService = OrderServiceImpl.getInstance();

    @Override
    public ResponseContext execute(RequestContext request) {
        try {
            int productId = Integer.parseInt(request.getRequestParameters().get(RequestConstant.ID));
            Optional<Product> productOptional = productService.findProductById(productId);
            if (productOptional.isPresent()) {
                orderService.deleteProductFromOrders(productId);
                Product product = productOptional.get();
                productService.deleteProduct(product);
                IOUtil.deleteData(product.getImgFileName());

                Map<String, Object> requestMap = new HashMap<>();
                requestMap.put(RequestConstant.REDIRECT_COMMAND, CommandManager.TO_CATALOG_ITEM.getCommandName());
                return new ResponseContext(new RestResponseType(), requestMap, new HashMap<>());
            }

        } catch (ServiceException | NumberFormatException e) {
            logger.error("Failed to delete product or incorrect product id", e);
        }
        return new ResponseContext(new ForwardResponseType(PageConstant.ERROR_PAGE), new HashMap<>(), new HashMap<>());
    }
}
