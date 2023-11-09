package org.piglets.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.piglets.botapi.BotState;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Document(collection = "User")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private long id;
    private long chatId;
    private Long partnerId;
    private String language;
    private BotState botState;
    private List<Message> messages;
    private Instant createdAt;
    private Instant updatedAt;
    private Piglet currentPiglet;

    public record Message(Piglet piglet, String text, Instant createdAt) {}
}
