package com.epam.training.jwd.online.shop.controller.command.showpage;

import com.epam.training.jwd.online.shop.controller.command.Command;
import com.epam.training.jwd.online.shop.controller.command.CommandRequest;
import com.epam.training.jwd.online.shop.controller.command.CommandResponse;
import com.epam.training.jwd.online.shop.dao.entity.User;
import com.epam.training.jwd.online.shop.service.dto.UserService;

import java.util.List;

public enum ShowUsersPageCommand implements Command {
    INSTANCE;

    private static final String USERS_ATTRIBUTE_NAME = "users";
    private static final String LANGUAGE_PARAM = "language";
    private static final String USERS_PAGE_PATH = "/WEB-INF/jsp/user/usersPage.jsp";

    private static final CommandResponse SHOW_USERS_PAGE_RESPONSE = new CommandResponse() {
        @Override
        public String getPath() {
            return USERS_PAGE_PATH;
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    private final UserService service;

    ShowUsersPageCommand() {
        this.service = UserService.simple();
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        String language = request.getParameter(LANGUAGE_PARAM);

        final List<User> users = service.findAll();
        request.setAttribute(USERS_ATTRIBUTE_NAME, users);
        request.setAttribute(LANGUAGE_PARAM, language);
        return SHOW_USERS_PAGE_RESPONSE;
    }
}
