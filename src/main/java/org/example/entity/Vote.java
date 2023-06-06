package org.example.entity;

import jakarta.persistence.*;

@Entity
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "question_id")
    private Long questionId;

    @Column(name = "upvote")
    private boolean upvote;

    public Vote() {
    }

    public Vote(Long id, Long userId, Long questionId, boolean upvote) {
        this.id = id;
        this.userId = userId;
        this.questionId = questionId;
        this.upvote = upvote;
    }

    public Vote(Long userId, Long questionId, int vote) {
        this.userId = userId;
        this.questionId = questionId;
        this.upvote = vote == 1;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public boolean isUpvote() {
        return upvote;
    }

    public void setUpvote(boolean upvote) {
        this.upvote = upvote;
    }

    public void setVote(int vote) {
        this.upvote = vote == 1;
    }
}
