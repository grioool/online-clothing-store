package com.epam.training.jwd.online.shop.controller.command.impl;


import com.epam.training.jwd.online.shop.controller.command.*;
import com.epam.training.jwd.online.shop.controller.command.marker.AdminCommand;
import com.epam.training.jwd.online.shop.controller.constants.PageConstant;
import com.epam.training.jwd.online.shop.controller.constants.RequestConstant;
import com.epam.training.jwd.online.shop.controller.handler.impl.NumberHandler;
import com.epam.training.jwd.online.shop.dao.entity.User;
import com.epam.training.jwd.online.shop.dao.exception.ServiceException;
import com.epam.training.jwd.online.shop.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class UpdateUserCommand implements Command, AdminCommand {
    private static final Logger logger = LogManager.getLogger(UpdateUserCommand.class);
    private static final UserServiceImpl userService = UserServiceImpl.getInstance();

    @Override
    public ResponseContext execute(RequestContext request) {
        Set<String> errorMessages = new NumberHandler(request.getRequestParameters()
                .get(RequestConstant.LOYALTY_POINTS)).handleRequest(request);

        if (errorMessages.isEmpty()) {
            try {
                Integer id = Integer.parseInt(request.getRequestParameters().get(RequestConstant.ID));
                String checkUserBlock = request.getRequestParameters().get(RequestConstant.IS_BLOCKED);
                boolean isUserBlocked = Boolean.parseBoolean(checkUserBlock);
                Optional<User> userOptional = userService.findById(id);

                if (userOptional.isPresent()) {
                    User user = User.builder()
                            .withId(id)
                            .withUsername(userOptional.get().getUsername())
                            .withFirstName(userOptional.get().getFirstName())
                            .withLastName(userOptional.get().getLastName())
                            .withEmail(userOptional.get().getEmail())
                            .withPassword(userOptional.get().getPassword())
                            .withRole(userOptional.get().getRole())
                            .withIsBlocked(isUserBlocked)
                            .withPhoneNumber(userOptional.get().getPhoneNumber())
                            .build();
                    userService.updateUser(user);
                    return new ResponseContext(new RestResponseType(), new HashMap<>(), new HashMap<>());
                } else {
                    return new ResponseContext(new ForwardResponseType(PageConstant.ERROR_PAGE));
                }
            } catch (ServiceException e) {
                logger.error("Failed update user", e);
            }
        }
        Map<String,Object> requestMap = new HashMap<>();
        requestMap.put(RequestConstant.ERROR_MESSAGE, errorMessages);
        return new ResponseContext(new RestResponseType(), requestMap, new HashMap<>());
    }
}
