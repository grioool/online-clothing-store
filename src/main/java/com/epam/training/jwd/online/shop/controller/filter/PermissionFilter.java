package com.epam.training.jwd.online.shop.controller.filter;

import com.epam.training.jwd.online.shop.controller.command.ApplicationCommand;
import com.epam.training.jwd.online.shop.dao.entity.UserRole;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

import static com.epam.training.jwd.online.shop.controller.ApplicationController.COMMAND_PARAM_NAME;
import static com.epam.training.jwd.online.shop.controller.command.authorization.LoginCommand.USER_ROLE_SESSION_ATTRIB_NAME;
import static com.epam.training.jwd.online.shop.dao.entity.UserRole.UNAUTHORIZED;



@WebFilter(urlPatterns = "/*")
public class PermissionFilter implements Filter {

    private static final String ERROR_PAGE_LOCATION = "/controller?command=error";

    private final Map<UserRole, Set<ApplicationCommand>> commandsByRoles;

    public PermissionFilter() {
        commandsByRoles = new EnumMap<>(UserRole.class);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        for (ApplicationCommand command : ApplicationCommand.values()) {
            for (UserRole allowedRole : command.getAllowedRoles()) {
                Set<ApplicationCommand> commands = commandsByRoles.computeIfAbsent(allowedRole, k -> EnumSet.noneOf(ApplicationCommand.class));
                commands.add(command);
            }
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest req = (HttpServletRequest) request;
        final ApplicationCommand command = ApplicationCommand.of(req.getParameter(COMMAND_PARAM_NAME));
        final HttpSession session = req.getSession(false);
        UserRole currentRole = extractRoleFromSession(session);
        final Set<ApplicationCommand> allowedCommands = commandsByRoles.get(currentRole);
        if (allowedCommands.contains(command)) {
            chain.doFilter(request, response);
        } else {
            ((HttpServletResponse) response).sendRedirect(ERROR_PAGE_LOCATION);
        }
    }

    private UserRole extractRoleFromSession(HttpSession session) {
        return session != null && session.getAttribute(USER_ROLE_SESSION_ATTRIB_NAME) != null
                ? (UserRole) session.getAttribute(USER_ROLE_SESSION_ATTRIB_NAME)
                : UNAUTHORIZED;
    }
}
