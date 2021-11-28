package com.epam.training.jwd.online.shop.controller.command.impl;


import com.epam.training.jwd.online.shop.controller.command.*;
import com.epam.training.jwd.online.shop.controller.command.marker.UserCommand;
import com.epam.training.jwd.online.shop.controller.constants.PageConstant;
import com.epam.training.jwd.online.shop.controller.constants.RequestConstant;
import com.epam.training.jwd.online.shop.controller.handler.impl.NameHandler;
import com.epam.training.jwd.online.shop.controller.handler.impl.PhoneNumberHandler;
import com.epam.training.jwd.online.shop.dao.entity.User;
import com.epam.training.jwd.online.shop.dao.exception.ServiceException;
import com.epam.training.jwd.online.shop.service.dto.UserDto;
import com.epam.training.jwd.online.shop.service.dto.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Handler;

public class EditProfileCommand implements Command, UserCommand {
    private static final Logger logger = LogManager.getLogger(EditProfileCommand.class);
    private static final UserServiceImpl userService = UserServiceImpl.getInstance();
    private static final Handler editHandler = new NameHandler(new PhoneNumberHandler());

    @Override
    public ResponseContext execute(RequestContext request) {
        Set<String> errorMessages = editHandler.handleRequest(request);
        Map<String, Object> requestMap = new HashMap<>();

        if (errorMessages.isEmpty()) {
            try {
                UserDto userDto = (UserDto) request.getSessionAttributes().get(RequestConstant.USER);
                Optional<User> userOptional = userService.findById(userDto.getId());

                if (userOptional.isPresent()) {

//                    User user = User.builder()
//                            .withId(userDto.getId())
//                            .withFirstName(request.getRequestParameters().get(RequestConstant.FIRST_NAME))
//                            .withLastName(request.getRequestParameters().get(RequestConstant.LAST_NAME))
//                            .withPhoneNumber(request.getRequestParameters().get(RequestConstant.PHONE_NUMBER))
//                            .withPassword(userOptional.get().getPassword())
//                            .withUsername(userOptional.get().getUsername())
//                            .withRole(userOptional.get().getRole())
//                            .withEmail(userOptional.get().getEmail())
//                            .build();

                    userService.updateUser(user);

                    requestMap.put(RequestConstant.REDIRECT_COMMAND, CommandManager.TO_PROFILE.getCommandName());
                    return new ResponseContext(new RestResponseType(), requestMap, new HashMap<>());
                }
            } catch (ServiceException e) {
                logger.error("Failed to edit profile", e);
                return new ResponseContext(new ForwardResponseType(PageConstant.ERROR_PAGE));
            }
        }
        requestMap.put(RequestConstant.ERROR_MESSAGE, errorMessages);
        return new ResponseContext(new RestResponseType(), requestMap, new HashMap<>());
    }
}

