package org.piglets.botapi;

import org.piglets.entity.User;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.stream.Stream;

public interface InputHandler {

    List<BotApiMethod<?>> handle(Update update, User user);

    boolean isApplicable(Update update, User user);
}