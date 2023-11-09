package org.piglets.botapi.message_handlers;

import org.piglets.botapi.BotState;
import org.piglets.entity.Piglet;
import org.piglets.entity.User;
import org.piglets.service.UserService;
import org.piglets.static_data.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.Clock;
import java.util.List;

import static org.piglets.botapi.BotState.*;
import static org.piglets.entity.Piglet.BLACK;
import static org.piglets.entity.Piglet.WHITE;
import static org.piglets.static_data.Buttons.*;
import static org.piglets.static_data.Keyboards.*;
import static org.piglets.static_data.Messages.*;
import static org.piglets.static_data.Messages.whitePigletChosen;
import static org.piglets.utils.CommonMessageUtils.callBackAnswer;
import static org.piglets.utils.CommonMessageUtils.deleteMessage;

@Component
public class BreakItHandler implements InputMessageHandler {

    @Autowired
    private UserService userService;


    @Override
    public List<PartialBotApiMethod<?>> handle(Update update, User user) {
        String text = messageText(update);
        if (BACK.equals(text)) {
            userService.saveBotState(user.getId(), MAIN_MENU);
            return List.of(callBackAnswer(user.getChatId(), mainMenu(), mainMenuKeyboard()));
        }
        if (!BREAK.equals(text)) {
            return List.of(callBackAnswer(user.getChatId(), failedToProcess(), breakItKeyboard()));
        }
        Piglet currentPiglet = user.getCurrentPiglet();
        if (currentPiglet == null) {
            userService.saveBotState(user.getId(), MAIN_MENU);
            return List.of(callBackAnswer(user.getChatId(), stateChangedError(), mainMenuKeyboard()));
        }

        userService.saveBotState(user.getId(), AWAITING_BREAK_CONFIRMATION);
        var partner = userService.getUser(user.getPartnerId());
        var requestToPartner = callBackAnswer(partner.getChatId(),
                breakRequest(currentPiglet),
                breakInboundRequestKeyboard(currentPiglet));
        var answerToUser = callBackAnswer(user.getId(),
                waitingForBreakRequestApproval(),
                breakOutboundRequestKeyboard());
        return List.of(requestToPartner, answerToUser);
    }

    @Override
    public BotState getBotState() {
        return PIGLET_BREAK;
    }
}

