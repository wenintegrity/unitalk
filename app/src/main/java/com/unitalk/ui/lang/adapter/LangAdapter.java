package com.unitalk.ui.lang.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unitalk.R;
import com.unitalk.ui.lang.LangPresenter;
import com.unitalk.ui.lang.settings_model.Lang;
import com.unitalk.utils.LocaleHelper;

import java.util.ArrayList;
import java.util.Arrays;

public class LangAdapter extends RecyclerView.Adapter<LangHolder> {

    private ArrayList<Lang> langList;
    private LangPresenter presenter;
    private Context context;

    public LangAdapter(LangPresenter presenter, Context context) {
        langList = new ArrayList<>(Arrays.asList(Lang.values()));
        this.presenter = presenter;
        this.context = context;
    }

    @NonNull
    @Override
    public LangHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lang_list_item, parent, false);
        return new LangHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LangHolder holder, int position) {
        holder.langTextItem.setText(langList.get(position).getName());
        holder.engLabel.setText(langList.get(position).getEngName());
        //holder.engLabel.setText(LocaleHelper.getLanguage(context));

        holder.checkView.setVisibility(View.INVISIBLE);
        if (LocaleHelper.getLanguage(context).toLowerCase().equals(langList.get(position).getLocale())) {
            holder.checkView.setVisibility(View.VISIBLE);
        }

        holder.setOnItemClickListener(new ItemClick() {
            @Override
            public void onItemClick(View view, int position) {

                switch (view.getId()) {
                    case R.id.lang_frame:
                        //holder.checkView.setVisibility(View.INVISIBLE);
                        presenter.changeLang(langList.get(position));
                        holder.engLabel.setText(LocaleHelper.getLanguage(context));
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (langList != null && langList.size() > 0) {
            return langList.size();
        }
        return 0;
    }
}
