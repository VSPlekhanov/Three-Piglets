package org.piglets.botapi.command_handlers;

import org.piglets.entity.User;
import org.piglets.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;


import java.time.Clock;
import java.time.Instant;
import java.util.List;

import static org.piglets.botapi.BotState.CHOOSE_PARTNER;
import static org.piglets.utils.CommonMessageUtils.callBackAnswer;
import static org.piglets.static_data.Messages.greetingMessage;
import static org.piglets.static_data.Messages.toBegin;

@Component
public class StartCommandHandler implements InputCommandHandler {

    @Autowired
    private UserService userService;

    @Override
    public List<BotApiMethod<?>> handle(Update update, User user) {
        Message message = update.getMessage();
        Long userId = message.getFrom().getId();
        if (userService.findUser(userId).isEmpty()) {
            Instant now = Clock.systemUTC().instant();
            userService.save(
                    new User(userId,
                            message.getChatId(),
                            null,
                            "ru",
                            CHOOSE_PARTNER,
                            List.of(),
                            now,
                            now,
                            null));
            return List.of(
                    callBackAnswer(message.getChatId(), greetingMessage()),
                    callBackAnswer(message.getChatId(), toBegin()),
                    callBackAnswer(message.getChatId(), userId.toString())
            );
        }
        return List.of(callBackAnswer(message.getChatId(), greetingMessage()));
    }

    @Override
    public String getCommand() {
        return "/start";
    }

}

