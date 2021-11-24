package com.epam.training.jwd.online.shop.controller.command.showpage;

import com.epam.training.jwd.online.shop.controller.command.Command;
import com.epam.training.jwd.online.shop.controller.command.CommandRequest;
import com.epam.training.jwd.online.shop.controller.command.CommandResponse;

public class ShowAuthorizationPageCommand implements Command {

    private static ShowAuthorizationPageCommand instance;

    private ShowAuthorizationPageCommand() {
    }

    public static ShowAuthorizationPageCommand getInstance() {
        ShowAuthorizationPageCommand localInstance = instance;
        if (localInstance == null) {
            synchronized (ShowAuthorizationPageCommand.class) {
                if (localInstance == null) {
                    instance = localInstance = new ShowAuthorizationPageCommand();
                }
            }
        }
        return localInstance;
    }

    private static final String AUTHORIZATION_PAGE_PATH = "/WEB-INF/jsp/user/authorization.jsp";

    private static final CommandResponse LOGIN_PAGE_RESPONSE = new CommandResponse() {
        @Override
        public String getPath() {
            return AUTHORIZATION_PAGE_PATH;
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    @Override
    public CommandResponse execute(CommandRequest request) {
        return LOGIN_PAGE_RESPONSE;
    }
}