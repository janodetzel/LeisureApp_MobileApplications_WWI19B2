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

        TextView noFavsText = (TextView) view.findViewById(R.id.noFavsText);
        favs = (ListView) view.findViewById(R.id.favs);
        addFavoritesToList(arrayList);
        if(arrayList.isEmpty()) {
            noFavsText.setVisibility(View.VISIBLE);
        } else {
            noFavsText.setVisibility(View.INVISIBLE);
            FavCardAdapter adapter = new FavCardAdapter(getActivity(), arrayList);
            favs.setAdapter(adapter);
            favs.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                   if(arrayList.isEmpty()) {
                       noFavsText.setVisibility(View.VISIBLE);
                   }
                }
            });

            favs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    FavCardDialogFragment dialog = new FavCardDialogFragment(arrayList.get(i), i, favs);
                    dialog.show(getFragmentManager(), "favCardDialog");
                }
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

        for(int i=0; i <= favourites.length - 1; i++) {
            favList.add(favourites[i]);
        }
    }
}