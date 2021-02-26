package com.example.leisureapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DiffUtil;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeableMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private static final String TAG = HomeFragment.class.getSimpleName();

    private CardStackLayoutManager manager;
    private CardStackAdapter adapter;

    public HomeFragment() {
        // Required empty public constructor
    }


    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CardStackView cardStackView = view.findViewById(R.id.card_stack_view);
        manager = new CardStackLayoutManager(view.getContext(), new CardStackListener() {
            @Override
            public void onCardDragging(Direction direction, float ratio) {
                // Method called when card is dragged
            }

            @Override
            public void onCardSwiped(Direction direction) {
                // Call Action for swipe method
                if (direction == Direction.Left) {
                    // LEFT SWIPE -> Favorite
                    Toast.makeText(view.getContext(), "Direction LEFT", Toast.LENGTH_SHORT).show();
                } else if (direction == Direction.Right) {
                    // RIGHT SWIPE -> Next
                    Toast.makeText(view.getContext(), "Direction RIGHT", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCardRewound() {
                // Method called when rewind-method is called
            }

            @Override
            public void onCardCanceled() {
                // Method called when stop dragging without swipe
            }

            @Override
            public void onCardAppeared(View view, int position) {
                TextView tv = view.findViewById(R.id.itemName);
                Log.d(TAG, "onCardAppeared=" + position + ", name=" + tv.getText());
            }

            @Override
            public void onCardDisappeared(View view, int position) {
                // Method called when card is swiped and disappears
            }
        });

        // See documentation for settings: https://github.com/yuyakaido/CardStackView
        manager.setStackFrom(StackFrom.None);
        manager.setVisibleCount(3);
        manager.setTranslationInterval(8.0f);
        manager.setScaleInterval(0.95f);
        manager.setSwipeThreshold(0.3f);
        manager.setMaxDegree(20.0f);
        manager.setDirections(Direction.HORIZONTAL);
        manager.setCanScrollHorizontal(true);
        manager.setSwipeableMethod(SwipeableMethod.Manual);
        manager.setOverlayInterpolator(new LinearInterpolator());

        adapter = new CardStackAdapter(addList());
        cardStackView.setLayoutManager(manager);
        cardStackView.setAdapter(adapter);
        cardStackView.setItemAnimator(new DefaultItemAnimator());
    }


    private List<ItemModel> addList() {
        // Get Data for cards
        List<ItemModel> items = new ArrayList<ItemModel>();
        items.add((new ItemModel("TEST-1")));
        items.add((new ItemModel("TEST-2")));
        items.add((new ItemModel("TEST-3")));
        items.add((new ItemModel("TEST-4")));
        items.add((new ItemModel("TEST-5")));

        return items;
    }
}