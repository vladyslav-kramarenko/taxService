package com.my.kramarenko.taxService.web.command;

import com.my.kramarenko.taxService.web.command.admin.DeleteUserCommand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandContainerTest {

    @Test
    void getCommand() {
        Command command = CommandContainer.getCommand("deleteUser");
        assertEquals(DeleteUserCommand.class, command.getClass());
    }
}