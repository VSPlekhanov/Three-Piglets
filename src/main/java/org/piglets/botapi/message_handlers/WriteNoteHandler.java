package org.piglets.botapi.message_handlers;

import org.piglets.botapi.BotState;
import org.piglets.entity.Piglet;
import org.piglets.entity.User;
import org.piglets.service.UserService;
import org.piglets.static_data.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.Clock;
import java.time.Instant;
import java.util.List;

import static org.piglets.botapi.BotState.*;
import static org.piglets.entity.Piglet.*;
import static org.piglets.static_data.Buttons.*;
import static org.piglets.static_data.Keyboards.*;
import static org.piglets.static_data.Messages.*;
import static org.piglets.utils.CommonMessageUtils.callBackAnswer;
import static org.piglets.utils.CommonMessageUtils.deleteMessage;

@Component
public class WriteNoteHandler implements InputMessageHandler {

    @Autowired
    private UserService userService;


    @Override
    public List<BotApiMethod<?>> handle(Update update, User user) {
        Piglet currentPiglet = user.getCurrentPiglet();
        userService.saveBotState(user.getId(), MAIN_MENU);
        if (currentPiglet == null) {
            return List.of(callBackAnswer(user.getChatId(), stateChangedError(), mainMenuKeyboard()));
        }
        var message = new User.Message(currentPiglet, messageText(update), Instant.ofEpochSecond(update.getMessage().getDate()));
        userService.appendMessage(user.getId(), message);
        return List.of(
                callBackAnswer(user.getChatId(), writeSuccess(), mainMenuKeyboard()),
                deleteMessage(user.getChatId(), update.getMessage().getMessageId())
        );
    }

    @Override
    public BotState getBotState() {
        return PIGLET_WRITE;
    }
}

