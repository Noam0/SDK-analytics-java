package com.example.analyticslibrary.models;

public class UserRequest {
    private String userId;
    private String appId;
    private String firstSeen;
    private String lastSeen;

    public UserRequest(String userId, String appId, String firstSeen, String lastSeen) {
        this.userId = userId;
        this.appId = appId;
        this.firstSeen = firstSeen;
        this.lastSeen = lastSeen;
    }

    // Getters and setters (if needed)
    public String getUserId() {
        return userId;
    }

    public String getAppId() {
        return appId;
    }

    public String getFirstSeen() {
        return firstSeen;
    }

    public String getLastSeen() {
        return lastSeen;
    }
}
