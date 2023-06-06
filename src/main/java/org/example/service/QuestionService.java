package org.example.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import org.example.entity.Question;
import org.example.entity.Tag;
import org.example.entity.User;
import org.example.entity.Vote;
import org.example.repository.QuestionRepository;
import org.example.repository.TagRepository;
import org.example.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Version;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    TagRepository tagRepository;
    @Autowired
    VoteRepository voteRepository;

    public List<Question> retrieveQuestions() {
        return (List<Question>) questionRepository.findAll();
    }

    public Question retrieveQuestionById(Long id) {
        Optional<Question> question = questionRepository.findById(id);
        if (question.isPresent()) {
            return question.get();
        } else {
            return null;
        }
    }

    public void removeQuestionById(Long id) {
        questionRepository.deleteById(id);
    }

    public Question updateQuestion(Question updatedQuestion) {
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


    @Version
    private int version;


    public Question createQuestion(Question newQuestion) {
        List<Tag> existingTags = tagRepository.findByNameIn(newQuestion.getTags());

        newQuestion.setTags(existingTags);

        newQuestion.setDate(LocalDateTime.now());


        List<Tag> tagEntities = newQuestion.getTags().stream()
                .map(tagName -> {
                    Tag tag = tagRepository.findByName(tagName);
                    if (tag == null) {
                        tag = new Tag(tagName);  // This assumes Tag has a constructor that accepts a name
                        tagRepository.save(tag);
                    }
                    return tag;
                })
                .collect(Collectors.toList());

        newQuestion.setTags(tagEntities);
       // questionRepository.save(newQuestion);

        return questionRepository.save(newQuestion);
    }

    public List<Question> searchQuestions(String title, String author, String tagName) {
        return questionRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrTags_NameContainingIgnoreCase(
                title, author, tagName);
    }

    // vote for question
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    public void voteQuestion(Long id, int vote, Long userId) {
//        Vote existingVote = voteRepository.findByQuestionIdAndUserId(id, userId);
//        if (existingVote != null) {
//            throw new IllegalArgumentException("User has already voted for this question");
//        }
//
//        Question question = entityManager.find(Question.class, id, LockModeType.PESSIMISTIC_WRITE);
//        int totalVotes = question.calculateTotalVotes();
//        // Note: Depending on your business rules, you might not need to manually set the total votes.
//        // Instead, you can calculate them on the fly when you need them.
//
//        Vote newVote = new Vote();
//        newVote.setUserId(userId);
//        newVote.setQuestionId(id);
//        newVote.setVote(vote);
//        voteRepository.save(newVote);
//    }






}
