package org.piglets.botapi.callback_handlers;

import org.piglets.entity.User;
import org.piglets.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

import static org.piglets.botapi.BotState.*;
import static org.piglets.static_data.Callbacks.CANCEL_PARTNERSHIP_CALLBACK;
import static org.piglets.utils.CommonMessageUtils.callBackAnswer;
import static org.piglets.static_data.Messages.cancelPartnershipRequestSuccess;
import static org.piglets.static_data.Messages.stateChangedError;

@Component
public class PartnershipRequestCancellingCallbackHandler implements InputCallbackHandler {

    @Autowired
    private UserService userService;

    @Override
    public List<BotApiMethod<?>> handle(Update update, User user) {
        Long partnerId = userService.getUser(user.getId()).getPartnerId();;
        if (user.getBotState() != AWAITING_PARTNERSHIP_CONFIRMATION || partnerId == null) {
            return List.of(callBackAnswer(user.getChatId(), stateChangedError()));
        }
        userService.savePartner(user.getId(), null);
        userService.saveBotState(user.getId(), CHOOSE_PARTNER);

        return List.of(
                callBackAnswer(user.getChatId(), cancelPartnershipRequestSuccess()),
                callBackAnswer(user.getChatId(), String.valueOf(user.getId()))
        );
    }

    @Override
    public boolean isApplicable(String callbackData) {
        return callbackData.equals(CANCEL_PARTNERSHIP_CALLBACK.toString());
    }
}

