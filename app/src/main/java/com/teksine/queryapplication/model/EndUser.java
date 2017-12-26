package com.teksine.queryapplication.model;

import java.util.Date;

/**
 * Created by abin on 09/12/2017.
 */

public class EndUser  {

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

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    private String answer;

    private Date postedDate;

    public Long getAnswerStatus() {
        return answerStatus;
    }

    public void setAnswerStatus(Long answerStatus) {
        this.answerStatus = answerStatus;
    }

    private Long answerStatus;

}
