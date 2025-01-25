package com.example.analyticslibrary.models;

public class GeolocationRequest {
    private String appId;
    private String userId;
    private double latitude;
    private double longitude;

    public GeolocationRequest(String appId, String userId, double latitude, double longitude) {
        this.appId = appId;
        this.userId = userId;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getAppId() {
        return appId;
    }

    public String getUserId() {
        return userId;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
    // ✅ Add a toString() method for logging
    @Override
    public String toString() {
        return "GeolocationRequest{" +
                "appId='" + appId + '\'' +
                ", userId='" + userId + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

}
