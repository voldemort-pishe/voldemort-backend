package io.avand.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.avand.domain.enumeration.FeedbackRate;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class FeedbackDTO implements Serializable {

    private Long id;

    private String feedbackText;

    private FeedbackRate rating;

    @NotNull
    private Long candidateId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFeedbackText() {
        return feedbackText;
    }

    public void setFeedbackText(String feedbackText) {
        this.feedbackText = feedbackText;
    }

    public FeedbackRate getRating() {
        return rating;
    }

    public void setRating(FeedbackRate rating) {
        this.rating = rating;
    }

    public Long getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
    }

    @Override
    public String toString() {
        return "FeedbackDTO{" +
            "id=" + id +
            ", feedbackText='" + feedbackText + '\'' +
            ", rating=" + rating +
            '}';
    }
}
