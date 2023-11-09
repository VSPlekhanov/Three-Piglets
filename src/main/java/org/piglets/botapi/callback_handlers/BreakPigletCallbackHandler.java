package org.piglets.botapi.callback_handlers;

import org.piglets.entity.Piglet;
import org.piglets.entity.User;
import org.piglets.service.UserService;
import org.piglets.static_data.Keyboards;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

import static org.piglets.botapi.BotState.PIGLET_BREAK;
import static org.piglets.botapi.BotState.PIGLET_WRITE;
import static org.piglets.static_data.Callbacks.ADD_NOTE_CALLBACK;
import static org.piglets.static_data.Callbacks.BREAK_PIGLET_CALLBACK;
import static org.piglets.static_data.Keyboards.breakItKeyboard;
import static org.piglets.static_data.Messages.breakIt;
import static org.piglets.static_data.Messages.writeNote;
import static org.piglets.utils.CommonMessageUtils.callBackAnswer;

@Component
public class BreakPigletCallbackHandler implements InputCallbackHandler {

    @Autowired
    private UserService userService;

    @Override
    public List<BotApiMethod<?>> handle(Update update, User user) {
        Piglet piglet = Piglet.valueOf(callbackValue(update));
        user.setBotState(PIGLET_BREAK);
        user.setCurrentPiglet(piglet);
        userService.save(user);
        return List.of(callBackAnswer(user.getChatId(), breakIt(), breakItKeyboard()));
    }

    @Override
    public boolean isApplicable(String callbackData) {
        return callbackData.startsWith(BREAK_PIGLET_CALLBACK.toString());
    }
}

