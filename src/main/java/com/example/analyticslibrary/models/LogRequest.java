package com.example.analyticslibrary.models;

public class LogRequest {
    private String appId;
    private String logType; // e.g., "Crash"
    private String description; // e.g., stack trace or error message

    public LogRequest(String appId, String logType, String description) {
        this.appId = appId;
        this.logType = logType;
        this.description = description;
    }

    // Getters (if needed)
    public String getAppId() {
        return appId;
    }

    public String getLogType() {
        return logType;
    }

    public String getDescription() {
        return description;
    }
}