package com.epam.training.jwd.online.shop.controller;


import com.epam.training.jwd.online.shop.controller.command.*;
import com.epam.training.jwd.online.shop.controller.constants.RequestConstant;
import com.epam.training.jwd.online.shop.dao.exception.EntityNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

/**
 * The class provide entry point for all requests
 * @author Olga Grigorieva
 * @version 1.0.0
 */

@WebServlet(urlPatterns = "/controller")
public class ApplicationController extends HttpServlet {

    public static final String COMMAND_PARAM_NAME = "command";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        RequestContext requestContext = new RequestContext(req);
        String commandName = req.getParameter(RequestConstant.COMMAND);
        Command command = Command.withName(commandName);

        ResponseContext responseContext = command.execute(requestContext);
        responseContext.getRequestAttributes().forEach(req::setAttribute);
        responseContext.getSessionAttributes().forEach(req.getSession()::setAttribute);

        if (responseContext.getRequestAttributes().containsKey(RequestConstant.LOGOUT)) {
            req.getSession().invalidate();
            responseContext.getRequestAttributes().remove(RequestConstant.LOGOUT);
        }
        chooseResponseType(req, resp, responseContext);
    }

    private void chooseResponseType(HttpServletRequest req, HttpServletResponse resp, ResponseContext responseContext) throws IOException, ServletException {
        ResponseType responseType = responseContext.getResponseType();

        switch (responseType.getType()) {
            case REDIRECT:
                resp.sendRedirect(req.getContextPath() + ((RedirectResponseType) responseType).getCommand());
                break;
            case FORWARD:
                req.getRequestDispatcher(((ForwardResponseType) responseType).getPage()).forward(req, resp);
                break;
            case REST:
                resp.getWriter().write(new ObjectMapper().writeValueAsString(responseContext.getRequestAttributes()));
                break;
        }
    }

}