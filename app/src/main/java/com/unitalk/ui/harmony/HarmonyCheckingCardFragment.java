package com.unitalk.ui.harmony;

import com.unitalk.enums.MoodCardsModel;

import java.util.List;

public interface HarmonyCheckingCardFragment {
    void setToGoToCards();
    void bindCardView(final List<MoodCardsModel> cardList);
}
