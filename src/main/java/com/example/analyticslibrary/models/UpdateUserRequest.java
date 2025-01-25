package com.example.analyticslibrary.models;

public class UpdateUserRequest {
    private String appId;
    private String lastSeen;

    public UpdateUserRequest(String appId, String lastSeen) {
        this.appId = appId;
        this.lastSeen = lastSeen;
    }

    // Getters (required for Retrofit serialization)
    public String getAppId() { return appId; }
    public String getLastSeen() { return lastSeen; }
}