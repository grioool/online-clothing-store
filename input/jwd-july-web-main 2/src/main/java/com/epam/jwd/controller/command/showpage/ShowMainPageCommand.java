package com.epam.jwd.controller.command.showpage;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.CommandRequest;
import com.epam.jwd.controller.command.CommandResponse;

public enum ShowMainPageCommand implements Command {
    INSTANCE;

    private static final String MAIN_PAGE_PATH = "/WEB-INF/jsp/main.jsp";

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
