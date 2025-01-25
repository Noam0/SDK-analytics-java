package com.example.analyticslibrary.models;
public class AppRatingRequest {
    private String appId;
    private String userId;
    private int rating;
    private String comment;

    public AppRatingRequest(String appId, String userId, int rating, String comment) {
        this.appId = appId;
        this.userId = userId;
        this.rating = rating;
        this.comment = comment;
    }

    // Getters
    public String getAppId() {
        return appId;
    }

    public String getUserId() {
        return userId;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

}