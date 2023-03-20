package org.example.repository;



import org.example.entity.Question;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface QuestionRepository extends CrudRepository<Question, Long> {
}

