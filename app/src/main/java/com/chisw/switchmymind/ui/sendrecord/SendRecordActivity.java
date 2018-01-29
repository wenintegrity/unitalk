package com.chisw.switchmymind.ui.sendrecord;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.chisw.switchmymind.R;

/**
 * Created by user on 19.01.18.
 */

public class SendRecordActivity extends AppCompatActivity implements SendRecordView {
    private SendRecordPresenter<SendRecordView> presenter;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_record);
        presenter = new SendRecordPresenterImpl();
    }
}
