package org.example.service;

import org.example.entity.Answer;
import org.example.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Service
public class AnswerService {

    @Autowired
    AnswerRepository  answerRepository;


    public List<Answer> retrieveAnswers() {
        return (List<Answer>) answerRepository.findAll();
    }

    public Answer retrieveAnswerById(Long id) {

        Optional<Answer> answer = answerRepository.findById(id);

        if(answer.isPresent()) {
            return answer.get();
        } else {
            return null;
        }
    }

    public void removeAnswerById(Long id) {
        answerRepository.deleteById(id);
    }

    public Answer updateAnswer(Answer updatedAnswer) {
        //need to add a check if the answer cnp exists!!
        Answer existingAnswer = answerRepository.findById(updatedAnswer.getId()).orElseThrow();
        existingAnswer.setCreationTime(updatedAnswer.getCreationTime());
        existingAnswer.setText(updatedAnswer.getText());
        existingAnswer.setPicture(updatedAnswer.getPicture());
        existingAnswer.setVotes(updatedAnswer.getVotes());
        existingAnswer.setQuestionId(updatedAnswer.getQuestionId());
        existingAnswer.setAuthor(updatedAnswer.getAuthor());
        existingAnswer.setAuthorName(updatedAnswer.getAuthorName());
        return answerRepository.save(existingAnswer);
    }

    public Answer createAnswer(Answer newAnswer) {

        newAnswer.setCreationTime(LocalDateTime.now());
        return answerRepository.save(newAnswer);
    }

    public List<Answer> retrieveAnswersByQuestionId(Long questionId) {
        return answerRepository.findByQuestionId(questionId);
    }


}
