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
    private String newLocation = "";

    public LocationDetector(Context context) {
        this.context = context;
        intent = new Intent("location");
    }

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

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

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

    public void disconnect() {
        try {
            LocationServices.FusedLocationApi.removeLocationUpdates(
                    googleApiClient, this);
        } catch (Exception e) {
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        longitude = location.getLongitude();
        latitude = location.getLatitude();
        detectCountry();
    }

    private void detectCountry() {
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

    public String getLocation() {
        if (getAddress()) {
            return street + " " + locality + " " + state + " " + country;
        }
        return "unknown";
    }

    private boolean getAddress() {
        return (!country.equals(" ") || !state.equals(" ") || !locality.equals(" ") || !street.equals(" "));
    }

    private void decodeAddress(List<Address> addresses) {
        Address address = addresses.get(0);
        country = address.getCountryName();
        state = address.getAdminArea();
        locality = address.getSubLocality();
        street = address.getFeatureName();
    }

    private void updateLocation() {
        if (!getLocation().equals(newLocation)) {
            newLocation = getLocation();
            intent.putExtra("Location", getLocation());
            context.sendBroadcast(intent);
        }
    }
}
