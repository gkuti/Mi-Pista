package com.andela.gkuti.mipista;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;

public class LocationDetector implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private GoogleApiClient googleApiClient;
    private Context context;
    private String country;
    private String state;
    private String locality;
    private String street;

    public LocationDetector(Context context) {
        this.context = context;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            if (location != null) {
                double lat = location.getLatitude(), lon = location.getLongitude();
                Log.d("ne", String.valueOf(lon) + " " + String.valueOf(lat));
                Geocoder geocoder = new Geocoder(context);
                List<Address> addresses = null;
                try {
                    addresses = geocoder.getFromLocation(lat, lon, 1);
                    if (addresses != null && addresses.size() > 0) {
                        Address address = addresses.get(0);
                        country = address.getCountryName();
                        state = address.getAdminArea();
                        locality = address.getSubLocality();
                        street = address.getFeatureName();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void connect() {
        googleApiClient = new GoogleApiClient.Builder(context, this, this).addApi(LocationServices.API).build();
        googleApiClient.connect();
    }

    public String getCountry() {
        return country;
    }

    public String getState() {
        return state;
    }

    public String getLocality() {
        return locality;
    }

    public String getStreet() {
        return street;
    }
}
