package org.piglets.service;

import org.piglets.botapi.BotState;
import org.piglets.entity.User;
import org.piglets.repository.UserMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.util.Optional;

@Service
public class UserService {
    private final UserMongoRepository repository;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public UserService(UserMongoRepository repository, MongoTemplate mongoTemplate) {
        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
    }

    public void saveBotState(Long userId, BotState botState) {
        Query query = new Query(Criteria.where("id").is(userId));
        Update update = new Update().set("botState", botState)
                .set("updatedAt", Clock.systemUTC().instant());
        mongoTemplate.updateFirst(query, update, User.class);
    }

    public void savePartner(Long userId, Long partnerId) {
        Query query = new Query(Criteria.where("id").is(userId));
        Update update = new Update().set("partnerId", partnerId)
                .set("updatedAt", Clock.systemUTC().instant());
        mongoTemplate.updateFirst(query, update, User.class);
    }

    public void save(User user) {
        repository.save(user);
    }

    public Optional<User> findUser(Long userId) {
        return repository.findById(userId);
    }

    public User getUser(Long userId) {
        return repository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("Cant find user: " + userId));
    }

    public void appendMessage(Long userId, User.Message message) {
        mongoTemplate.updateFirst(
                Query.query(Criteria.where("_id").is(userId)),
                new Update().push("messages", message),
                User.class
        );
    }

}
