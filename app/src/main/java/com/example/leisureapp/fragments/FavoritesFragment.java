package com.example.leisureapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.leisureapp.LeisureSingleton;
import com.example.leisureapp.activities.Popup;
import com.example.leisureapp.adapters.CardStackAdapter;
import com.example.leisureapp.models.ItemModel;
import com.example.leisureapp.adapters.FavCardAdapter;
import com.example.leisureapp.R;
import com.example.leisureapp.database.DatabaseManager;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import kotlin.jvm.Synchronized;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoritesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoritesFragment extends Fragment {

    ListView favs;
    String URL = "https://www.boredapi.com/api/activity?key=";
    public static ArrayList<ItemModel> arrayList = new ArrayList<ItemModel>();


    public FavoritesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FavoritesFragment.
     */
    public static FavoritesFragment newInstance(String param1, String param2) {
        FavoritesFragment fragment = new FavoritesFragment();
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
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        favs = (ListView) view.findViewById(R.id.favs);
        addFavoritesToList(arrayList);

        FavCardAdapter adapter = new FavCardAdapter(getActivity(), arrayList);
        favs.setAdapter(adapter);

        favs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Popup.favCard = arrayList.get(i);
                Popup.index = i;
                Popup.lv = favs;
                startActivity(new Intent(FavoritesFragment.this.getContext(), Popup.class));
            }
        });
    }


    @Override
    public void onDestroy() {
        arrayList.clear();
        super.onDestroy();
    }

    public void addFavoritesToList(ArrayList favList) {
        DatabaseManager db = new DatabaseManager(getActivity());
        ItemModel[] favourites = db.getFavorites();

        for(int i=0; i <= favourites.length - 1; i++) {
            favList.add(favourites[i]);
        }
    }
}