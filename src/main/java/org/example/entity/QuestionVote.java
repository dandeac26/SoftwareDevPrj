package org.example.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "question_vote")
public class QuestionVote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "question_id")
    private Long questionId;

    @Column(name = "user_id")
    private Long userId;

    public QuestionVote() {
    }

    public QuestionVote(Long questionId, Long userId) {
        this.questionId = questionId;
        this.userId = userId;
    }

    public QuestionVote(Long id, Long questionId, Long userId) {
        this.id = id;
        this.questionId = questionId;
        this.userId = userId;
    }
// Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


}
