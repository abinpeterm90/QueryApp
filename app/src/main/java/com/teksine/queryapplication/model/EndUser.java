package com.teksine.queryapplication.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by abin on 09/12/2017.
 */

public class EndUser implements Serializable {

    public Date getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(Date postedDate) {
        this.postedDate = postedDate;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    private String query;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    private String topic;


    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    private String answer;

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    private String photoUrl;

    private Date postedDate;

    public Long getAnswerStatus() {
        return answerStatus;
    }

    public void setAnswerStatus(Long answerStatus) {
        this.answerStatus = answerStatus;
    }

    private Long answerStatus;

}
