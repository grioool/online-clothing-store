package com.epam.training.jwd.online.shop.controller.command;


import com.epam.training.jwd.online.shop.controller.command.authorization.LoginCommand;
import com.epam.training.jwd.online.shop.controller.command.authorization.LogoutCommand;
import com.epam.training.jwd.online.shop.controller.command.showpage.ShowErrorPageCommand;
import com.epam.training.jwd.online.shop.controller.command.showpage.ShowAuthorizationPageCommand;
import com.epam.training.jwd.online.shop.controller.command.showpage.ShowMainPageCommand;
import com.epam.training.jwd.online.shop.controller.command.showpage.ShowUsersPageCommand;
import com.epam.training.jwd.online.shop.dao.entity.UserRole;

import java.util.Arrays;
import java.util.List;

import static com.epam.training.jwd.online.shop.dao.entity.UserRole.*;


public enum ApplicationCommand {
    MAIN_PAGE(ShowMainPageCommand.INSTANCE),
    SHOW_USERS(ShowUsersPageCommand.INSTANCE, ADMIN),
    AUTHORIZATION(LoginCommand.INSTANCE, UNAUTHORIZED),
    SHOW_AUTHORIZATION(ShowAuthorizationPageCommand.INSTANCE, UNAUTHORIZED),
    LOGOUT(LogoutCommand.INSTANCE, USER, ADMIN),
    ERROR(ShowErrorPageCommand.INSTANCE),
    DEFAULT(ShowMainPageCommand.INSTANCE);

    private final Command command;
    private final List<UserRole> allowedRoles;

    ApplicationCommand(Command command, UserRole... roles) {
        this.command = command;
        this.allowedRoles = roles != null && roles.length > 0 ? Arrays.asList(roles) : UserRole.valuesAsList();
    }

    public Command getCommand() {
        return command;
    }

    public List<UserRole> getAllowedRoles() {
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
