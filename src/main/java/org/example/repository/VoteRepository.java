package org.example.repository;

import org.example.entity.Question;
import org.example.entity.User;
import org.example.entity.Vote;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends CrudRepository<Vote, Long> {
    Vote findByQuestionIdAndUserId(Long questionId, Long userId);

}
