package com.unitalk.ui.harmony;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.unitalk.R;
import com.unitalk.core.App;
import com.unitalk.enums.CardsState;
import com.unitalk.enums.MoodCardsModel;
import com.unitalk.ui.BaseFragment;
import com.unitalk.ui.callback.OnScreenNavigationCallback;
import com.unitalk.ui.recording.sampling.VoiceSamplingFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Completable;
import io.reactivex.disposables.Disposable;

public class HarmonyOneCardFragment extends BaseFragment {
    private static final int SPLASH_DELAY = 3;
    private Disposable splashTimerDisposable;
    private static final String CARD_NAME_KEY = "CARD_NAME_KEY";
    @BindView(R.id.cvMoodCard)
    CardView cvMoodCard;
    @BindView(R.id.tvMoodCardTitle)
    TextView tvMoodCardTitle;
    @BindView(R.id.ivMoodCard)
    ImageView ivMoodCard;

    private MoodCardsModel moodCardsModel;
    private OnScreenNavigationCallback onScreenNavigationCallback;

    /**
     * @param moodCardName - card to show in CardView
     * @return - fragment with params in
     */
    public static HarmonyOneCardFragment newInstance(final String moodCardName) {
        final Bundle args = new Bundle();
        args.putString(CARD_NAME_KEY, moodCardName);
        final HarmonyOneCardFragment fragment = new HarmonyOneCardFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int provideLayout() {
        return R.layout.fragment_harmony_one_card;
    }

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        if (context instanceof OnScreenNavigationCallback) {
            onScreenNavigationCallback = (OnScreenNavigationCallback) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            moodCardsModel = MoodCardsModel.valueOf(getArguments().getString(CARD_NAME_KEY));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        splashTimerDisposable = Completable.timer(SPLASH_DELAY, TimeUnit.SECONDS).doOnComplete(this::onNextScreen).subscribe();
    }

    @Override
    public void onStop() {
        super.onStop();
        splashTimerDisposable.dispose();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setData();
    }

    private void setData() {
        cvMoodCard.setCardBackgroundColor(ContextCompat.getColor(getActivity(), moodCardsModel.getBackgroundColor()));
        ivMoodCard.setImageDrawable(ContextCompat.getDrawable(getActivity(), moodCardsModel.getImage()));
        tvMoodCardTitle.setTextColor(ContextCompat.getColor(getActivity(), moodCardsModel.getTextColor()));
        tvMoodCardTitle.setText(moodCardsModel.getDescription());
    }

    public void onNextScreen() {
        App.getInstance().getStringArrayDataLocalStorage().add(moodCardsModel.getName());
        Log.d("---", moodCardsModel.getName());
        if (moodCardsModel == MoodCardsModel.CONGRATULATIONS) {
            if (onScreenNavigationCallback != null) {
                App.getInstance().getSharedManager().setCardsState(CardsState.NONE.name());
                onScreenNavigationCallback.startFragmentTransactionFromRightToLeft(VoiceSamplingFragment.newInstance());
            }
        } else if (moodCardsModel == MoodCardsModel.HAND_SHAKE) {
            if (onScreenNavigationCallback != null) {
                onScreenNavigationCallback.startFragmentTransactionFromRightToLeft(
                        HarmonyCheckingCardFragmentImpl.newInstance(createFourMoodItemsVer2()));
            }
        }
    }

    // FIXME: 4/20/18 change this approach
    private List<String> createFourMoodItemsVer2() {
        final List<String> list = new ArrayList<>();
        list.add(MoodCardsModel.SECURE_CALM.getName());
        list.add(MoodCardsModel.PEACEFUL_SATISFIED.getName());
        list.add(MoodCardsModel.ABLE_CONFIDENT.getName());
        list.add(MoodCardsModel.KIND_LOVING.getName());
        return list;
    }
}
