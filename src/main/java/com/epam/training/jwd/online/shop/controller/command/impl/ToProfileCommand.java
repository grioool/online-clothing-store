package com.epam.training.jwd.online.shop.controller.command.impl;

import com.epam.training.jwd.online.shop.controller.command.Command;
import com.epam.training.jwd.online.shop.controller.command.ForwardResponseType;
import com.epam.training.jwd.online.shop.controller.command.RequestContext;
import com.epam.training.jwd.online.shop.controller.command.ResponseContext;
import com.epam.training.jwd.online.shop.controller.command.marker.UserCommand;
import com.epam.training.jwd.online.shop.controller.constants.PageConstant;
import com.epam.training.jwd.online.shop.controller.constants.RequestConstant;
import com.epam.training.jwd.online.shop.dao.entity.User;
import com.epam.training.jwd.online.shop.dao.exception.ServiceException;
import com.epam.training.jwd.online.shop.service.dto.UserDto;
import com.epam.training.jwd.online.shop.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * The class provides moving {@link User} to profile page
 *
 * @author Olga Grigorieva
 * @version 1.0.0
 */

public class ToProfileCommand implements Command, UserCommand {
    private static final Logger logger = LogManager.getLogger(ToProfileCommand.class);

    @Override
    public ResponseContext execute(RequestContext request) {
        int userId = ((UserDto) (request.getSessionAttributes().get(RequestConstant.USER))).getId();
        try {
            Optional<User> userOptional = UserServiceImpl.getInstance().findById(userId);
            if (userOptional.isPresent()) {
                Map<String, Object> requestMap = new HashMap<>();
                requestMap.put(RequestConstant.USER, userOptional.get());
                return new ResponseContext(new ForwardResponseType(PageConstant.PROFILE_PAGE), requestMap, new HashMap<>());
            }
        } catch (ServiceException e) {
            logger.error("Failed move to profile", e);
        }
        return new ResponseContext(new ForwardResponseType(PageConstant.ERROR_PAGE));
    }
}
