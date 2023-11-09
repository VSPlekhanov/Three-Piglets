package org.piglets.botapi.callback_handlers;

import org.piglets.botapi.InputHandler;
import org.piglets.entity.User;
import org.piglets.static_data.Callbacks;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface InputCallbackHandler extends InputHandler {

    default boolean isApplicable(Update update, User user) {
        return update != null
                && update.hasCallbackQuery()
                && update.getCallbackQuery().getData() != null
                && isApplicable(callbackData(update));
    }

    boolean isApplicable(String callbackData);

    default String callbackData(Update update) {
        return update.getCallbackQuery().getData();
    }

    default String callbackValue(Update update) {
        return Callbacks.getValue(callbackData(update));
    }
}