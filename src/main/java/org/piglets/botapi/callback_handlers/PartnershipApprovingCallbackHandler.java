package org.piglets.botapi.callback_handlers;

import org.piglets.entity.User;
import org.piglets.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Objects;

import static org.piglets.botapi.BotState.MAIN_MENU;
import static org.piglets.static_data.Callbacks.APPROVE_PARTNERSHIP_CALLBACK;
import static org.piglets.static_data.Keyboards.mainMenuKeyboard;
import static org.piglets.utils.CommonMessageUtils.callBackAnswer;
import static org.piglets.static_data.Messages.*;

@Component
public class PartnershipApprovingCallbackHandler implements InputCallbackHandler {

    @Autowired
    private UserService userService;

    @Override
    public List<PartialBotApiMethod<?>> handle(Update update, User user) {
        String partnerIdString = callbackValue(update);
        if (user.getPartnerId() != null) {
            return List.of(callBackAnswer(user.getChatId(), youAlreadyHavePartner(user.getPartnerId().toString())));
        }

        var potentialPartner = userService.getUser(Long.valueOf(partnerIdString));
        if (!Objects.equals(potentialPartner.getPartnerId(), user.getId())) {
            return List.of(callBackAnswer(user.getChatId(), partnershipRequestHaveBeenCancelled(partnerIdString)));
        }

        userService.savePartner(user.getId(), potentialPartner.getId());
        userService.saveBotState(user.getId(), MAIN_MENU);
        userService.saveBotState(potentialPartner.getId(), MAIN_MENU);
        return List.of(
                callBackAnswer(user.getChatId(), partnershipRequestApproved(partnerIdString), mainMenuKeyboard()),
                callBackAnswer(potentialPartner.getChatId(), partnershipRequestApproved(String.valueOf(user.getId())), mainMenuKeyboard())
        );
    }

    @Override
    public boolean isApplicable(String callbackData) {
        return callbackData.startsWith(APPROVE_PARTNERSHIP_CALLBACK.toString());
    }
}

