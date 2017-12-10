package com.teksine.queryapplication.model;

import java.util.Date;

/**
 * Created by abin on 09/12/2017.
 */

public class EndUser extends User {

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

    public Integer getAnswerStatus() {
        return answerStatus;
    }

    public void setAnswerStatus(Integer answerStatus) {
        this.answerStatus = answerStatus;
    }

    private String query;
    private Date postedDate;
    private Integer answerStatus;
}
