package com.unitalk.utils.customview;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;

import com.unitalk.R;
import com.unitalk.core.App;
import com.unitalk.utils.ResourcesManager;

public class NotificationSettingsDialog extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_notification_settings, null))
                .setPositiveButton(R.string.ok, (dialog, id) -> {
                    NotificationSettingsDialog.this.getDialog().cancel();
                    final Intent intent = new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                    startActivity(intent);
                    App.getInstance().getSharedManager().setDialogClosed(true);
                });
        final AlertDialog dialog = builder.create();
        dialog.setOnShowListener(dialog1 -> dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(ResourcesManager.getColor(R.color.colorDialogButton)));
        return dialog;
    }
}
