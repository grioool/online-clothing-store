package com.epam.training.jwd.online.shop.controller.command.impl;


import com.epam.training.jwd.online.shop.controller.command.*;
import com.epam.training.jwd.online.shop.controller.constants.PageConstant;
import com.epam.training.jwd.online.shop.controller.constants.RequestConstant;
import com.epam.training.jwd.online.shop.controller.handler.impl.PasswordHandler;
import com.epam.training.jwd.online.shop.controller.handler.impl.UsernameHandler;
import com.epam.training.jwd.online.shop.dao.exception.ServiceException;
import com.epam.training.jwd.online.shop.service.dto.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Handler;

public class LoginCommand implements Command {
    private static final Logger logger = LogManager.getLogger(LoginCommand.class);
    private static final UserServiceImpl userService = UserServiceImpl.getInstance();
    private static final Handler loginHandler = new UsernameHandler(new PasswordHandler());


    @Override
    public ResponseContext execute(RequestContext request) {
        Set<String> errorMessages = loginHandler.handleRequest(request);
        ResponseContext responseContext;

        if (errorMessages.isEmpty()) {
            String username = request.getRequestParameters().get(RequestConstant.USERNAME);
            String password = request.getRequestParameters().get(RequestConstant.PASSWORD);
            password = PasswordEncoder.encryptPassword(password);

            try {
                Map<String, Object> responseSession = new HashMap<>();
                Map<String, Object> requestMap = new HashMap<>();
                Optional<String> serverMessage = userService.loginUser(username, password, responseSession);

                if (!serverMessage.isPresent()) {
                    requestMap.put(RequestConstant.REDIRECT_COMMAND, CommandManager.TO_MAIN.getCommandName());
                    responseContext = new ResponseContext(new RestResponseType(), requestMap, responseSession);
                } else {
                    requestMap.put(RequestConstant.SERVER_MESSAGE, LocalizationMessage.localize(request.getLocale(), serverMessage.get()));
                    responseContext = new ResponseContext(new RestResponseType(), requestMap);
                }

            } catch (ServiceException e) {
                logger.error("Failed login user", e);
                responseContext = new ResponseContext(new ForwardResponseType(PageConstant.ERROR_PAGE));
            }

        } else {
            Map<String, Object> map = new HashMap<>();
            map.put(RequestConstant.ERROR_MESSAGE, errorMessages);
            responseContext = new ResponseContext(new RestResponseType(), map);
        }
        return responseContext;
    }
}