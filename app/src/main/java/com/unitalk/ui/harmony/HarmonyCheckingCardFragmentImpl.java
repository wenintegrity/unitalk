package com.unitalk.ui.harmony;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.unitalk.R;
import com.unitalk.core.App;
import com.unitalk.enums.CardsState;
import com.unitalk.enums.MoodCardsModel;
import com.unitalk.ui.BaseFragment;
import com.unitalk.ui.callback.OnMainActivityCallback;
import com.unitalk.ui.callback.OnScreenNavigationCallback;
import com.unitalk.ui.lang.FragmentUpdateEvent;
import com.unitalk.ui.recording.sampling.VoiceSamplingFragment;
import com.unitalk.utils.LocaleHelper;
import com.unitalk.utils.customview.MoodCardView;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;

public class HarmonyCheckingCardFragmentImpl extends BaseFragment implements HarmonyCheckingCardFragment, FragmentUpdateEvent {

    @BindView(R.id.tv_harmony_chk_cards)
    TextView tv_harmony_chk_cards;
    @BindView(R.id.tv_cards_chk_title)
    TextView tv_cards_chk_title;

    public static final String LIST_OF_CARDS_KEY = "LIST_OF_CARDS_KEY";
    private List<String> mCardList;
    private MoodCardView moodCardsView;
    private HarmonyCheckingCardPresenter presenter;
    private OnMainActivityCallback onMainActivityCallback;
    private OnScreenNavigationCallback onScreenNavigationCallback;

    /**
     * @param items - list of MoodCardInterface implemented items to set them to custom view
     * @return - fragment with params in
     */
    public static HarmonyCheckingCardFragmentImpl newInstance(final List<String> items) {
        final Bundle args = new Bundle();
        args.putSerializable(LIST_OF_CARDS_KEY, (Serializable) items);
        final HarmonyCheckingCardFragmentImpl fragment = new HarmonyCheckingCardFragmentImpl();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    protected int provideLayout() {
        return R.layout.fragment_harmony_cards;
    }

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        if (context instanceof OnScreenNavigationCallback) {
            onScreenNavigationCallback = (OnScreenNavigationCallback) context;
        }
        if (context instanceof OnMainActivityCallback) {
            onMainActivityCallback = (OnMainActivityCallback) context;
        }
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStates();
        presenter = new HarmonyCheckingCardPresenterImpl(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        moodCardsView = view.findViewById(R.id.custom_mood_card_view);
        presenter.createCardList(mCardList);
        presenter.getCardStateCounter();
    }

    private void setStates() {
        if (getArguments() != null) {
            mCardList = (List<String>) getArguments().getSerializable(LIST_OF_CARDS_KEY);
        }
    }

    @Override
    public void bindCardView(final List<MoodCardsModel> enumList) {
        moodCardsView.setItems(enumList);
    }

    // FIXME: 5/17/18 make simpler method
    @Override
    public void setToGoToCards() {
        moodCardsView.setClickListener(item -> {
            presenter.setNextCardState();
            final MoodCardsModel moodCardsModel = MoodCardsModel.valueOf(item.getName());
            App.getInstance().getStringArrayDataLocalStorage().add(moodCardsModel.getName());
            Log.d("---", moodCardsModel.getName());
            if (moodCardsModel == MoodCardsModel.I_UNDERSTAND_MYSELF) {
                onMainActivityCallback.startHarmonyOneCardFragment(MoodCardsModel.CONGRATULATIONS.getName());
            } else if (moodCardsModel == MoodCardsModel.I_HAVE_A_SOLUTION) {
                onMainActivityCallback.startHarmonyOneCardFragment(MoodCardsModel.HAND_SHAKE.getName());
            } else {
                if (moodCardsModel == MoodCardsModel.ABLE_CONFIDENT
                        || moodCardsModel == MoodCardsModel.KIND_LOVING
                        || moodCardsModel == MoodCardsModel.PEACEFUL_SATISFIED
                        || moodCardsModel == MoodCardsModel.SECURE_CALM) {
                    App.getInstance().getSharedManager().setCardsState(CardsState.NONE.name());
                }
                onScreenNavigationCallback.startFragmentTransactionFromRightToLeft(VoiceSamplingFragment.newInstance());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        updateView();
    }

    @Override
    public void updateView() {
        Resources resources = LocaleHelper.getResources(getContext());
        tv_harmony_chk_cards.setText(resources.getString(R.string.harmony_checking));
        tv_cards_chk_title.setText(resources.getString(R.string.harmony_checking_cards_title));
    }
}
