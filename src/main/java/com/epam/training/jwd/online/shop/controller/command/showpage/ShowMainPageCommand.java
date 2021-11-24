package com.epam.training.jwd.online.shop.controller.command.showpage;

import com.epam.training.jwd.online.shop.controller.command.Command;
import com.epam.training.jwd.online.shop.controller.command.CommandRequest;
import com.epam.training.jwd.online.shop.controller.command.CommandResponse;

public class ShowMainPageCommand implements Command {

    private static ShowMainPageCommand instance;

    private ShowMainPageCommand() {
    }

    public static ShowMainPageCommand getInstance() {
        ShowMainPageCommand localInstance = instance;
        if (localInstance == null) {
            synchronized (ShowAuthorizationPageCommand.class) {
                if (localInstance == null) {
                    instance = localInstance = new ShowMainPageCommand();
                }
            }
        }
        return localInstance;
    }


    private static final String MAIN_PAGE_PATH = "/WEB-INF/jsp/user/mainPage.jsp";

    private static final CommandResponse SHOW_MAIN_PAGE = new CommandResponse() {
        @Override
        public String getPath() {
            return MAIN_PAGE_PATH;
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    @Override
    public CommandResponse execute(CommandRequest request) {
        return SHOW_MAIN_PAGE;
    }
}