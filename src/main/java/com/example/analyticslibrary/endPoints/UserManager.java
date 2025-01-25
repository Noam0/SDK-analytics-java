package com.example.analyticslibrary.endPoints;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.analyticslibrary.AnalyticsSDK;
import com.example.analyticslibrary.models.GeolocationRequest;
import com.example.analyticslibrary.models.UpdateUserRequest;
import com.example.analyticslibrary.models.UserRequest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserManager {

    private static final String PREFS_NAME = "AnalyticsPrefs";
    private static final String USER_ID_KEY = "user_id";

    /**
     * Checks if the user exists. If not, registers a new user; otherwise, updates last seen.
     */
    public static void createOrUpdateUser(Context context) {
        String userId = getUserId(context);

        if (userId == null) {
            Log.d("UserManager", "New user detected, registering user...");
            userId = generateNewUserId(context);
            String currentTimestamp = getCurrentTimestamp();
            registerUser(userId, currentTimestamp, currentTimestamp);
        } else {
            Log.d("UserManager", "Existing user detected, updating last seen...");
            updateLastSeen(context, userId);
        }
    }

    /**
     * Registers a new user by sending a POST request to the server.
     */
    private static void registerUser(String userId, String firstSeen, String lastSeen) {
        String appId = AnalyticsSDK.getInstance().getAppId();
        UserRequest request = new UserRequest(userId, appId, firstSeen, lastSeen);

        AnalyticsSDK.getInstance().getApiService().registerUserWithHeaders("application/json", request)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Log.d("UserManager", "User registered successfully!");
                        } else {
                            Log.e("UserManager", "Failed to register user: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.e("UserManager", "Error registering user: " + t.getMessage());
                    }
                });
    }

    /**
     * Updates the user's last seen timestamp.
     */
    private static void updateLastSeen(Context context, String userId) {
        String appId = AnalyticsSDK.getInstance().getAppId();
        String currentTimestamp = getCurrentTimestamp();
        UpdateUserRequest request = new UpdateUserRequest(appId, currentTimestamp);

        AnalyticsSDK.getInstance().getApiService().updateUserWithHeaders("application/json", userId, request)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Log.d("UserManager", "Last seen updated successfully!");
                        } else {
                            Log.e("UserManager", "Failed to update last seen: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.e("UserManager", "Error updating last seen: " + t.getMessage());
                    }
                });
    }

    /**
     * Generates a new user ID, saves it, and returns it.
     */
    private static String generateNewUserId(Context context) {
        String newUserId = UUID.randomUUID().toString();
        saveUserId(context, newUserId);
        return newUserId;
    }

    /**
     * Saves the user ID in SharedPreferences.
     */
    private static void saveUserId(Context context, String userId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(USER_ID_KEY, userId).apply();
    }

    /**
     * Retrieves the user ID from SharedPreferences.
     */
    private static String getUserId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getString(USER_ID_KEY, null);
    }

    /**
     * Gets the current timestamp in ISO 8601 format.
     */
    private static String getCurrentTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
        return sdf.format(new Date());
    }



    //Locations:


    public static void sendGeolocation(Activity activity) {
        if (!LocationManager.checkLocationPermissions(activity)) {
            Log.e("UserManager", "Cannot send geolocation: Permissions not granted.");
            return;
        }

        LocationManager.getCurrentLocation(activity, new LocationManager.LocationCallbackListener() {
            @Override
            public void onLocationRetrieved(double latitude, double longitude) {
                String appId = AnalyticsSDK.getInstance().getAppId();
                String userId = getUserId(activity);

                if (userId == null) {
                    Log.e("UserManager", "Cannot send geolocation: User ID is null.");
                    return;
                }

                GeolocationRequest request = new GeolocationRequest(appId, userId, latitude, longitude);
                Log.d("GeolocationRequest", "Sending request: " + request.toString());

                AnalyticsSDK.getInstance().getApiService().sendGeolocation("application/json", request)
                        .enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()) {
                                    Log.d("UserManager", "Geolocation sent successfully!");
                                } else {
                                    Log.e("UserManager", "Failed to send geolocation: " + response.code());
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Log.e("UserManager", "Error sending geolocation: " + t.getMessage());
                            }
                        });
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e("UserManager", "Failed to get location: " + errorMessage);
            }
        });
    }



}
