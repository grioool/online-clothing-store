package com.epam.training.jwd.online.shop.controller.command.impl;


import com.epam.training.jwd.online.shop.controller.command.*;
import com.epam.training.jwd.online.shop.controller.constants.PageConstant;
import com.epam.training.jwd.online.shop.controller.constants.RequestConstant;
import com.epam.training.jwd.online.shop.controller.handler.Handler;
import com.epam.training.jwd.online.shop.controller.handler.impl.*;
import com.epam.training.jwd.online.shop.dao.entity.User;
import com.epam.training.jwd.online.shop.dao.entity.UserRole;
import com.epam.training.jwd.online.shop.dao.exception.ServiceException;
import com.epam.training.jwd.online.shop.domain.security.PasswordEncoder;
import com.epam.training.jwd.online.shop.service.impl.UserServiceImpl;
import com.epam.training.jwd.online.shop.util.LocalizationMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;


public class RegistrationCommand implements Command {
    private static final Logger logger = LogManager.getLogger(RegistrationCommand.class);
    private static final UserServiceImpl userService = UserServiceImpl.getInstance();
    private static final Handler registrationHandler = new NameHandler(new PasswordHandler(
            new MatchingPasswordsHandler(
                    new EmailHandler(new PhoneNumberHandler(new UsernameHandler())))
    ));


    @Override
    public ResponseContext execute(RequestContext request) {
        Set<String> errorMessages = registrationHandler.handleRequest(request);
        ResponseContext responseContext;

        if (errorMessages.isEmpty()) {
            String firstName = request.getRequestParameters().get(RequestConstant.FIRST_NAME);
            String lastName = request.getRequestParameters().get(RequestConstant.LAST_NAME);
            String username = request.getRequestParameters().get(RequestConstant.USERNAME);
            String email = request.getRequestParameters().get(RequestConstant.EMAIL);
            String phoneNumber = request.getRequestParameters().get(RequestConstant.PHONE_NUMBER);
            String password = request.getRequestParameters().get(RequestConstant.PASSWORD);

            User user = User.builder()
                    .withUsername(username)
                    .withFirstName(firstName)
                    .withLastName(lastName)
                    .withEmail(email)
                    .withPassword(PasswordEncoder.encryptPassword(password))
                    .withRole(UserRole.USER)
                    .withIsBlocked(false)
                    .withPhoneNumber(phoneNumber)
                    .build();
            try {
                Optional<String> serverMessage = userService.registerUser(user);
                Map<String, Object> requestMap = new HashMap<>();

                if (!serverMessage.isPresent()) {
                    Map<String, Object> sessionMap = new HashMap<>();
                    requestMap.put(RequestConstant.REDIRECT_COMMAND, CommandManager.TO_LOGIN.getCommandName());
                    responseContext = new ResponseContext(new RestResponseType(), requestMap, sessionMap);
                } else {
                    requestMap.put(RequestConstant.SERVER_MESSAGE, LocalizationMessage.localize(request.getLocale(), serverMessage.get()));
                    responseContext = new ResponseContext(new RestResponseType(), requestMap);
                }
            } catch (ServiceException e) {
                logger.error("Registration failed", e);
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
