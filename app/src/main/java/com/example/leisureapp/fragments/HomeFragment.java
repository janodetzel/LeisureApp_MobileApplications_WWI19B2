package com.example.leisureapp.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.leisureapp.LeisureSingleton;
import com.example.leisureapp.R;
import com.example.leisureapp.adapters.CardStackAdapter;
import com.example.leisureapp.database.DatabaseManager;
import com.example.leisureapp.interfaces.VolleyCallback;
import com.example.leisureapp.models.ItemModel;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeableMethod;

import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private static final String TAG = HomeFragment.class.getSimpleName();
    String unsplashAuth = "&client_id=cOMcQHsoAAQBMhZUAR-2zZRQyNBb0lvufuME78DiDdc";
    String boredURL = "https://www.boredapi.com/api/activity/";
    String unsplashURL = "https://api.unsplash.com/search/photos?orientation=portrait&page1&query=";
    String unsplashRandomURL = "https://api.unsplash.com/photos?orientation=portrait/random/" + unsplashAuth;


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
            private Direction direction;

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

                    // Get key from adapter
                    ItemModel item = adapter.getItems().get(manager.getTopPosition() - 1);

                    // Insert item in local db
                    DatabaseManager db = new DatabaseManager(getActivity());
                    db.insertFavorite(item);

                    Log.d(TAG, "Insert favorite in db with key: " + item.getKey());

                } else if (direction == Direction.Right) {
                    // RIGHT SWIPE -> Next
                    Toast.makeText(view.getContext(), "Direction RIGHT", Toast.LENGTH_SHORT).show();
                }

                LeisureSingleton.getInstance(getActivity()).addToRequestQueue(objectRequest);
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
                TextView tv = view.findViewById(R.id.activityText);
                //Log.d(TAG, "onCardAppeared=" + position + ", name=" + tv.getText());
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
        manager.setSwipeThreshold(0.5f);
        manager.setMaxDegree(20.0f);
        manager.setDirections(Direction.HORIZONTAL);
        manager.setCanScrollHorizontal(true);
        manager.setSwipeableMethod(SwipeableMethod.Manual);
        manager.setOverlayInterpolator(new LinearInterpolator());
        manager.setStackFrom(StackFrom.Top);
        adapter = new CardStackAdapter(new ArrayList<ItemModel>());

        cardStackView.setLayoutManager(manager);
        cardStackView.setAdapter(adapter);
        cardStackView.setItemAnimator(new DefaultItemAnimator());

        DatabaseManager db = new DatabaseManager(getActivity());
        ItemModel[] tmp = db.getTmp();

        if (tmp.length != 0) {
            for (int i = 0; i <= tmp.length - 1; i++) {
                Log.e("Message", tmp[i].getActivity());
                adapter.addItem(0, tmp[i]);
            }
        } else {
            for (int i = 0; i < 3; i++) {
                LeisureSingleton.getInstance(getActivity()).addToRequestQueue(objectRequest);
            }
        }
    }

    JsonObjectRequest objectRequest = new JsonObjectRequest(
            Request.Method.GET,
            boredURL,
            null,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    final String[] imgUrl = {""};
                    imgURLRequest(Request.Method.GET, unsplashURL + "house" + unsplashAuth, null,
                            new VolleyCallback() {
                                @Override
                                public void onSuccessResponse(String result) {
                                    Log.d("Success Image", result);
                                    imgUrl[0] = result;
                                }
                            });

                    Log.d("Bored API response", response.toString());
                    ItemModel itemModel = new Gson().fromJson(response.toString(), ItemModel.class);

                    Log.d("Activity", itemModel.getActivity());
                    Log.d("Image", imgUrl[0] + "imageurl");

                    ItemModel newItem = new ItemModel(
                            itemModel.getActivity(),
                            itemModel.getType(),
                            itemModel.getParticipants(),
                            itemModel.getPrice(),
                            itemModel.getLink(),
                            itemModel.getKey(),
                            itemModel.getAccessibility(),
                            itemModel.getImgURL());

                    DatabaseManager db = new DatabaseManager(getActivity());

                    db.insertTmp(newItem);

                    adapter.addItem(adapter.getItemCount(), newItem);

                }
            }, error -> Log.e("Error Response", error.toString())
    );

    public void imgURLRequest(int method, String url, JSONObject jsonValue, final VolleyCallback callback) {

        JsonObjectRequest unsplashRequest = new JsonObjectRequest(
                method,
                url,
                jsonValue,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Unsplash API response", response.toString());

                        String imgUrl = JsonParser.parseString(response.toString())
                                .getAsJsonObject()
                                .get("results")
                                .getAsJsonArray()
                                .get(0)
                                .getAsJsonObject()
                                .get("urls")
                                .getAsJsonObject()
                                .get("regular")
                                .getAsString();

                        Log.d("Unsplash imgURL", imgUrl);

                        callback.onSuccessResponse(imgUrl);
                    }
                }, error -> Log.e("Error Response", error.toString())
        );

        LeisureSingleton.getInstance(getActivity()).addToRequestQueue(unsplashRequest);
    }

}