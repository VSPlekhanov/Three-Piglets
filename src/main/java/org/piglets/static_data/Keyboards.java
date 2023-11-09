package org.piglets.static_data;

import org.piglets.entity.Piglet;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.piglets.static_data.Buttons.*;
import static org.piglets.static_data.Callbacks.*;
import static org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton.builder;

public class Keyboards {

    public static InlineKeyboardMarkup partnershipInboundRequestKeyboard(String userId) {
        return new InlineKeyboardMarkup(List.of(List.of(
                builder().text("Принять").callbackData(APPROVE_PARTNERSHIP_CALLBACK.setValue(userId)).build(),
                builder().text("Отклонить").callbackData(REJECT_PARTNERSHIP_CALLBACK.setValue(userId)).build()
        )));
    }

    public static InlineKeyboardMarkup partnershipOutboundRequestKeyboard() {
        return new InlineKeyboardMarkup(List.of(List.of(
                builder().text("Отменить").callbackData(CANCEL_PARTNERSHIP_CALLBACK.toString()).build()
        )));
    }

    public static InlineKeyboardMarkup breakInboundRequestKeyboard(Piglet piglet) {
        return new InlineKeyboardMarkup(List.of(List.of(
                builder().text("Принять").callbackData(APPROVE_BREAK_CALLBACK.setValue(piglet.toString())).build(),
                builder().text("Отклонить").callbackData(REJECT_BREAK_CALLBACK.setValue(piglet.toString())).build()
        )));
    }

    public static InlineKeyboardMarkup breakOutboundRequestKeyboard() {
        return new InlineKeyboardMarkup(List.of(List.of(
                builder().text("Отменить").callbackData(CANCEL_BREAK_CALLBACK.toString()).build()
        )));
    }

    private static ReplyKeyboardMarkup replyKeyboardMarkup(String ... buttons) {
        List<KeyboardRow> rows = Arrays.stream(buttons)
                .map(button -> new KeyboardRow(List.of(new KeyboardButton(button))))
                .toList();

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(rows);
        replyKeyboardMarkup.setResizeKeyboard(true);
        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup mainMenuKeyboard() {
        return replyKeyboardMarkup(BLACK_PIGLET, WHITE_PIGLET, PINK_PIGLET);
    }

    public static ReplyKeyboardMarkup settingsMenuKeyboard() {
        return replyKeyboardMarkup(REMOVE_PARTNER, ABOUT, BACK);
    }

    public static ReplyKeyboardMarkup breakItKeyboard() {
        return replyKeyboardMarkup(BREAK, BACK);
    }

    public static InlineKeyboardMarkup pigletKeyboard(Piglet piglet) {
        return new InlineKeyboardMarkup(List.of(List.of(
                builder().text(ADD_NOTE).callbackData(ADD_NOTE_CALLBACK.setValue(piglet.toString())).build(),
                builder().text(BREAK).callbackData(BREAK_PIGLET_CALLBACK.setValue(piglet.toString())).build()
        )));
    }

}
