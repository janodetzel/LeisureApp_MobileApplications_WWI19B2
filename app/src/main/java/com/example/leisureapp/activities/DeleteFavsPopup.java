package com.example.leisureapp.activities;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.leisureapp.R;
import com.example.leisureapp.database.DatabaseManager;
import com.example.leisureapp.fragments.FavoritesFragment;

public class DeleteFavsPopup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.delete_favs_popup);

        MainActivity.dimPopupBackground.setVisibility(View.VISIBLE);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int totalWidth = dm.widthPixels;
        int totalHeight = dm.heightPixels;
        int popupWidth = (int) (totalWidth*0.8);
        int popupHeight = (int) (totalHeight*0.25);

        getWindow().setLayout(popupWidth, popupHeight);

        Button delete = findViewById(R.id.deleteFavsBtn);
        Button close = findViewById(R.id.returnBtnPopup);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.dimPopupBackground.setVisibility(View.INVISIBLE);
                FavoritesFragment.arrayList.clear();
                DatabaseManager db = new DatabaseManager(getBaseContext());
                db.clearFavorites();
                DeleteFavsPopup.super.onBackPressed();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.dimPopupBackground.setVisibility(View.INVISIBLE);
                DeleteFavsPopup.super.onBackPressed();
            }
        });

    }
}
