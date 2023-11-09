package org.piglets.botapi.callback_handlers;

import com.google.common.collect.Streams;
import org.piglets.entity.Piglet;
import org.piglets.entity.User;
import org.piglets.service.UserService;
import org.piglets.static_data.Keyboards;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Optional;

import static org.piglets.botapi.BotState.AWAITING_BREAK_CONFIRMATION;
import static org.piglets.botapi.BotState.MAIN_MENU;
import static org.piglets.static_data.Callbacks.REJECT_BREAK_CALLBACK;
import static org.piglets.static_data.Keyboards.mainMenuKeyboard;
import static org.piglets.static_data.Messages.*;
import static org.piglets.utils.CommonMessageUtils.callBackAnswer;

@Component
public class BreakRejectingCallbackHandler implements InputCallbackHandler {

    @Autowired
    private UserService userService;

    @Override
    public List<BotApiMethod<?>> handle(Update update, User user) {
        User partner = userService.getUser(user.getPartnerId());
        if (partner.getBotState() != AWAITING_BREAK_CONFIRMATION) {
            return List.of(callBackAnswer(user.getChatId(), rejectedSuccess(), mainMenuKeyboard()));
        }
        userService.saveBotState(partner.getId(), MAIN_MENU);
        return List.of(
                callBackAnswer(user.getChatId(), rejectedSuccess(), mainMenuKeyboard()),
                callBackAnswer(partner.getChatId(), breakRequestRejected(), mainMenuKeyboard())
        );
    }

    @Override
    public boolean isApplicable(String callbackData) {
        return callbackData.startsWith(REJECT_BREAK_CALLBACK.toString());
    }
}

