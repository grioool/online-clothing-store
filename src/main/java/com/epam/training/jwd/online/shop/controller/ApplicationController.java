package com.epam.training.jwd.online.shop.controller;


import com.epam.training.jwd.online.shop.controller.command.Command;
import com.epam.training.jwd.online.shop.controller.command.CommandRequest;
import com.epam.training.jwd.online.shop.controller.command.CommandResponse;
import com.epam.training.jwd.online.shop.dao.exception.EntityNotFoundException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@WebServlet(urlPatterns = "/controller")
public class ApplicationController extends HttpServlet {

    public static final String COMMAND_PARAM_NAME = "command";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            process(req, resp);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            process(req, resp);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, EntityNotFoundException {
        System.out.println(this.toString());

        final String commandName = req.getParameter(COMMAND_PARAM_NAME);
        final Command command = Command.withName(commandName);
        final CommandResponse response = command.execute(new CommandRequest() {
            @Override
            public HttpSession createSession() {
                return req.getSession(true);
            }

            @Override
            public Optional<HttpSession> getCurrentSession() {
                return Optional.ofNullable(req.getSession(false));
            }

            @Override
            public void invalidateCurrentSession() {
                final HttpSession session = req.getSession(false);
                if (session != null) {
                    session.invalidate();
                }
            }

            @Override
            public String getParameter(String name) {
                return req.getParameter(name);
            }

            @Override
            public void setAttribute(String name, Object value) {
                req.setAttribute(name, value);
            }
        });
        if (response.isRedirect()) {
            resp.sendRedirect(response.getPath());
        } else {
            final RequestDispatcher dispatcher = req.getRequestDispatcher(response.getPath());
            dispatcher.forward(req, resp);
        }

    }

}

