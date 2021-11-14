package com.epam.training.jwd.online.shop.controller.command.authorization;


import com.epam.training.jwd.online.shop.controller.command.Command;
import com.epam.training.jwd.online.shop.controller.command.CommandRequest;
import com.epam.training.jwd.online.shop.controller.command.CommandResponse;

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
