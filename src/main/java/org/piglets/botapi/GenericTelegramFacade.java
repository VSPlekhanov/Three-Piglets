package org.piglets.botapi;


import com.google.common.collect.Streams;
import lombok.extern.slf4j.Slf4j;
import org.piglets.botapi.callback_handlers.InputCallbackHandler;
import org.piglets.botapi.command_handlers.InputCommandHandler;
import org.piglets.botapi.message_handlers.InputMessageHandler;
import org.piglets.static_data.Messages;
import org.piglets.utils.CommonMessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.*;
import org.piglets.service.UserService;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.piglets.static_data.Messages.FATAL_ERROR;


@Component
@Slf4j
public class GenericTelegramFacade {

    private final List<InputMessageHandler> messageHandlers;
    private final List<InputCommandHandler> commandHandlers;
    private final List<InputCallbackHandler> callbackHandlers;
    private final DefaultHandler defaultHandler;
    private final UserService userService;

    @Autowired
    public GenericTelegramFacade(List<InputMessageHandler> messageHandlers,
                                 List<InputCommandHandler> commandHandlers,
                                 List<InputCallbackHandler> callbackHandlers,
                                 DefaultHandler defaultHandler, UserService userService) {
        this.messageHandlers = messageHandlers;
        this.commandHandlers = commandHandlers;
        this.callbackHandlers = callbackHandlers;
        this.defaultHandler = defaultHandler;
        this.userService = userService;
    }

    public List<BotApiMethod<?>> handleUpdate(Update update) {
        if ((!update.hasCallbackQuery() && !update.hasMessage()) || (update.hasMessage() && !update.getMessage().hasText())) {
            log.error("Invalid update: {}", update);
            return List.of();
        }
        var user = update.hasCallbackQuery() ? update.getCallbackQuery().getFrom() : update.getMessage().getFrom();

        try {
            log.info("New update from User:{}, update: {}", user, update);
            var from = userService.findUser(user.getId()).orElse(null);

            return Streams.concat(callbackHandlers.stream(), commandHandlers.stream(), messageHandlers.stream())
                    .filter(handler -> handler.isApplicable(update, from))
                    .findFirst()
                    .orElse(defaultHandler)
                    .handle(update, from);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            var chatId = update.hasMessage() ? update.getMessage().getChatId() : update.getCallbackQuery().getMessage().getChatId();
            return List.of(CommonMessageUtils.callBackAnswer(chatId, FATAL_ERROR));
        }
    }
}