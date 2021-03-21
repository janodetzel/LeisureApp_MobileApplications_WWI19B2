package com.example.leisureapp.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.example.leisureapp.R;
import com.example.leisureapp.database.DatabaseManager;
import com.example.leisureapp.models.ItemModel;

public class FavCardDialogFragment extends DialogFragment {

    public ItemModel favCard;
    public int index;
    public ListView lv;

    public FavCardDialogFragment(ItemModel favCard, int index, ListView lv) {
        this.favCard = favCard;
        this.index = index;
        this.lv = lv;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fav_card_dialog, null);
        Button btnClose = view.findViewById(R.id.closePopup);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FavCardDialogFragment.this.getDialog().cancel();
            }
        });
        Button btnRemoveFa = view.findViewById(R.id.removeFav);
        btnRemoveFa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FavoritesFragment.arrayList.remove(index);
                lv.invalidateViews();
                FavCardDialogFragment.this.getDialog().cancel();
                DatabaseManager db = new DatabaseManager(getContext());
                db.removeFavorite(favCard.getKey());
            }
        });

        ImageView bgPic = view.findViewById(R.id.cardImage);
        //TODO: Change to real URL
        bgPic.setImageResource(R.drawable.city_photo);
        //bgPic.setImageURI(Uri.parse(favCard.getImgURL()));

        TextView mainText = view.findViewById(R.id.popupMainText);
        mainText.setText(favCard.getActivity());

        TextView typeText = view.findViewById(R.id.popupTypeText);
        typeText.setText(favCard.getType());
        // 0..1
        double price = favCard.getPrice();
        if (price > 0.0) {
            TextView popupCost1 = view.findViewById(R.id.popupCost1);
            popupCost1.setTextColor(getResources().getColor(R.color.blue));
        }
        if (price >= 0.3) {
            TextView popupCost2 = view.findViewById(R.id.popupCost2);
            popupCost2.setTextColor(getResources().getColor(R.color.blue));
        }
        if (price >= 0.45) {
            TextView popupCost3 = view.findViewById(R.id.popupCost3);
            popupCost3.setTextColor(getResources().getColor(R.color.blue));
        }
        if (price >= 0.6) {
            TextView popupCost4 = view.findViewById(R.id.popupCost4);
            popupCost4.setTextColor(getResources().getColor(R.color.blue));
        }


        int persons = favCard.getParticipants();
        if(persons >= 1) {
            ImageView popupPersons1 = view.findViewById(R.id.popupPersons1);
            popupPersons1.setColorFilter(getResources().getColor(R.color.blue));
        }
        if(persons >= 2) {
            ImageView popupPersons2 = view.findViewById(R.id.popupPersons2);
            popupPersons2.setColorFilter(getResources().getColor(R.color.blue));
        }
        if(persons >= 3) {
            ImageView popupPersons3 = view.findViewById(R.id.popupPersons3);
            popupPersons3.setColorFilter(getResources().getColor(R.color.blue));
        }
        if(persons >= 4) {
            ImageView popupPersons4 = view.findViewById(R.id.popupPersons4);
            popupPersons4.setColorFilter(getResources().getColor(R.color.blue));
        }
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        DisplayMetrics dm = Resources.getSystem().getDisplayMetrics();
        dialog.getWindow().setLayout((int)(dm.widthPixels*0.8), (int)(dm.heightPixels*0.7));
        return dialog;
    }




}
