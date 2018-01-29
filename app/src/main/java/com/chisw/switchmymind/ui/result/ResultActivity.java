package com.chisw.switchmymind.ui.result;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.chisw.switchmymind.R;
import com.chisw.switchmymind.utils.Constants;

/**
 * Created by user on 19.01.18.
 */

public class ResultActivity extends AppCompatActivity implements ResultView {
    private ResultPresenter<ResultView> presenter;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        presenter = new ResultPresenterImpl();

        final TextView tvResult = (TextView) findViewById(R.id.tvResult);
        final String result = getResources().getString(R.string.result) + getIntent().getExtras().getString(Constants.EXTRA_RESULT);
        tvResult.setText(result);
    }
}
