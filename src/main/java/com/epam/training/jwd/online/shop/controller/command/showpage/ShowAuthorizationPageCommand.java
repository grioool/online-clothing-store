package com.epam.training.jwd.online.shop.controller.command.showpage;


import com.epam.training.jwd.online.shop.controller.command.Command;
import com.epam.training.jwd.online.shop.controller.command.CommandRequest;
import com.epam.training.jwd.online.shop.controller.command.CommandResponse;

public enum ShowAuthorizationPageCommand implements Command {
    INSTANCE;

    private static final String LOGIN_PAGE_PATH = "/WEB-INF/jsp/login.jsp";

    private static final CommandResponse LOGIN_PAGE_RESPONSE = new CommandResponse() {
        @Override
        public String getPath() {
            return "/WEB-INF/jsp/login.jsp";
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
