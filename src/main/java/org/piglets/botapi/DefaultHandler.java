package org.piglets.botapi;

import lombok.extern.slf4j.Slf4j;
import org.piglets.entity.User;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

import static org.piglets.static_data.Messages.failedToProcess;
import static org.piglets.utils.CommonMessageUtils.callBackAnswer;

@Slf4j
@Component
public class DefaultHandler implements InputHandler {
    @Override
    public List<PartialBotApiMethod<?>> handle(Update update, User user) {
        log.error("Failed to process user update. User [{}]. Update [{}]", user, update);
        return List.of(callBackAnswer(user.getChatId(), failedToProcess()));
    }

    @Override
    public boolean isApplicable(Update update, User user) {
        return true;
    }
}
