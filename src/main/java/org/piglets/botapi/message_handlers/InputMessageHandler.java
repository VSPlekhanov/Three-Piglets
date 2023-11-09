package org.piglets.botapi.message_handlers;

import org.piglets.botapi.BotState;
import org.piglets.botapi.InputHandler;
import org.piglets.entity.User;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface InputMessageHandler extends InputHandler {
    BotState getBotState();

    default boolean isApplicable(Update update, User user) {
        return update != null
                && user != null
                && update.hasMessage()
                && getBotState().equals(user.getBotState());
    }

    default String messageText(Update update) {
        return update.getMessage().getText();
    }
}