package org.piglets;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.piglets.botapi.GenericTelegramFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;
import java.util.List;

@Service
@Slf4j
@ConditionalOnExpression("'${telegrambot.webHookPath}' != null")
public class WebHookBot extends TelegramWebhookBot {
    private final String botUserName;
    private final String webHookPath;
    private final String botToken;
    private final GenericTelegramFacade telegramFacade;

    @Autowired
    public WebHookBot(GenericTelegramFacade telegramFacade,
                      @Value("${telegrambot.webHookPath}") String webHookPath,
                      @Value("${telegrambot.botToken}") String botToken,
                      @Value("${telegrambot.botUserName}") String botUserName) {
        this.telegramFacade = telegramFacade;
        this.webHookPath = webHookPath;
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
    public String getBotPath() {
        return webHookPath;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        try {
            var replyMessageToUser = telegramFacade.handleUpdate(update);
            for (var message : replyMessageToUser) {
                if (message instanceof BotApiMethod<?>) {
                    execute((BotApiMethod<?>) message);
                } else if (message instanceof SendPhoto) {
                    super.execute((SendPhoto) message);
                } else {
                    throw new IllegalStateException("Unsupported API method: " + message.getClass());
                }
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @SneakyThrows
    @Override
    public <T extends Serializable, Method extends BotApiMethod<T>> T execute(Method method) {
        return super.execute(method);
    }

}