package org.piglets.botapi.callback_handlers;

import org.piglets.entity.Piglet;
import org.piglets.entity.User;
import org.piglets.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

import static org.piglets.botapi.BotState.*;
import static org.piglets.static_data.Callbacks.ADD_NOTE_CALLBACK;
import static org.piglets.static_data.Messages.*;
import static org.piglets.utils.CommonMessageUtils.callBackAnswer;

@Component
public class AddNoteCallbackHandler implements InputCallbackHandler {

    @Autowired
    private UserService userService;

    @Override
    public List<PartialBotApiMethod<?>> handle(Update update, User user) {
        Piglet piglet = Piglet.valueOf(callbackValue(update));
        user.setBotState(PIGLET_WRITE);
        user.setCurrentPiglet(piglet);
        userService.save(user);
        return List.of(callBackAnswer(user.getChatId(), writeNote()));
    }

    @Override
    public boolean isApplicable(String callbackData) {
        return callbackData.startsWith(ADD_NOTE_CALLBACK.toString());
    }
}

