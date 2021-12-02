package com.epam.training.jwd.online.shop.controller.command.impl;


import com.epam.training.jwd.online.shop.controller.command.*;
import com.epam.training.jwd.online.shop.controller.command.marker.UserCommand;
import com.epam.training.jwd.online.shop.controller.constants.PageConstant;
import com.epam.training.jwd.online.shop.controller.constants.RequestConstant;
import com.epam.training.jwd.online.shop.service.validator.Validator;
import com.epam.training.jwd.online.shop.service.validator.impl.NameValidator;
import com.epam.training.jwd.online.shop.service.validator.impl.PhoneNumberValidator;
import com.epam.training.jwd.online.shop.dao.entity.User;
import com.epam.training.jwd.online.shop.dao.exception.ServiceException;
import com.epam.training.jwd.online.shop.service.dto.UserDto;
import com.epam.training.jwd.online.shop.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * The class provides editing {@link com.epam.training.jwd.online.shop.dao.entity.Product} by admin
 * @author Olga Grigorieva
 * @version 1.0.0
 */

public class EditProfileCommand implements Command, UserCommand {
    private static final Logger logger = LogManager.getLogger(EditProfileCommand.class);
    private static final UserServiceImpl userService = UserServiceImpl.getInstance();
    private static final Validator EDIT_VALIDATOR = new NameValidator(new PhoneNumberValidator());

    @Override
    public ResponseContext execute(RequestContext request) {
        Set<String> errorMessages = EDIT_VALIDATOR.validateRequest(request);
        Map<String, Object> requestMap = new HashMap<>();

        if (errorMessages.isEmpty()) {
            try {
                UserDto userDto = (UserDto) request.getSessionAttributes().get(RequestConstant.USER);
                Optional<User> userOptional = userService.findById(userDto.getId());

                if (userOptional.isPresent()) {

                    User user = User.builder()
                            .withId(userDto.getId())
                            .withUsername(userOptional.get().getUsername())
                            .withFirstName(request.getRequestParameters().get(RequestConstant.FIRST_NAME))
                            .withLastName(request.getRequestParameters().get(RequestConstant.LAST_NAME))
                            .withEmail(userOptional.get().getEmail())
                            .withPassword(userOptional.get().getPassword())
                            .withRole(userOptional.get().getRole())
                            .withIsBlocked(false)
                            .withPhoneNumber(request.getRequestParameters().get(RequestConstant.PHONE_NUMBER))
                            .build();

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

