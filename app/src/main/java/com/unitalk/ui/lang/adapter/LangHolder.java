package com.unitalk.ui.lang.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.unitalk.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LangHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.lang_text_item)
    TextView langTextItem;
    @BindView(R.id.eng_label)
    TextView engLabel;
    @BindView(R.id.check_view)
    ImageView checkView;
    @BindView(R.id.lang_frame)
    FrameLayout langFrame;

    private ItemClick listener;

    public LangHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        langFrame.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        listener.onItemClick(v, this.getLayoutPosition());
    }

    public void setOnItemClickListener(ItemClick listener) {
        this.listener = listener;
    }

}
