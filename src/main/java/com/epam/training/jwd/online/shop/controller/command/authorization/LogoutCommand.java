package com.epam.training.jwd.online.shop.controller.command.authorization;


import com.epam.training.jwd.online.shop.controller.command.Command;
import com.epam.training.jwd.online.shop.controller.command.CommandRequest;
import com.epam.training.jwd.online.shop.controller.command.CommandResponse;

public class LogoutCommand implements Command {

    private static volatile LogoutCommand instance;

    public static LogoutCommand getInstance() {
        LogoutCommand localInstance = instance;
        if (localInstance == null) {
            synchronized (LogoutCommand.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new LogoutCommand();
                }
            }
        }
        return localInstance;
    }

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
