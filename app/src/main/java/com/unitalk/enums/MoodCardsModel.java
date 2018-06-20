package com.unitalk.enums;

import android.support.annotation.NonNull;

import com.unitalk.R;

import java.io.Serializable;

public enum MoodCardsModel implements MoodCardInterface, Serializable {
    KIND_LOVING(R.color.moodCardColorRed2, R.color.moodCardTextColorRed, R.drawable.ic_love_70dp, R.string.card_view_kind_loving),
    SECURE_CALM(R.color.moodCardColorBlue, R.color.moodCardTextColorBlue, R.drawable.ic_relief_70, R.string.card_view_secure_calm),
    I_UNDERSTAND_MYSELF(R.color.moodCardColorYellow, R.color.moodCardTextColorYellow, R.drawable.ic_understand_50dp, R.string.card_view_i_understand_myself),
    I_HAVE_A_SOLUTION(R.color.moodCardColorPurple, R.color.moodCardTextColorPurple, R.drawable.ic_solution_50dp, R.string.card_view_i_have_a_solution),
    PEACEFUL_SATISFIED(R.color.moodCardColorYellow2, R.color.moodCardTextColorYellow2, R.drawable.ic_satisfied_70dp, R.string.card_view_peaceful_satisfied),
    ABLE_CONFIDENT(R.color.moodCardColorPink, R.color.moodCardTextColorPink, R.drawable.ic_happy_70dp, R.string.card_view_able_confident),
    AFRAID_AGGRESSIVE(R.color.moodCardColorRed, R.color.moodCardTextColorRed2, R.drawable.ic_failure_70dp, R.string.card_view_afraid_aggressive),
    ANGRY_BLAMING(R.color.moodCardColorOrange, R.color.moodCardTextColorOrange, R.drawable.ic_angry_70dp, R.string.card_view_angry_blaming),
    SUBMISSIVE_PLEASER(R.color.moodCardColorGreen, R.color.moodCardTextColorGreen, R.drawable.ic_wanted_70dp, R.string.card_view_submissive_manipulative),
    CONTROLLING_MANIPULATIVE(R.color.moodCardColorBlue, R.color.moodCardTextColorBlue2, R.drawable.ic_controlled_70dp, R.string.card_view_controlling_manipulative),

    //one card
    CONGRATULATIONS(R.color.moodCardColorYellow, R.color.moodCardTextColorYellow, R.drawable.ic_heart, R.string.card_view_congratulations),
    HAND_SHAKE(R.color.moodCardColorPurple, R.color.moodCardTextColorPurple, R.drawable.ic_hand_shake, R.string.card_view_congratulations);

    private int backgroundColor;
    private int textColor;
    private int image;
    private int description;

    MoodCardsModel(final int backgroundColor, final int textColor, final int image, final int description) {
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
        this.image = image;
        this.description = description;
    }

    @Override
    public int getBackgroundColor() {
        return backgroundColor;
    }

    @Override
    public int getTextColor() {
        return textColor;
    }

    @Override
    public int getImage() {
        return image;
    }

    @Override
    public int getDescription() {
        return description;
    }

    @NonNull
    @Override
    public String getName() {
        return this.name();
    }
}
