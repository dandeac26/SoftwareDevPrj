package org.example.repository;

import org.example.entity.QuestionVote;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface QuestionVoteRepository extends CrudRepository<QuestionVote, Long> {
    QuestionVote findByQuestionIdAndUserId(Long questionId, Long userId);
}
