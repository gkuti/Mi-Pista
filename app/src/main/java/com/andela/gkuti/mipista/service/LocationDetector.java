package com.andela.gkuti.mipista.service;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;

/**
 * LocationDetector class
 */
public class LocationDetector implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private GoogleApiClient googleApiClient;
    private Context context;
    private String country;
    private String state;
    private String locality;
    private String street;
    private double longitude;
    private double latitude;
    private Intent intent;
    private String newLocation = "unknown";

    /**
     * Constructor for LocationDetector class
     *
     * @param context activity context
     */
    public LocationDetector(Context context) {
        this.context = context;
        intent = new Intent("location");
    }

    /**
     * called when connection was successful
     */
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(60000);
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        }
    }

    /**
     * called when the connection was suspended
     */
    @Override
    public void onConnectionSuspended(int i) {
        /* This method is called only when you gps is on but location could not be detected due to data service.
           But since the Requirement class checks for data services before tracking is started therefore
           this callback is not reached.
         */
    }

    /**
     * called when the connection failed
     */
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        /* This method is called only when you try to connect to client without having gps on.
           But since the Requirement class prompts the user to switch gps on before tracking is started,
           this callback will not be reached.
         */
    }

    /**
     * method for setting up a connection
     */
    public void connect() {
        country = " ";
        state = " ";
        locality = " ";
        street = " ";
        googleApiClient = new GoogleApiClient.Builder(context).addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        googleApiClient.connect();
    }

    /**
     * method for disconnecting the connection
     */
    public void disconnect() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                googleApiClient, this);
    }

    /**
     * callback for changes in location
     *
     * @param location the new location
     */
    @Override
    public void onLocationChanged(Location location) {
        longitude = location.getLongitude();
        latitude = location.getLatitude();
        detectLocation();
    }

    /**
     * method for detecting user location using Geocoder
     */
    private void detectLocation() {
        Geocoder geocoder = new Geocoder(context);
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null) {
                decodeAddress(addresses);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (getAddress()) {
            updateLocation();
        }
    }

    /**
     * method for getting the user location
     *
     * @return the return location else returns unknown
     */
    public String getLocation() {
        if (getAddress()) {
            return street + " " + locality + " " + state + " " + country;
        }
        return null;
    }

    /**
     * method for checking if the user address has been detected
     *
     * @return true if the address has been detected else false
     */
    private boolean getAddress() {
        return (!country.equals(" ") || !state.equals(" ") || !locality.equals(" ") || !street.equals(" "));
    }

    /**
     * assigns data to location parameters
     *
     * @param addresses list of address
     */
    private void decodeAddress(List<Address> addresses) {
        Address address = addresses.get(0);
        country = address.getCountryName();
        state = address.getAdminArea();
        locality = address.getSubLocality();
        street = address.getFeatureName();
    }

    /**
     * method for creating a new intent if a new location was detected
     */
    private void updateLocation() {
        String location = getLocation();
        if (location != null && !location.equals(newLocation)) {
            newLocation = getLocation();
            intent.putExtra("Location", getLocation());
            context.sendBroadcast(intent);
        }
    }
}
