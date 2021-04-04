package com.example.leisureapp.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;

import com.example.leisureapp.utils.LeisureSingleton;
import com.example.leisureapp.R;
import com.example.leisureapp.adapters.CardStackAdapter;
import com.example.leisureapp.database.DatabaseManager;
import com.example.leisureapp.interfaces.VolleyCallback;
import com.example.leisureapp.models.ItemModel;
import com.google.gson.Gson;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeableMethod;

import org.json.JSONException;

import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class HomeFragment extends Fragment {
    private CardStackLayoutManager manager;
    private CardStackAdapter adapter;

    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CardStackView cardStackView = view.findViewById(R.id.card_stack_view);

        TextView errorText = (TextView) view.findViewById(R.id.noActivityFoundText);
        errorText.setVisibility(View.INVISIBLE);
        TextView noInternetText = (TextView) view.findViewById(R.id.noInternetConnectionText);
        noInternetText.setVisibility(View.INVISIBLE);

        manager = new CardStackLayoutManager(view.getContext(), new CardStackListener() {
            @Override
            public void onCardDragging(Direction direction, float ratio) {
            }

            @Override
            public void onCardSwiped(Direction direction) {
                if (direction == Direction.Left) {
                    ItemModel item = adapter.getItems().get(manager.getTopPosition() - 1);

                    DatabaseManager db = new DatabaseManager(getActivity());
                    db.insertFavorite(item, view.getContext());
                }

                getCardDetails(view, noInternetText);
            }

            @Override
            public void onCardRewound() {
            }

            @Override
            public void onCardCanceled() {
            }

            @Override
            public void onCardAppeared(View view, int position) {
            }

            @Override
            public void onCardDisappeared(View view, int position) {
            }
        });

        manager.setStackFrom(StackFrom.None);
        manager.setVisibleCount(3);
        manager.setTranslationInterval(8.0f);
        manager.setScaleInterval(0.95f);
        manager.setSwipeThreshold(0.5f);
        manager.setMaxDegree(20.0f);
        manager.setDirections(Direction.HORIZONTAL);
        manager.setCanScrollHorizontal(true);
        manager.setSwipeableMethod(SwipeableMethod.Manual);
        manager.setOverlayInterpolator(new LinearInterpolator());
        manager.setStackFrom(StackFrom.Top);
        adapter = new CardStackAdapter(new ArrayList<>());

        cardStackView.setLayoutManager(manager);
        cardStackView.setAdapter(adapter);
        cardStackView.setItemAnimator(new DefaultItemAnimator());

        DatabaseManager db = new DatabaseManager(getActivity());
        ItemModel[] tmp = db.getTmp();

        if (tmp.length != 0) {
            for (int i = 0; i <= tmp.length - 1; i++) {
                adapter.addItem(0, tmp[i]);
            }
        } else {
            for (int i = 0; i < 3; i++) {
                getCardDetails(view, noInternetText);
            }
        }
    }

    public ItemModel createItem(String boredApiResponse, String unsplashApiResponse) {
        if (boredApiResponse != null) {
            ItemModel itemModel = new Gson().fromJson(boredApiResponse, ItemModel.class);

            ItemModel newItem = new ItemModel(
                    itemModel.getActivity(),
                    itemModel.getType(),
                    itemModel.getParticipants(),
                    itemModel.getPrice(),
                    itemModel.getLink(),
                    itemModel.getKey(),
                    itemModel.getAccessibility(),
                    unsplashApiResponse);

            return newItem;
        } else {
            Log.e("Error creating card item", "No data available");
            return null;
        }
    }

    public void getCardDetails(View view, TextView noInternetText){
        LeisureSingleton.getInstance(getActivity()).fetchActivity(new VolleyCallback() {
            @Override
            public void onSuccessResponse(String boredApiResponse) {
                LeisureSingleton.getInstance(getActivity()).fetchImageURL(new VolleyCallback() {
                    @Override
                    public void onSuccessResponse(String unsplashApiResponse) {
                        ItemModel newItem = createItem(boredApiResponse, unsplashApiResponse);

                        Activity activity = getActivity();
                        if(activity != null){
                            DatabaseManager db = new DatabaseManager(getActivity());
                            db.insertTmp(newItem, getResources().getString(R.string.no_img_url));
                        }

                        adapter.addItem(adapter.getItemCount(), newItem);
                    }

                    @Override
                    public void onError(String result) {
                        ItemModel newItem = createItem(boredApiResponse, getResources().getString(R.string.unsplash_error_replace_url));

                        adapter.addItem(adapter.getItemCount(), newItem);
                    }
                }, boredApiResponse);

            }

            @Override
            public void onError(String result) {
                TextView errorText = (TextView) view.findViewById(R.id.noActivityFoundText);
                errorText.setVisibility(View.VISIBLE);
            }

        }, noInternetText);
    }
}