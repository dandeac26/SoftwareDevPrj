package org.example.controller;

import jakarta.persistence.EntityNotFoundException;
import org.example.entity.Question;
import org.example.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.entity.UserIdWrapper;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionController {
    @Autowired
    QuestionService questionService;

    @GetMapping("/getAll")
    @ResponseBody
    public List<Question> retrieveQuestions(){return questionService.retrieveQuestions();}
    @GetMapping("/getById/{id}")
    @ResponseBody
    public Question retrieveById(@PathVariable Long id){
        return questionService.retrieveQuestionById(id);
    }


    @DeleteMapping("/deleteId={id}")
    public ResponseEntity<Void> removeQuestion(@PathVariable Long id) {
        questionService.removeQuestionById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/updateId={id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable Long id, @RequestBody Question updatedQuestion) {
        if (!id.equals(updatedQuestion.getId())) {
            throw new IllegalArgumentException("ID in path must match ID in request body");
        }
        updatedQuestion.setDate(LocalDateTime.now());
        Question savedQuestion = questionService.updateQuestion(updatedQuestion);
        return ResponseEntity.ok(savedQuestion);
    }

    @PostMapping("/create")
    public ResponseEntity<Question> createQuestion(@RequestBody Question newQuestion) {
        Question savedQuestion = questionService.createQuestion(newQuestion);
        return ResponseEntity.ok(savedQuestion);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Question>> searchQuestions(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String tagName
    ) {
        List<Question> searchedQuestions = questionService.searchQuestions(title, author, tagName);
        return ResponseEntity.ok(searchedQuestions);
    }

//    @PutMapping("/vote/{id}")
//    public ResponseEntity<Void> voteQuestion(@PathVariable Long id, @RequestParam int vote, @RequestParam Long userId) {
//        questionService.voteQuestion(id, vote, userId);
//        return ResponseEntity.noContent().build();
//    }

}
