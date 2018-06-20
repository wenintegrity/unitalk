package com.unitalk.ui.harmony;
import com.unitalk.core.App;
import com.unitalk.enums.CardsState;
import com.unitalk.enums.MoodCardsModel;

import java.util.ArrayList;
import java.util.List;

public class HarmonyCheckingCardPresenterImpl implements HarmonyCheckingCardPresenter {
    private HarmonyCheckingCardFragment view;

    public HarmonyCheckingCardPresenterImpl(final HarmonyCheckingCardFragment view) {
        this.view = view;
    }

    @Override
    public void getCardStateCounter() {
        view.setToGoToCards();
    }

    @Override
    public void createCardList(final List<String> stringList) {
        view.bindCardView(creteEnumsListFromStrings(stringList));
    }

    @Override
    public void setNextCardState() {
        if (App.getInstance().getSharedManager().getCardsState().equals(CardsState.SIX.name())) {
            App.getInstance().getSharedManager().setCardsState(CardsState.NONE.name());
        } else {
            App.getInstance().getSharedManager().setCardsState(CardsState.SIX.name());
        }
    }

    private List<MoodCardsModel> creteEnumsListFromStrings(final List<String> stringList) {
        final List<MoodCardsModel> enumList = new ArrayList<>();
        for (String sModel : stringList) {
            enumList.add(MoodCardsModel.valueOf(sModel));
        }
        return enumList;
    }
}
