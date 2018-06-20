package com.unitalk.ui;

import android.support.annotation.NonNull;
import android.util.Log;

import com.unitalk.core.App;
import com.unitalk.network.NetworkManager;
import com.unitalk.network.model.CardsClickedData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class BaseQueriesPresenterImpl extends BasePresenterImpl implements BaseQueriesPresenter {
    private final Callback<Void> onSendCardsClickedDataCallback = new Callback<Void>() {
        private static final String TAG = "sendCardsClicked";

        @Override
        public void onResponse(@NonNull final Call<Void> call, @NonNull final Response<Void> response) {
            if (response.isSuccessful()) {
                Log.d(TAG, "success");
            } else {
                Log.d(TAG, "Code: " + response.code());
            }
        }

        @Override
        public void onFailure(@NonNull final Call<Void> call, @NonNull final Throwable t) {
            Log.d(TAG, "failure");
        }
    };

    @Override
    public void sendCardsClickedData() {
        if (!App.getInstance().getStringArrayDataLocalStorage().isArrayDataEmpty()) {
            final String calculationID = App.getInstance().getSharedManager().getCalculationID();
            final List<String> cardsClickedList = App.getInstance().getStringArrayDataLocalStorage().getArrayData();
            final CardsClickedData cardsClickedData = new CardsClickedData();
            cardsClickedData.setCardsClickedList(cardsClickedList);
            final Call<Void> call = NetworkManager.getInstance().getNetworkApi().sendUserCards(calculationID, cardsClickedData);
            call.enqueue(onSendCardsClickedDataCallback);
            App.getInstance().getStringArrayDataLocalStorage().clearArrayData();
        }
    }
}
