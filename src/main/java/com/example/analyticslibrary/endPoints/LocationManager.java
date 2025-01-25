package com.example.analyticslibrary.endPoints;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Looper;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
public class LocationManager {

        private static final String TAG = "LocationManager";
        private static FusedLocationProviderClient fusedLocationClient;
        private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;

        public interface LocationCallbackListener {
            void onLocationRetrieved(double latitude, double longitude);
            void onFailure(String errorMessage);
        }

        // ✅ Check and request location permissions before accessing location
        public static boolean checkLocationPermissions(Activity activity) {
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                // Request location permissions from the user
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                        LOCATION_PERMISSION_REQUEST_CODE);

                return false; // Permissions not granted yet
            }
            return true; // Permissions already granted
        }

        @SuppressLint("MissingPermission")
        public static void getCurrentLocation(Activity activity, LocationCallbackListener callback) {
            if (!checkLocationPermissions(activity)) {
                callback.onFailure("Location permissions are not granted.");
                return;
            }

            if (fusedLocationClient == null) {
                fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);
            }

            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(location -> {
                        if (location != null) {
                            callback.onLocationRetrieved(location.getLatitude(), location.getLongitude());
                        } else {
                            requestNewLocation(activity, callback);
                        }
                    })
                    .addOnFailureListener(e -> callback.onFailure("Failed to get location: " + e.getMessage()));
        }

        @SuppressLint("MissingPermission")
        private static void requestNewLocation(Activity activity, LocationCallbackListener callback) {
            LocationRequest locationRequest = LocationRequest.create()
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setInterval(5000)
                    .setFastestInterval(2000);

            fusedLocationClient.requestLocationUpdates(locationRequest, new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    fusedLocationClient.removeLocationUpdates(this);
                    if (locationResult != null && locationResult.getLastLocation() != null) {
                        Location location = locationResult.getLastLocation();
                        callback.onLocationRetrieved(location.getLatitude(), location.getLongitude());
                    } else {
                        callback.onFailure("Location result was null.");
                    }
                }
            }, Looper.getMainLooper());
        }
}
