package com.epam.training.jwd.online.shop.controller.filter;

import com.epam.training.jwd.online.shop.controller.command.Command;
import com.epam.training.jwd.online.shop.controller.command.marker.AdminCommand;
import com.epam.training.jwd.online.shop.controller.command.marker.UserCommand;
import com.epam.training.jwd.online.shop.controller.constants.RequestConstant;
import com.epam.training.jwd.online.shop.dao.entity.UserRole;
import com.epam.training.jwd.online.shop.service.dto.UserDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "CommandFilter",
        initParams = {@WebInitParam(name = "command", value = "to_access_blocked")})
public class CommandFilter implements Filter {

    private static final Logger LOGGER = LogManager.getLogger(CommandFilter.class);
    private String accessBlockedCommand;

    @Override
    public void init(FilterConfig filterConfig) {
        accessBlockedCommand = filterConfig.getServletContext().getContextPath()
                + filterConfig.getInitParameter(RequestConstant.COMMAND);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        UserDto user = (UserDto) session.getAttribute(RequestConstant.USER);

        if (isUserHasAccess(request, user)) {
            filterChain.doFilter(request, response);
        } else {
            LOGGER.warn("Access blocked to user");
            response.sendRedirect(request.getServletPath() + "?command=" + accessBlockedCommand);
        }
    }

    @Override
    public void destroy() {
        accessBlockedCommand = null;
    }

    private boolean isUserHasAccess(HttpServletRequest request, UserDto user) {
        boolean isHasAccess = true;
        String commandName = request.getParameter(RequestConstant.COMMAND);

        if (commandName != null) {
            Command command = Command.withName(commandName.toUpperCase());
            if (user == null && (command instanceof UserCommand
                    || command instanceof AdminCommand)) {
                isHasAccess = false;
            }
            if (user != null && command instanceof AdminCommand
                    && user.getRole().equals(UserRole.USER)) {
                isHasAccess = false;
            }
        }
        return isHasAccess;
    }
}
