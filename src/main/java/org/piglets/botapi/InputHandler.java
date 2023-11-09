package org.piglets.botapi;

import org.piglets.entity.User;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public interface InputHandler {

    List<PartialBotApiMethod<?>> handle(Update update, User user);

    boolean isApplicable(Update update, User user);
}