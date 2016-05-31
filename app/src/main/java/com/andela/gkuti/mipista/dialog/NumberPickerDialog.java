package com.andela.gkuti.mipista.dialog;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import com.andela.gkuti.mipista.R;
import com.andela.gkuti.mipista.callback.NumberPickCallback;

/**
 * NumberPickerDialog class
 */
public class NumberPickerDialog extends DialogFragment {
    private Dialog dialog;
    private Button cancelButton;
    private Button okButton;
    private NumberPickCallback callback;
    private NumberPicker numberPicker;

    /**
     * method for setting the callback for the class
     *
     * @param callback
     */
    public void setCallback(NumberPickCallback callback) {
        this.callback = callback;
    }

    /**
     * method called when the dialog is about to start
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialog = new Dialog(getContext());
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.setContentView(R.layout.number_dialog_v21);
        } else {
            dialog.setContentView(R.layout.number_dialog);
        }
        initViews();
        return dialog;
    }

    /**
     * method for initializing views
     */
    private void initViews() {
        cancelButton = (Button) dialog.findViewById(R.id.cancel);
        okButton = (Button) dialog.findViewById(R.id.okay);
        numberPicker = (NumberPicker) dialog.findViewById(R.id.numberPicker);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(30);
        assignClickHandlers();
    }

    /**
     * method for setting listeners to buttons
     */
    private void assignClickHandlers() {
        cancelButton.setOnClickListener(cancelListener);
        okButton.setOnClickListener(addListener);
    }

    private View.OnClickListener cancelListener = new View.OnClickListener() {
        /**
         * method called when the cancel button is clicked
         */
        @Override
        public void onClick(View v) {
            dialog.dismiss();
        }
    };

    private View.OnClickListener addListener = new View.OnClickListener() {
        /**
         * method called when the ok button is clicked
         */
        @Override
        public void onClick(View v) {
            int value = numberPicker.getValue();
            callback.onNumberPick(value);
            dialog.dismiss();
        }
    };

}
