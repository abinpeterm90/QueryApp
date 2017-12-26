package com.teksine.queryapplication.other;

/**
 * Created by abin on 16/12/2017.
 */

public class Notification {

    public Notification(Long answerStatus,String email,String firstName, String lastName,String googleID, String photoUrl,String topic){
       this.answerStatus=answerStatus;
        this.email=email;
        this.firstName=firstName;
        this.lastName=lastName;
        this.googleID=googleID;
        this.photoUrl=photoUrl;
        this.topic=topic;

    }
    public Notification(){};
    String email;
    String firstName;
    String lastName;
    String googleID;
    String photoUrl;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    String topic;

    public Long getAnswerStatus() {
        return answerStatus;
    }

    public void setAnswerStatus(Long answerStatus) {
        this.answerStatus = answerStatus;
    }

    Long answerStatus;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGoogleID() {
        return googleID;
    }

    public void setGoogleID(String googleID) {
        this.googleID = googleID;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }



}
