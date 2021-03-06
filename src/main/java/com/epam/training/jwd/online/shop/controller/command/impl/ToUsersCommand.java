package com.epam.training.jwd.online.shop.controller.command.impl;

import com.epam.training.jwd.online.shop.controller.command.Command;
import com.epam.training.jwd.online.shop.controller.command.ForwardResponseType;
import com.epam.training.jwd.online.shop.controller.command.RequestContext;
import com.epam.training.jwd.online.shop.controller.command.ResponseContext;
import com.epam.training.jwd.online.shop.controller.command.marker.AdminCommand;
import com.epam.training.jwd.online.shop.controller.constants.PageConstant;
import com.epam.training.jwd.online.shop.controller.constants.RequestConstant;
import com.epam.training.jwd.online.shop.dao.entity.User;
import com.epam.training.jwd.online.shop.dao.exception.ServiceException;
import com.epam.training.jwd.online.shop.service.impl.UserServiceImpl;
import com.epam.training.jwd.online.shop.util.PaginationContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The class provides moving an admin to page with all users data
 *
 * @author Olga Grigorieva
 * @version 1.0.0
 */

public class ToUsersCommand implements Command, AdminCommand {
    private static final Logger logger = LogManager.getLogger(ToUsersCommand.class);
    private static final UserServiceImpl userService = UserServiceImpl.getInstance();

    @Override
    public ResponseContext execute(RequestContext request) {
        ResponseContext responseContext;
        Map<String, Object> requestMap = new HashMap<>();
        int page = Integer.parseInt(request.getRequestParameters().get(RequestConstant.CURRENT_PAGE));
        try {
            List<User> userList = userService.findAllUsers();
            if (userList.size() > 0) {
                PaginationContext<User> paginationContext = new PaginationContext<>(userList, page);
                requestMap.put(RequestConstant.PAGINATION_CONTEXT, paginationContext);
                responseContext = new ResponseContext(
                        new ForwardResponseType(PageConstant.USERS_PAGE), requestMap, new HashMap<>());
            } else {
                responseContext = new ResponseContext(
                        new ForwardResponseType(PageConstant.USERS_PAGE), new HashMap<>(), new HashMap<>());
            }
        } catch (ServiceException e) {
            logger.error("Failed move to users", e);
            responseContext = new ResponseContext(
                    new ForwardResponseType(PageConstant.ERROR_PAGE), new HashMap<>(), new HashMap<>());
        }
        return responseContext;
    }
}
