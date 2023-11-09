package org.piglets.botapi.message_handlers;

import org.piglets.botapi.BotState;
import org.piglets.entity.User;
import org.piglets.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

import static org.piglets.botapi.BotState.*;
import static org.piglets.static_data.Keyboards.partnershipOutboundRequestKeyboard;
import static org.piglets.utils.CommonMessageUtils.callBackAnswer;
import static org.piglets.static_data.Keyboards.partnershipInboundRequestKeyboard;
import static org.piglets.static_data.Messages.*;

@Component
public class LinkPartnerHandler implements InputMessageHandler {

    @Autowired
    private UserService userService;

    @Override
    public List<PartialBotApiMethod<?>> handle(Update update, User user) {
        String partnerIdString = messageText(update);
        try {
            Long partnerId = Long.valueOf(partnerIdString);
            if (partnerId.equals(user.getId())) {
                return List.of(callBackAnswer(user.getChatId(), partnershipRequestMyself()));
            }

            var maybePartner = userService.findUser(partnerId);
            if (maybePartner.isEmpty()) {
                return List.of(callBackAnswer(user.getChatId(), cantFindByIdMessage(partnerIdString)));
            }
            
            User partner = maybePartner.get();
            if (partner.getPartnerId() != null) {
                return List.of(callBackAnswer(user.getChatId(), partnerIsAlreadyTaken(partnerIdString)));
            }
            
            userService.savePartner(user.getId(), partnerId);
            userService.saveBotState(user.getId(), AWAITING_PARTNERSHIP_CONFIRMATION);
            var requestToPartner = callBackAnswer(partner.getChatId(),
                    partnershipRequestMessage(String.valueOf(user.getId())),
                    partnershipInboundRequestKeyboard(String.valueOf(user.getId())));
            var answerToUser = callBackAnswer(user.getId(),
                    waitingForPartnershipRequestApproval(),
                    partnershipOutboundRequestKeyboard());
            return List.of(requestToPartner, answerToUser);
            
        } catch (NumberFormatException e) {
            return List.of(callBackAnswer(user.getChatId(), wrongIdMessage(partnerIdString)));
        }
    }

    @Override
    public BotState getBotState() {
        return CHOOSE_PARTNER;
    }
}

