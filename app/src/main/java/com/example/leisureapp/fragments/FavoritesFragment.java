package com.example.leisureapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.leisureapp.activities.Popup;
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

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavoritesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavoritesFragment newInstance(String param1, String param2) {
        FavoritesFragment fragment = new FavoritesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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

        ArrayList<ItemModel> arrayList = new ArrayList<ItemModel>();
        addFavoritesToList(arrayList);
        FavCardAdapter adapter = new FavCardAdapter(this.getActivity(), arrayList);
        favs.setAdapter(adapter);

        favs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(FavoritesFragment.this.getContext(), Popup.class));
            }
        });
    }

    public void addFavoritesToList(ArrayList arrayList) {
        DatabaseManager db = new DatabaseManager(getActivity());
        String[] keys = db.getFavorites();

        for(int i=0; i <= keys.length - 1; i++) {
            //TODO: Change to real data
            ItemModel card = new ItemModel("Activity Text", "type", 2, 1.0, "link", "key", 0.8, "url", super.getContext());
            arrayList.add(card);
        }
    }
}