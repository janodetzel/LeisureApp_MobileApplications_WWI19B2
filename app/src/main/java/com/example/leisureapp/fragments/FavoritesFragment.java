package com.example.leisureapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.leisureapp.models.ItemModel;
import com.example.leisureapp.adapters.FavCardAdapter;
import com.example.leisureapp.R;
import com.example.leisureapp.database.DatabaseManager;

import java.util.ArrayList;
import java.util.Collections;

public class FavoritesFragment extends Fragment {
    public static ArrayList<ItemModel> arrayList = new ArrayList<>();
    private ListView favs;

    public FavoritesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView noFavsText = (TextView) view.findViewById(R.id.noFavsText);
        favs = (ListView) view.findViewById(R.id.favs);
        addFavoritesToList(arrayList);

        if(arrayList.isEmpty()) {
            noFavsText.setVisibility(View.VISIBLE);
        } else {
            noFavsText.setVisibility(View.INVISIBLE);
            FavCardAdapter adapter = new FavCardAdapter(getActivity(), arrayList);
            favs.setAdapter(adapter);
            favs.addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
               if(arrayList.isEmpty()) {
                   noFavsText.setVisibility(View.VISIBLE);
               }
            });

            favs.setOnItemClickListener((adapterView, view1, position, id) -> {
                FavCardDialogFragment dialog = new FavCardDialogFragment(arrayList.get(position), position, favs);
                assert getFragmentManager() != null;
                dialog.show(getFragmentManager(), "favCardDialog");
            });
        }
    }

    @Override
    public void onDestroy() {
        arrayList.clear();
        super.onDestroy();
    }

    public void addFavoritesToList(ArrayList favList) {
        DatabaseManager db = new DatabaseManager(getActivity());
        ItemModel[] favourites = db.getFavorites();
        Collections.addAll(favList, favourites);
    }
}