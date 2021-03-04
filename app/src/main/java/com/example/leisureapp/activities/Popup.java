package com.example.leisureapp.activities;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.leisureapp.R;
import com.example.leisureapp.database.DatabaseManager;

public class Popup extends AppCompatActivity {

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
                Popup.super.onBackPressed();
                DatabaseManager db = new DatabaseManager(getBaseContext());
                //TODO: real favCard Object
                db.removeFavorite(null);
            }
        });

        ImageView bgPic = (ImageView) findViewById(R.id.popupPic);
        //TODO: Change to real URL
        bgPic.setImageResource(R.drawable.city_photo);

        TextView mainText = (TextView) findViewById(R.id.popupMainText);
        mainText.setMaxWidth((int) (popupWidth*0.75));
        //TODO: Change to real Text
        mainText.setText("Explore the wildlife of your city");

        TextView popupCost1 = (TextView) findViewById(R.id.popupCost1);
        TextView popupCost2 = (TextView) findViewById(R.id.popupCost2);
        TextView popupCost3 = (TextView) findViewById(R.id.popupCost3);
        TextView popupCost4 = (TextView) findViewById(R.id.popupCost4);

        ImageView popupPersons1 = (ImageView) findViewById(R.id.popupPersons1);
        ImageView popupPersons2 = (ImageView) findViewById(R.id.popupPersons2);
        ImageView popupPersons3 = (ImageView) findViewById(R.id.popupPersons3);
        ImageView popupPersons4 = (ImageView) findViewById(R.id.popupPersons4);

    }

    @Override
    public void onDestroy() {
        MainActivity.dimPopupBackground.setVisibility(View.INVISIBLE);
        super.onDestroy();
    }

}
