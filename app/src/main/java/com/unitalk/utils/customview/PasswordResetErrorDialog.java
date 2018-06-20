package com.unitalk.utils.customview;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;

import com.unitalk.R;
import com.unitalk.utils.ResourcesManager;

public class PasswordResetErrorDialog extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_password_reset_error, null))
                .setPositiveButton(R.string.ok, (dialog, id) -> PasswordResetErrorDialog.this.getDialog().cancel());
        final AlertDialog dialog = builder.create();
        dialog.setOnShowListener(dialog1 -> dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(ResourcesManager.getColor(R.color.colorDialogButton)));
        return dialog;
    }
}
