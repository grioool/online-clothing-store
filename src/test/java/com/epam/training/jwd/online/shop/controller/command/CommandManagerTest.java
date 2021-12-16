package com.epam.training.jwd.online.shop.controller.command;

import com.epam.training.jwd.online.shop.controller.command.impl.RegistrationCommand;
import com.epam.training.jwd.online.shop.controller.command.impl.ToNotFoundPageCommand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class CommandManagerTest {

    @Test
    public void of_ShouldReturnRegistrationCommand() {
        String command = CommandManager.REGISTRATION.getCommandName();

        assertTrue(CommandManager.of(command) instanceof RegistrationCommand);
    }

    @Test
    public void of_ShouldReturnToPageNotFoundCommand_WhenCommandIsNotFound() {
        String command = "command doesn't exist";

        assertTrue(CommandManager.of(command) instanceof ToNotFoundPageCommand);
    }
}