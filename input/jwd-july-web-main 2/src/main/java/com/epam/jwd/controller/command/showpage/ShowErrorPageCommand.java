package com.epam.jwd.controller.command.showpage;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.CommandRequest;
import com.epam.jwd.controller.command.CommandResponse;

public enum ShowErrorPageCommand implements Command {
    INSTANCE;

    private static final String ERROR_PAGE_PATH = "/WEB-INF/jsp/error.jsp";

    private static final CommandResponse ERROR_PAGE_RESPONSE = new CommandResponse() {
        @Override
        public String getPath() {
            return ERROR_PAGE_PATH;
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    @Override
    public CommandResponse execute(CommandRequest request) {
        return ERROR_PAGE_RESPONSE;
    }
}
