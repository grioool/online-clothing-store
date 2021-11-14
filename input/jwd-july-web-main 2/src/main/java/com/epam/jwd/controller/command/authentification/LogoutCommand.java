package com.epam.jwd.controller.command.authentification;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.CommandRequest;
import com.epam.jwd.controller.command.CommandResponse;

public enum LogoutCommand implements Command {
    INSTANCE;

    private static final CommandResponse MAIN_PAGE_REDIRECT = new CommandResponse() {
        @Override
        public String getPath() {
            return "/index.jsp";
        }

        @Override
        public boolean isRedirect() {
            return true;
        }
    };

    @Override
    public CommandResponse execute(CommandRequest request) {
        request.invalidateCurrentSession();
        return MAIN_PAGE_REDIRECT;
    }
}
