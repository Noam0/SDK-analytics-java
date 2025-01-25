package com.example.analyticslibrary.endPoints;

import android.util.Log;

import com.example.analyticslibrary.AnalyticsSDK;
import com.example.analyticslibrary.models.LogRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class LogManager {

    /**
     * Sends a log to the server.
     *
     * @param logType     The type of log (e.g., "Crash").
     * @param description The log description (e.g., stack trace or error details).
     */
    public static void sendLog(String logType, String description) {
        String appId = AnalyticsSDK.getInstance().getAppId();
        LogRequest logRequest = new LogRequest(appId, logType, description);

        AnalyticsSDK.getInstance().getApiService().createLog("application/json", logRequest)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Log.d("LogManager", "Log sent successfully!");
                        } else {
                            Log.e("LogManager", "Failed to send log: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.e("LogManager", "Error sending log: " + t.getMessage());
                    }
                });
    }


}
