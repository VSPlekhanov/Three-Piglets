package org.piglets.botapi.command_handlers;

import org.piglets.botapi.BotState;
import org.piglets.botapi.InputHandler;
import org.piglets.entity.User;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.stream.Stream;

public interface InputCommandHandler extends InputHandler {
    default boolean isApplicable(Update update, User user) {
        return update != null
                && update.hasMessage()
                && update.getMessage().hasText()
                && update.getMessage().getText().startsWith(getCommand());
    }

    String getCommand();
}