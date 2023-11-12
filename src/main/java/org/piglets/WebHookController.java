package org.piglets;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
@Slf4j
@ConditionalOnExpression("'${telegrambot.webHookPath}' != null")
public class WebHookController {

    @Autowired
    private WebHookBot bot;

    @PostMapping(value = "/")
    public ResponseEntity<Integer> onUpdateReceived(@RequestBody Update update) {
        bot.onWebhookUpdateReceived(update);
        return ResponseEntity.ok(200);
    }

}
