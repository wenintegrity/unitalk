package com.unitalk.ui.lang;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.unitalk.R;
import com.unitalk.ui.lang.adapter.LangAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LangActivity extends AppCompatActivity implements LangView {

    @BindView(R.id.lang_list)
    RecyclerView recyclerView;

    private LangAdapter langAdapter;
    private LangPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lang);
        ButterKnife.bind(this);

        presenter = new LangPresenterImpl();
        presenter.attachView(this);

        langAdapter = new LangAdapter(presenter, this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(langAdapter);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
    }

    @Override
    public void updateList() {
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.detachView();
        }
    }
}
