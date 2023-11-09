package org.piglets;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.piglets.botapi.GenericTelegramFacade;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class Bot extends TelegramLongPollingBot {
    private final String botUserName;
    private final String botToken;
    private final GenericTelegramFacade telegramFacade;

    @Autowired
    public Bot(GenericTelegramFacade telegramFacade,
               @Value("${telegrambot.botToken}") String botToken,
               @Value("${telegrambot.botUserName}") String botUserName) {
        this.telegramFacade = telegramFacade;
        this.botToken = botToken;
        this.botUserName = botUserName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public String getBotUsername() {
        return botUserName;
    }

    @Override
    public void onUpdateReceived(Update update) {
        CompletableFuture.runAsync(() -> {
            List<BotApiMethod<?>> replyMessageToUser = telegramFacade.handleUpdate(update);
            for (BotApiMethod<?> message : replyMessageToUser) {
                execute(message);
            }
        });
    }

    @SneakyThrows
    @Override
    public <T extends Serializable, Method extends BotApiMethod<T>> T execute(Method method) {
        return super.execute(method);
    }

}