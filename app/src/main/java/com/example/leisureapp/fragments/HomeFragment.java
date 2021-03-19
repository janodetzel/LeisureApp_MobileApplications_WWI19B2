package com.example.leisureapp.fragments;

import android.content.ClipData;
import android.content.Context;
import android.content.SharedPreferences;
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
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.leisureapp.utils.LeisureSingleton;
import com.example.leisureapp.R;
import com.example.leisureapp.adapters.CardStackAdapter;
import com.example.leisureapp.database.DatabaseManager;
import com.example.leisureapp.interfaces.VolleyCallback;
import com.example.leisureapp.models.ItemModel;
import com.example.leisureapp.utils.SharedPreferencesHelper;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeableMethod;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private static final String TAG = HomeFragment.class.getSimpleName();
    String unsplashAuth = "&client_id=cOMcQHsoAAQBMhZUAR-2zZRQyNBb0lvufuME78DiDdc";
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

        TextView errorText = (TextView) view.findViewById(R.id.noActivityFoundText);
        errorText.setVisibility(View.INVISIBLE);


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
                    db.insertFavorite(item, view.getContext());

                    Log.d(TAG, "Insert favorite in db with key: " + item.getKey());

                } else if (direction == Direction.Right) {
                    // RIGHT SWIPE -> Next
                    Toast.makeText(view.getContext(), "Direction RIGHT", Toast.LENGTH_SHORT).show();
                }

                //LeisureSingleton.getInstance(getActivity()).addToRequestQueue(objectRequest);

                fetchActivity(new VolleyCallback() {
                    @Override
                    public void onSuccessResponse(String response) {

                        ItemModel newItem = createItem(response);

                        DatabaseManager db = new DatabaseManager(getActivity());

                        //db.insertTmp(newItem);

                        adapter.addItem(adapter.getItemCount(), newItem);

                    }

                    @Override
                    public void onError(String result) throws Exception {

                        TextView errorText = (TextView) view.findViewById(R.id.noActivityFoundText);
                        errorText.setVisibility(View.VISIBLE);

                    }

                });
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
                fetchActivity(new VolleyCallback() {
                    @Override
                    public void onSuccessResponse(String response) {

                        ItemModel newItem = createItem(response);
                        DatabaseManager db = new DatabaseManager(getActivity());

                        //db.insertTmp(newItem);

                        adapter.addItem(adapter.getItemCount(), newItem);

                    }

                    @Override
                    public void onError(String result) throws Exception {
                        TextView errorText = (TextView) view.findViewById(R.id.noActivityFoundText);
                        errorText.setVisibility(View.VISIBLE);

                    }

                });
            }
        }
    }


    public void fetchActivity(final VolleyCallback callback) {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);

        String baseURL = "https://www.boredapi.com/api/activity";
        String minPrice = "?minprice=" + SharedPreferencesHelper.getDouble(sharedPref, String.valueOf(R.id.seekBarCosts) + "filterCostsMin", 0);
        String maxPrice = "&maxprice=" + SharedPreferencesHelper.getDouble(sharedPref, String.valueOf(R.id.seekBarCosts) + "filterCostsMax", 1);
        String participants = "&participants=" + sharedPref.getInt(String.valueOf(R.id.seekBarPersons) + "filterPersons", 1);
        String type = "&type=" + sharedPref.getString(String.valueOf(R.id.settingsTypeDropDown) + "filterTypeValue", "");

        Log.d("Bored API requeststring", baseURL + minPrice + maxPrice + participants + type);
        JsonObjectRequest boredAPIRequest = new JsonObjectRequest(
                Request.Method.GET,
                baseURL + minPrice + maxPrice + participants + type,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("Bored API response", response.toString());

                        try {
                            if (response.toString().contains("error")) {
                                callback.onError(response.toString());
                            } else {
                                callback.onSuccessResponse(response.toString());
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, error -> Log.e("Error Response", error.toString())
        );

        LeisureSingleton.getInstance(getActivity()).addToRequestQueue(boredAPIRequest);
    }

    public ItemModel createItem(String response) {

        Log.d("Create Item from String", response);

        if (response != null) {
            ItemModel itemModel = new Gson().fromJson(response, ItemModel.class);

            ItemModel newItem = new ItemModel(
                    itemModel.getActivity(),
                    itemModel.getType(),
                    itemModel.getParticipants(),
                    itemModel.getPrice(),
                    itemModel.getLink(),
                    itemModel.getKey(),
                    itemModel.getAccessibility(),
                    itemModel.getImgURL());

//            Log.d("Item Model Activity", itemModel.getActivity());

            return newItem;
        } else {
            Log.d("CreateItem failure", "No data available");
            return null;
        }
    }


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

                        try {
                            callback.onSuccessResponse(imgUrl);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, error -> Log.e("Error Response", error.toString())
        );

        LeisureSingleton.getInstance(getActivity()).addToRequestQueue(unsplashRequest);
    }

}