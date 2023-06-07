package org.example.controller;

import jakarta.persistence.EntityNotFoundException;
import org.example.entity.Question;
import org.example.entity.QuestionVote;
import org.example.repository.QuestionRepository;
import org.example.repository.VoteRepository;
import org.example.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.entity.UserIdWrapper;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/questions")
public class QuestionController {
    @Autowired
    QuestionService questionService;
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    QuestionRepository questionVoteRepository;
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

    @PostMapping("/vote")
    public ResponseEntity<Map<String, Object>> voteQuestion(@RequestBody Map<String, Object> voteData) {
        Integer questionId = (Integer) voteData.get("questionId");
        Integer userId = (Integer) voteData.get("userId");
        String voteType = (String) voteData.get("voteType");

        String questionIdStr = questionId.toString();
        String userIdStr = userId.toString();

        Optional<Question> questionOptional = questionRepository.findById(Long.valueOf(questionId));
        if (!questionOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Question question = questionOptional.get();
        Map<String, Integer> votes = question.getVotes();
        Integer previousVote = votes.getOrDefault(userIdStr, 0);

        if (previousVote != 0) {
            // If the user has voted before, we do not update the vote count or votes map unless it is a different vote type

            if (previousVote == 1 && voteType.equals("downvote")) {
                question.setVoteCount(question.getVoteCount() - 1);
                votes.put(userIdStr, -1); // Set downvote
            } else if (previousVote == -1 && voteType.equals("upvote")) {
                question.setVoteCount(question.getVoteCount() + 1);
                votes.put(userIdStr, 1); // Set upvote
            } else if (previousVote == 1 && voteType.equals("upvote")) {
                question.setVoteCount(question.getVoteCount() - 1);
                votes.put(userIdStr, 0); // Reset vote
            } else if (previousVote == -1 && voteType.equals("downvote")) {
                question.setVoteCount(question.getVoteCount() + 1);
                votes.put(userIdStr, 0); // Reset vote
            } else if (previousVote == 0 && voteType.equals("upvote")) {
                question.setVoteCount(question.getVoteCount() + 1);
                votes.put(userIdStr, 1); // Set upvote
            } else if (previousVote == 0 && voteType.equals("downvote")) {
                question.setVoteCount(question.getVoteCount() - 1);
                votes.put(userIdStr, -1); // Set downvote
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
//            Map<String, Object> responseBody = new HashMap<>();
//            responseBody.put("voteCount", question.getVoteCount());
//            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        }

        if (voteType.equals("upvote")) {
            question.setVoteCount(question.getVoteCount() + 1);
            votes.put(userIdStr, 1); // Set upvote
        } else if (voteType.equals("downvote")) {
            question.setVoteCount(question.getVoteCount() - 1);
            votes.put(userIdStr, -1); // Set downvote
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        questionRepository.save(question);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("voteCount", question.getVoteCount());
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

//        Optional<QuestionVote> existingVote = questionVoteRepository.findByQuestionIdAndUserId(Long.valueOf(questionId),Long.valueOf(( userId)));
//        if (existingVote.isPresent()) {
//            // User has already voted on the question
//            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//        }
//
//        Optional<Question> questionOptional = questionRepository.findById(Long.valueOf(questionId));
//        if (!questionOptional.isPresent()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//        Question question = questionOptional.get();
//
//        if (question.getAuthor_id().equals(userId)) {
//            return new ResponseEntity<>(HttpStatus.FORBIDDEN); // User cannot vote their own question
//        }
//
//        Map<String, Integer> votes = question.getVotes();
//        Integer previousVote = votes.getOrDefault(userId, 0);
//
//        // Check if the user has voted on the question before
//        if(previousVote != 0) {
//            // If the user has voted before, we do not update the vote count or votes map
//            Map<String, Object> responseBody = new HashMap<>();
//            responseBody.put("voteCount", question.getVoteCount());
//            return new ResponseEntity<>(responseBody, HttpStatus.OK);
//        }
//
//        if (voteType.equals("upvote")) {
//            question.setVoteCount(question.getVoteCount() + 1);
//            votes.put(userIdStr, 1); // Set upvote
//        } else if (voteType.equals("downvote")) {
//            question.setVoteCount(question.getVoteCount() - 1);
//            votes.put(userIdStr, -1); // Set downvote
//        }

//        question.setVotes(votes);
//        questionRepository.save(question);
//
//        Map<String, Object> responseBody = new HashMap<>();
//        responseBody.put("voteCount", question.getVoteCount());
//
//        return new ResponseEntity<>(responseBody, HttpStatus.OK);
//    }



}
