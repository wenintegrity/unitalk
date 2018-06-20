package com.unitalk.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.unitalk.network.model.CurrentLocationData;

public class CurrentLocationManager {
    private static final String TAG = CurrentLocationManager.class.getName();
    private static final long MIN_TIME = 0;
    private static final long MIN_DISTANCE = 10;

    private Context context;
    private CurrentLocationData currentLocationData;

    public CurrentLocationManager(@NonNull final Context context) {
        this.context = context;
        init();
    }

    private void init() {
        currentLocationData = new CurrentLocationData();
        final LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, new CurrentLocationListener());
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, new CurrentLocationListener());

        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (location == null){
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        if (location != null){
            setCurrentLocation(location.getLatitude(), location.getLongitude());
        }
    }

    public CurrentLocationData getCurrentLocationData() {
        return currentLocationData;
    }

    private void setCurrentLocation(final double latitude, final double longitude){
        currentLocationData.setLatitude(latitude);
        currentLocationData.setLongitude(longitude);
        Log.v(TAG, "(" + latitude + "," + longitude + ")");
    }

    private final class CurrentLocationListener implements LocationListener {
        public void onLocationChanged(final Location location) {
            final double longitude = location.getLongitude();
            final double latitude = location.getLatitude();
            setCurrentLocation(latitude, longitude);
        }

        public void onProviderDisabled(final String provider){}
        public void onProviderEnabled(final String provider){}
        public void onStatusChanged(final String provider, final int status, final Bundle extras){}
    }
}
