package org.piglets.botapi.message_handlers;

import org.piglets.botapi.BotState;
import org.piglets.entity.User;
import org.piglets.static_data.Photos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

import static org.piglets.botapi.BotState.*;
import static org.piglets.entity.Piglet.*;
import static org.piglets.static_data.Buttons.BLACK_PIGLET;
import static org.piglets.static_data.Buttons.WHITE_PIGLET;
import static org.piglets.static_data.Buttons.PINK_PIGLET;
import static org.piglets.static_data.Buttons.SETTINGS;
import static org.piglets.static_data.Keyboards.pigletKeyboard;
import static org.piglets.static_data.Keyboards.settingsMenuKeyboard;
import static org.piglets.static_data.Messages.*;
import static org.piglets.utils.CommonMessageUtils.callBackAnswer;

@Component
public class MainMenuHandler implements InputMessageHandler {

    @Autowired
    private Photos photos;

    @Override
    public List<PartialBotApiMethod<?>> handle(Update update, User user) {
        String text = messageText(update);
        if (BLACK_PIGLET.equals(text)) {
            return List.of(
                    callBackAnswer(user.getChatId(), photos.getBlackPig()),
                    callBackAnswer(user.getChatId(), blackPigletChosen(), pigletKeyboard(BLACK))
            );
        }
        if (WHITE_PIGLET.equals(text)) {
            return List.of(
                    callBackAnswer(user.getChatId(), photos.getWhitePig()),
                    callBackAnswer(user.getChatId(), whitePigletChosen(), pigletKeyboard(WHITE))
            );
        }
        if (PINK_PIGLET.equals(text)) {
            return List.of(
                    callBackAnswer(user.getChatId(), photos.getPinkPig()),
                    callBackAnswer(user.getChatId(), pinkPigletChosen(), pigletKeyboard(PINK))
            );
        }
        if (SETTINGS.equals(text)) {
            return List.of(callBackAnswer(user.getChatId(), "", settingsMenuKeyboard()));
        }
        return List.of(callBackAnswer(user.getChatId(), failedToProcess()));
    }

    @Override
    public BotState getBotState() {
        return MAIN_MENU;
    }
}

