package com.epam.jwd.controller.command;

import com.epam.jwd.controller.command.authentification.LoginCommand;
import com.epam.jwd.controller.command.authentification.LogoutCommand;
import com.epam.jwd.controller.command.showpage.ShowErrorPageCommand;
import com.epam.jwd.controller.command.showpage.ShowLoginPageCommand;
import com.epam.jwd.controller.command.showpage.ShowMainPageCommand;
import com.epam.jwd.controller.command.showpage.ShowUsersPageCommand;
import com.epam.jwd.dao.model.Role;

import java.util.Arrays;
import java.util.List;

import static com.epam.jwd.dao.model.Role.ADMIN;
import static com.epam.jwd.dao.model.Role.UNAUTHORIZED;
import static com.epam.jwd.dao.model.Role.USER;

public enum ApplicationCommand {
    MAIN_PAGE(ShowMainPageCommand.INSTANCE),
    SHOW_USERS(ShowUsersPageCommand.INSTANCE, ADMIN),
    LOGIN(LoginCommand.INSTANCE, UNAUTHORIZED),
    SHOW_LOGIN(ShowLoginPageCommand.INSTANCE, UNAUTHORIZED),
    LOGOUT(LogoutCommand.INSTANCE, USER, ADMIN),
    ERROR(ShowErrorPageCommand.INSTANCE),
    DEFAULT(ShowMainPageCommand.INSTANCE);

    private final Command command;
    private final List<Role> allowedRoles;

    ApplicationCommand(Command command, Role... roles) {
        this.command = command;
        this.allowedRoles = roles != null && roles.length > 0 ? Arrays.asList(roles) : Role.valuesAsList();
    }

    public Command getCommand() {
        return command;
    }

    public List<Role> getAllowedRoles() {
        return allowedRoles;
    }

    public static ApplicationCommand of(String name) {
        for (ApplicationCommand command : values()) {
            if (command.name().equalsIgnoreCase(name)) {
                return command;
            }
        }
        return DEFAULT;
    }
}
