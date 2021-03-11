package com.example.leisureapp.activities;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.leisureapp.R;
import com.example.leisureapp.database.DatabaseManager;
import com.example.leisureapp.fragments.FavoritesFragment;
import com.example.leisureapp.models.ItemModel;

public class Popup extends AppCompatActivity {

    public static ItemModel favCard;
    public static int index;
    public static ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popup_layout);

        MainActivity.dimPopupBackground.setVisibility(View.VISIBLE);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int totalWidth = dm.widthPixels;
        int totalHeight = dm.heightPixels;
        int popupWidth = (int) (totalWidth*0.8);
        int popupHeight = (int) (totalHeight*0.7);

        getWindow().setLayout(popupWidth, popupHeight);

        Button btnClose = findViewById(R.id.closePopup);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Popup.super.onBackPressed();
            }
        });

        Button btnRemoveFa = findViewById(R.id.removeFav);
        btnRemoveFa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FavoritesFragment.arrayList.remove(index);
                lv.invalidateViews();
                Popup.super.onBackPressed();
                DatabaseManager db = new DatabaseManager(getBaseContext());
                db.removeFavorite(favCard.getKey());
            }
        });

        ImageView bgPic = (ImageView) findViewById(R.id.popupPic);
        //TODO: Change to real URL
        bgPic.setImageResource(R.drawable.city_photo);
        //bgPic.setImageURI(Uri.parse(favCard.getImgURL()));

        TextView mainText = (TextView) findViewById(R.id.popupMainText);
        mainText.setMaxWidth((int) (popupWidth*0.75));
        mainText.setText(favCard.getActivity());
        // 0..1
        double price = favCard.getPrice();
        if (price > 0.0) {
            TextView popupCost1 = (TextView) findViewById(R.id.popupCost1);
            popupCost1.setTextColor(getResources().getColor(R.color.blue));
        }
        if (price >= 0.25) {
            TextView popupCost2 = (TextView) findViewById(R.id.popupCost2);
            popupCost2.setTextColor(getResources().getColor(R.color.blue));
        }
        if (price >= 0.5) {
            TextView popupCost3 = (TextView) findViewById(R.id.popupCost3);
            popupCost3.setTextColor(getResources().getColor(R.color.blue));
        }
        if (price >= 0.75) {
            TextView popupCost4 = (TextView) findViewById(R.id.popupCost4);
            popupCost4.setTextColor(getResources().getColor(R.color.blue));
        }


        int persons = favCard.getParticipants();
        if(persons >= 1) {
            ImageView popupPersons1 = (ImageView) findViewById(R.id.popupPersons1);
            popupPersons1.setColorFilter(getResources().getColor(R.color.blue));
        }
        if(persons >= 2) {
            ImageView popupPersons2 = (ImageView) findViewById(R.id.popupPersons2);
            popupPersons2.setColorFilter(getResources().getColor(R.color.blue));
        }
        if(persons >= 3) {
            ImageView popupPersons3 = (ImageView) findViewById(R.id.popupPersons3);
            popupPersons3.setColorFilter(getResources().getColor(R.color.blue));
        }
        if(persons >= 4) {
            ImageView popupPersons4 = (ImageView) findViewById(R.id.popupPersons4);
            popupPersons4.setColorFilter(getResources().getColor(R.color.blue));
        }

    }

    @Override
    public void onDestroy() {
        MainActivity.dimPopupBackground.setVisibility(View.INVISIBLE);
        super.onDestroy();
    }

}
