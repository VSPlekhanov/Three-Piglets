package org.piglets.botapi.callback_handlers;

import org.piglets.entity.User;
import org.piglets.service.UserService;
import org.piglets.static_data.Keyboards;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

import static org.piglets.botapi.BotState.*;
import static org.piglets.static_data.Callbacks.CANCEL_BREAK_CALLBACK;
import static org.piglets.static_data.Callbacks.CANCEL_PARTNERSHIP_CALLBACK;
import static org.piglets.static_data.Keyboards.mainMenuKeyboard;
import static org.piglets.static_data.Messages.*;
import static org.piglets.utils.CommonMessageUtils.callBackAnswer;

@Component
public class BreakRequestCancellingCallbackHandler implements InputCallbackHandler {

    @Autowired
    private UserService userService;

    @Override
    public List<BotApiMethod<?>> handle(Update update, User user) {
        if (user.getBotState() != AWAITING_BREAK_CONFIRMATION) {
            return List.of(callBackAnswer(user.getChatId(), stateChangedError()));
        }
        userService.saveBotState(user.getId(), MAIN_MENU);
        return List.of(callBackAnswer(user.getChatId(), cancelRequestSuccess(), mainMenuKeyboard()));
    }

    @Override
    public boolean isApplicable(String callbackData) {
        return callbackData.equals(CANCEL_BREAK_CALLBACK.toString());
    }
}

