package com.unitalk.utils.customview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.unitalk.R;
import com.unitalk.enums.MoodCardInterface;

import java.util.List;

public class MoodCardView extends LinearLayout {

    private OnItemClickListener mClickListener;

    public MoodCardView(Context context) {
        super(context);
    }

    public MoodCardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setItems(List<? extends MoodCardInterface> items) {
        int innerLayoutsCount = 0;
        LinearLayout innerLinearLayout = null;

        for (int i = 0; i < items.size(); i++) {
            if (innerLayoutsCount == 0) {
                innerLinearLayout = initLinearLayout();
            }

            innerLinearLayout.addView(createCardView(items.get(i)));

            if (innerLayoutsCount == 1) {
                this.addView(innerLinearLayout);
                innerLayoutsCount = 0;
            } else {
                innerLayoutsCount++;
            }
        }
    }

    private CardView createCardView(MoodCardInterface item) {
        CardView cardView = (CardView) LayoutInflater.from(this.getContext())
                .inflate(R.layout.item_card_mood_checking, null, false);
        cardView.setOnClickListener(v -> {
            if (mClickListener != null) {
                mClickListener.onItemClick(item);
            }
        });
        //set params to cardView
        LayoutParams params = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.MATCH_PARENT,
                1f);

        params.setMargins(5, 5, 5, 5);

        cardView.setLayoutParams(params);

        ImageView cardImage = cardView.findViewById(R.id.ivMoodCard);
        TextView cardText = cardView.findViewById(R.id.tvMoodCardTitle);

        cardImage.setImageDrawable(ContextCompat.getDrawable(this.getContext(), item.getImage()));
        cardText.setText(item.getDescription());
        cardText.setTextColor(ContextCompat.getColor(this.getContext(), item.getTextColor()));
        cardView.setCardBackgroundColor(ContextCompat.getColor(this.getContext(), item.getBackgroundColor()));

        cardImage.setScaleX(0.7f);
        cardImage.setScaleY(0.7f);

        cardText.setScaleX(0.9f);
        cardText.setScaleY(0.9f);

        return cardView;
    }

    private LinearLayout initLinearLayout() {
        LinearLayout innerLinearLayout = new LinearLayout(this.getContext());

        //  set vertical weightSum, width
        innerLinearLayout.setOrientation(HORIZONTAL);

        //set weight
        innerLinearLayout.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT,
                1f));

        return innerLinearLayout;
    }

    public void setClickListener(OnItemClickListener clickListener) {
        mClickListener = clickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(MoodCardInterface item);
    }
}
