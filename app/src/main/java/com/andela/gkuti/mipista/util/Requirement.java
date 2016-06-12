package com.andela.gkuti.mipista.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.widget.Toast;

import com.andela.gkuti.mipista.R;

/**
 * Requirement class
 */
public class Requirement {
    private Context context;

    /**
     * Constructor for Requirement class
     *
     * @param context
     */
    public Requirement(Context context) {
        this.context = context;
    }

    /**
     * the method is called to check if gps and data service is turned on
     */
    public void check() {
        String provider = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        if (!provider.equals("")) {
            checkConnectivity();
        } else {
            showDialog();
        }
    }

    /**
     * method is called to show a dialog to goto location service settings
     */
    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        String message = context.getString(R.string.gps_error_message);
        builder.setMessage(message).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface d, int id) {
                context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                d.dismiss();
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface d, int id) {
                d.cancel();
            }
        });
        builder.create().show();
    }

    /**
     * method for checking if wifi of mobile dat is enabled
     */
    private void checkConnectivity() {
        ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo.State mobile = conMan.getNetworkInfo(0).getState();
        NetworkInfo.State wifi = conMan.getNetworkInfo(1).getState();
        if (!(mobile == NetworkInfo.State.CONNECTED || wifi == NetworkInfo.State.CONNECTED)) {
            Toast.makeText(context, R.string.data_error_message, Toast.LENGTH_LONG).show();
        }
    }
}
