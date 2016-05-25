package com.andela.gkuti.mipista;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.widget.Toast;

public class Requirement {
    private Context context;

    public Requirement(Context context) {
        this.context = context;
    }

    public void check() {
        String provider = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        if (!provider.equals("")) {
            checkConnectivity();
        } else {
            showDialog();
        }

    }

    private void showDialog() {
        final AlertDialog.Builder builder =
                new AlertDialog.Builder(context);
        String message = "Enable either GPS or any other location"
                + " service to find current location.  Click OK to go to"
                + " location services settings.";

        builder.setMessage(message)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                                d.dismiss();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                d.cancel();
                            }
                        });
        builder.create().show();
    }

    private void checkConnectivity() {
        ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo.State mobile = conMan.getNetworkInfo(0).getState();
        NetworkInfo.State wifi = conMan.getNetworkInfo(1).getState();
        if (!(mobile == NetworkInfo.State.CONNECTED || wifi == NetworkInfo.State.CONNECTED)) {
            Toast.makeText(context, "Data service not available Locations will be stored as unknown", Toast.LENGTH_LONG).show();
        }
    }
}
