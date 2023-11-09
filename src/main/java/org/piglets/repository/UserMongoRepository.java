package org.piglets.repository;

import org.piglets.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMongoRepository extends MongoRepository<User, Long> {

    @Query(value = "{ '_id' : ?0 }", fields = "{ 'messages' : 1 }")
    void appendElementToList(Long userId, User.Message message);
}
