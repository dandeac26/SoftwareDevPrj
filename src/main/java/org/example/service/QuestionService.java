package org.example.service;

import org.example.entity.Question;
import org.example.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    @Autowired
    QuestionRepository questionRepository;


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
        existingQuestion.setPicture(updatedQuestion.getPicture());
        existingQuestion.setDate(updatedQuestion.getDate());
        existingQuestion.setText(updatedQuestion.getText());
        existingQuestion.setTags(updatedQuestion.getTags());
        return questionRepository.save(existingQuestion);
    }

    public Question createQuestion(Question newQuestion) {
        return questionRepository.save(newQuestion);
    }
}
