package com.example.analyticslibrary;

import android.app.Application;
import android.app.Activity;
import android.content.Context;

import com.example.analyticslibrary.endPoints.LogManager;
import com.example.analyticslibrary.endPoints.UserManager;
import com.example.analyticslibrary.endPoints.AppRatingsManager;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AnalyticsSDK {
    private static AnalyticsSDK instance;
    private final ApiService apiService;
    private final String appId;
    private static Context appContext;
    // Constructor to initialize ApiService and appId
    private AnalyticsSDK(Context context, String baseUrl, String appId) {
        appContext = context.getApplicationContext();
        this.appId = appId;

        // Initialize Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.apiService = retrofit.create(ApiService.class);

        // Initialize logging
        CrashLogger.init();
        LifecycleLogger.init((Application) context);

        // Auto register or retrieve user
        //UserManager.autoRegisterUser(context);
    }

    // Initialize the AnalyticsSDK
    public static void initialize(Context context, String baseUrl, String appId) {
        if (instance == null) {
            instance = new AnalyticsSDK(context, baseUrl, appId);
        }
    }

    // Get the singleton instance of AnalyticsSDK
    public static AnalyticsSDK getInstance() {
        if (instance == null) {
            throw new IllegalStateException("AnalyticsSDK is not initialized. Call initialize() first.");
        }
        return instance;
    }



    // Getter for appId
    public String getAppId() {
        return appId;
    }

    // Getter for ApiService
    public ApiService getApiService() {
        return apiService;
    }

    //public static void registerUser(String userId, String firstSeen, String lastSeen) {
     //   UserManager.registerUser(userId, firstSeen, lastSeen);
   // }

    // Utility method to show the rating dialog
    public static void showRatingDialog(Activity activity) {
        AppRatingsManager.showRatingDialog(activity);
    }

   // public static void updateUserLastSeen() {
    //    UserManager.updateLastSeen(appContext);
    //}

    public static void logCrash(String crashDetails) {
        LogManager.sendLog("Crash", crashDetails);
    }

    public static void createLog(String logType, String description) {
        LogManager.sendLog(logType, description);
    }




}
