package org.piglets.botapi.callback_handlers;

import com.google.common.collect.Streams;
import org.piglets.entity.User;
import org.piglets.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Optional;

import static org.piglets.botapi.BotState.CHOOSE_PARTNER;
import static org.piglets.static_data.Callbacks.REJECT_PARTNERSHIP_CALLBACK;
import static org.piglets.static_data.Messages.*;
import static org.piglets.utils.CommonMessageUtils.callBackAnswer;

@Component
public class PartnershipRejectingCallbackHandler implements InputCallbackHandler {

    @Autowired
    private UserService userService;

    @Override
    public List<BotApiMethod<?>> handle(Update update, User user) {
        String rejectedUserId = callbackValue(update);
        User rejectedUser = userService.getUser(Long.valueOf(rejectedUserId));
        Long rejectedUserPartner = rejectedUser.getPartnerId();
        if (rejectedUserPartner == user.getId()) {
            rejectedUser.setPartnerId(null);
        }
        rejectedUser.setBotState(CHOOSE_PARTNER);
        userService.save(rejectedUser);

        BotApiMethod<?> rejectSuccessful = callBackAnswer(user.getChatId(), rejectedSuccess());
        BotApiMethod<?> youAreRejected = callBackAnswer(Long.valueOf(callbackValue(update)),
                partnershipRequestRejected(String.valueOf(user.getId())));
        BotApiMethod<?> toBegin = callBackAnswer(user.getChatId(), toBegin());
        BotApiMethod<?> userIdMessage = callBackAnswer(user.getChatId(), String.valueOf(user.getId()));

        return Streams.concat(
                        Optional.of(rejectSuccessful).stream(),
                        Optional.of(youAreRejected).filter(__ -> rejectedUserPartner == user.getId()).stream(),
                        Optional.of(toBegin).filter(__ -> user.getPartnerId() == null).stream(),
                        Optional.of(userIdMessage).filter(__ -> user.getPartnerId() == null).stream())
                .toList();
    }

    @Override
    public boolean isApplicable(String callbackData) {
        return callbackData.startsWith(REJECT_PARTNERSHIP_CALLBACK.toString());
    }
}

