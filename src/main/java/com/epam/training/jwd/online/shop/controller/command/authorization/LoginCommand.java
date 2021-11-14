package com.epam.training.jwd.online.shop.controller.command.authorization;

import com.epam.training.jwd.online.shop.controller.command.Command;
import com.epam.training.jwd.online.shop.controller.command.CommandRequest;
import com.epam.training.jwd.online.shop.controller.command.CommandResponse;
import com.epam.training.jwd.online.shop.dao.entity.User;
import com.epam.training.jwd.online.shop.dao.exception.EntityNotFoundException;
import com.epam.training.jwd.online.shop.service.dto.UserService;

import javax.servlet.http.HttpSession;

public enum LoginCommand implements Command {
    INSTANCE;

    private static final String LOGIN_PARAM_NAME = "authorization";
    private static final String USERNAME_SESSION_ATTRIB_NAME = "username";
    private static final String FIRST_NAME_PARAM = "firstName";
    private static final String LAST_NAME_PARAM = "lastName";
    private static final String EMAIL_PARAM = "email";
    private static final String PASSWORD_PARAM = "password";
    public static final String USER_ROLE_SESSION_ATTRIB_NAME = "userRole";
    private static final String BLOCK_STATUS_PARAM = "isBlocked";
    private static final String PHONE_NUMBER_PARAM = "phoneNumber";


    private static final String ERROR_ATTRIB_NAME = "error";
    private static final String INVALID_CREDENTIALS_MSG = "Wrong login or password";

    private static final CommandResponse LOGIN_ERROR_RESPONSE = new CommandResponse() {
        @Override
        public String getPath() {
            return "/WEB-INF/jsp/login.jsp";
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    public static final CommandResponse LOGIN_SUCCESS_RESPONSE = new CommandResponse() {
        @Override
        public String getPath() {
            return "/index.jsp";
        }

        @Override
        public boolean isRedirect() {
            return true;
        }
    };
    private final UserService service;

    LoginCommand() {
        service = UserService.simple();
    }

    @Override
    public CommandResponse execute(CommandRequest request) throws EntityNotFoundException {
        final String login = request.getParameter(LOGIN_PARAM_NAME);
        final String password = request.getParameter(PASSWORD_PARAM);
        final User user = new User(login, password);
        if (!service.canLogIn(user)) {
            return prepareErrorPage(request);
        }
        return addUserInfoToSession(request, login);
    }

    private CommandResponse prepareErrorPage(CommandRequest request) {
        request.setAttribute(ERROR_ATTRIB_NAME, INVALID_CREDENTIALS_MSG);
        return LOGIN_ERROR_RESPONSE;
    }

    private CommandResponse addUserInfoToSession(CommandRequest request, String login) throws EntityNotFoundException {
        request.getCurrentSession().ifPresent(HttpSession::invalidate);
        final HttpSession session = request.createSession();
        final User loggedInUser = service.findByLogin(login);
        session.setAttribute(USERNAME_SESSION_ATTRIB_NAME, loggedInUser.getUsername());
        session.setAttribute(FIRST_NAME_PARAM, loggedInUser.getFirstName());
        session.setAttribute(LAST_NAME_PARAM, loggedInUser.getLastName());
        session.setAttribute(EMAIL_PARAM, loggedInUser.getEmail());
        session.setAttribute(BLOCK_STATUS_PARAM, loggedInUser.isBlocked());
        session.setAttribute(USER_ROLE_SESSION_ATTRIB_NAME, loggedInUser.getRole());
        session.setAttribute(PHONE_NUMBER_PARAM, loggedInUser.getPhoneNumber());

        return LOGIN_SUCCESS_RESPONSE;
    }
}
