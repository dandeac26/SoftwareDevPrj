package org.example.service;

import org.example.entity.Question;
import org.example.entity.Tag;
import org.example.repository.QuestionRepository;
import org.example.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    TagRepository tagRepository;

    public List<Question> retrieveQuestions() {
        return (List<Question>) questionRepository.findAll();
    }

    public Question retrieveQuestionById(Long id) {

        Optional<Question> question = questionRepository.findById(id);

        if(question.isPresent()) {
            return question.get();
        } else {
            return null;
        }
    }

    public void removeQuestionById(Long id) {
        questionRepository.deleteById(id);
    }

    public Question updateQuestion(Question updatedQuestion) {
        //need to add a check if the user cnp exists!!
        Question existingQuestion = questionRepository.findById(updatedQuestion.getId()).orElseThrow();
        existingQuestion.setAuthor(updatedQuestion.getAuthor());
        existingQuestion.setAuthor_id(updatedQuestion.getAuthor_id());
        existingQuestion.setTitle(updatedQuestion.getTitle());
        existingQuestion.setPicture(updatedQuestion.getPicture());
        existingQuestion.setDate(updatedQuestion.getDate());
        existingQuestion.setBody(updatedQuestion.getBody());
        existingQuestion.setTags(updatedQuestion.getTagsObj());
        existingQuestion.setVotes(updatedQuestion.getVotes());

        return questionRepository.save(existingQuestion);
    }

    public Question createQuestion(Question newQuestion) {
        List<Tag> existingTags = tagRepository.findByNameIn(newQuestion.getTags());

        newQuestion.setTags(existingTags);
        //set the current date and time as the date of the question
        newQuestion.setDate(LocalDateTime.now());

        return questionRepository.save(newQuestion);
    }
}
