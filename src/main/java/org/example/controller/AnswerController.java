package org.example.controller;

import org.example.entity.Answer;
import org.example.entity.Answer;
import org.example.entity.Answer;
import org.example.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping( "/answers")
public class AnswerController {

    @Autowired
    AnswerService answerService;

    @GetMapping( "/getAll")
    @ResponseBody
    public List<Answer> retrieveAnswers() {
        return answerService.retrieveAnswers();
    }


    @GetMapping("/getById")
    @ResponseBody
    public Answer retrieveById(){
        return answerService.retrieveAnswerById(1L);
    }

    @GetMapping("/getByQuestion/{questionId}")
    @ResponseBody
    public List<Answer> retrieveByQuestion(@PathVariable Long questionId){
        return answerService.retrieveAnswersByQuestionId(questionId);
    }



    @DeleteMapping("/deleteId={id}")
    public ResponseEntity<Void> removeAnswer(@PathVariable Long id) {
        answerService.removeAnswerById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/updateId={id}")
    public ResponseEntity<Answer> updateAnswer(@PathVariable Long id, @RequestBody Answer updatedAnswer) {
        if (!id.equals(updatedAnswer.getId())) {
            throw new IllegalArgumentException("ID in path must match ID in request body");
        }
        Answer savedAnswer = answerService.updateAnswer(updatedAnswer);
        return ResponseEntity.ok(savedAnswer);
    }
    @PostMapping("/create")
    public ResponseEntity<Answer> createAnswer(@RequestBody Answer newAnswer) {
        Answer savedAnswer = answerService.createAnswer(newAnswer);
        return ResponseEntity.ok(savedAnswer);
    }
}
