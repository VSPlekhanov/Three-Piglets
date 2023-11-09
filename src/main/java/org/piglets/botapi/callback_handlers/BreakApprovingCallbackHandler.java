package org.piglets.botapi.callback_handlers;

import org.piglets.botapi.BotState;
import org.piglets.entity.Piglet;
import org.piglets.entity.User;
import org.piglets.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.piglets.botapi.BotState.AWAITING_BREAK_CONFIRMATION;
import static org.piglets.botapi.BotState.MAIN_MENU;
import static org.piglets.static_data.Callbacks.APPROVE_BREAK_CALLBACK;
import static org.piglets.static_data.Callbacks.APPROVE_PARTNERSHIP_CALLBACK;
import static org.piglets.static_data.Keyboards.mainMenuKeyboard;
import static org.piglets.static_data.Messages.*;
import static org.piglets.utils.CommonMessageUtils.callBackAnswer;

@Component
public class BreakApprovingCallbackHandler implements InputCallbackHandler {

    @Autowired
    private UserService userService;

    @Override
    public List<PartialBotApiMethod<?>> handle(Update update, User user) {
        var piglet = Piglet.valueOf(callbackValue(update));
        var partner = userService.getUser(user.getPartnerId());
        user.setBotState(MAIN_MENU);
        if (partner.getBotState() != AWAITING_BREAK_CONFIRMATION ) {
            return List.of(callBackAnswer(user.getChatId(), breakAlreadyCancelled(), mainMenuKeyboard()));
        }
        partner.setBotState(MAIN_MENU);
        var userForUser = getMessagesFor(user, user.getChatId(), piglet);
        var partnerForUser = getMessagesFor(partner, user.getChatId(), piglet);
        var partnerForPartner = getMessagesFor(partner, partner.getChatId(), piglet);
        var userForPartner = getMessagesFor(user, partner.getChatId(), piglet);

        user.setMessages(user.getMessages().stream().filter(message -> !message.piglet().equals(piglet)).toList());
        partner.setMessages(partner.getMessages().stream().filter(message -> !message.piglet().equals(piglet)).toList());
        userService.save(user);
        userService.save(partner);

        var response = new ArrayList<PartialBotApiMethod<?>>();
        response.add(callBackAnswer(user.getChatId(), yourMessages(), mainMenuKeyboard()));
        response.add(callBackAnswer(partner.getChatId(), yourMessages(), mainMenuKeyboard()));
        response.addAll(userForUser);
        response.addAll(partnerForPartner);
        response.add(callBackAnswer(user.getChatId(), partnerMessages(), mainMenuKeyboard()));
        response.add(callBackAnswer(partner.getChatId(), partnerMessages(), mainMenuKeyboard()));
        response.addAll(partnerForUser);
        response.addAll(userForPartner);

        return response;
    }

    private List<SendMessage> getMessagesFor(User user, long chatId, Piglet piglet) {
        return user.getMessages().stream()
                .filter(message -> message.piglet().equals(piglet))
                .map(message -> callBackAnswer(chatId, decorateMessage(message), mainMenuKeyboard()))
                .toList();
    }

    @Override
    public boolean isApplicable(String callbackData) {
        return callbackData.startsWith(APPROVE_BREAK_CALLBACK.toString());
    }
}

