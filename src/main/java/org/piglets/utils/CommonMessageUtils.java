package org.piglets.utils;

import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

public class CommonMessageUtils {

    public static Message createMessage(CallbackQuery buttonQuery) {
        Long clientId = buttonQuery.getFrom().getId();
        User user = new User();
        user.setId(clientId);
        Message message = new Message();
        message.setText(buttonQuery.getData());
        message.setMessageId(buttonQuery.getMessage().getMessageId());
        message.setCaption(buttonQuery.getId());
        message.setFrom(user);
        Chat chat = new Chat();
        chat.setId(clientId);
        message.setChat(chat);
        return message;
    }

    public static Message createPreviousMessage(Message msg, int deep) {
        return messageBase(
                msg.getFrom().getId(), msg.getText(), msg.getMessageId(), msg.getCaption(), deep, false
        );
    }

    public static Message createPreviousMessage(Message msg) {
        return messageBase(
                msg.getFrom().getId(), msg.getText(), msg.getMessageId(), msg.getCaption(), 1, false
        );
    }

    private static Message messageBase(Long id, String text, int msgId, String caption, int deep, boolean isNext) {
        int messageId = isNext ? msgId + deep : msgId - deep;
        User user = new User();
        user.setId(id);
        Message message = new Message();
        message.setText(text);
        message.setMessageId(messageId);
        message.setCaption(caption);
        message.setFrom(user);
        Chat chat = new Chat();
        chat.setId(id);
        message.setChat(chat);
        return message;
    }

    public static SendMessage callBackAnswer(Long chatId, String message) {
        return SendMessage.builder()
                .chatId(chatId)
                .parseMode(ParseMode.HTML)
                .disableWebPagePreview(true)
                .text(message)
                .build();
    }

    public static SendMessage callBackAnswer(long chatId, String message, ReplyKeyboard replyKeyboard) {
        return SendMessage.builder()
                .chatId(chatId)
                .replyMarkup(replyKeyboard)
                .parseMode(ParseMode.HTML)
                .disableWebPagePreview(true)
                .text(message)
                .build();
    }

    public static DeleteMessage deleteMessage(long chatId, int messageId) {
        return DeleteMessage.builder()
                .chatId(chatId)
                .messageId(messageId)
                .build();
    }

}