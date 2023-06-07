package org.example.repository;



import org.example.entity.Question;
import org.example.entity.QuestionVote;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends CrudRepository<Question, Long> {
    List<Question> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrTags_NameContainingIgnoreCase(String title, String author, String tagName);

}

