package com.example.analyticslibrary;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.analyticslibrary.endPoints.LogManager;

public class AutoClickTracker implements View.OnClickListener {
    private final View.OnClickListener originalClickListener;

    public AutoClickTracker(View.OnClickListener originalClickListener) {
        this.originalClickListener = originalClickListener;
    }

    @Override
    public void onClick(View v) {
        if (v instanceof Button) {
            String buttonId;
            try {
                buttonId = v.getResources().getResourceEntryName(v.getId());
            } catch (Exception e) {
                buttonId = "UnknownButton";
            }

            // ✅ Log button click event automatically
            LogManager.sendLog("Other", "User clicked: " + buttonId);
            Log.d("AutoClickTracker", "User clicked: " + buttonId);
        }

        // ✅ Call the original click listener if it exists
        if (originalClickListener != null) {
            originalClickListener.onClick(v);
        }
    }
}
