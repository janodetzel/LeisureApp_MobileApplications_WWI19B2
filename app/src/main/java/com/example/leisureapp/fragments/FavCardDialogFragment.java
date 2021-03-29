package com.example.leisureapp.fragments;

import android.app.Dialog;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.example.leisureapp.R;
import com.example.leisureapp.database.DatabaseManager;
import com.example.leisureapp.models.ItemModel;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Objects;

public class FavCardDialogFragment extends DialogFragment {
    public ItemModel favCard;
    public int position;
    public ListView listView;

    ArrayList<ImageView> personImages = new ArrayList<>();
    ArrayList<ImageView> moneyImages = new ArrayList<>();

    public FavCardDialogFragment(ItemModel favCard, int position, ListView listView) {
        this.favCard = favCard;
        this.position = position;
        this.listView = listView;
    }

    @Override
    @NotNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(Objects.requireNonNull(getContext()));
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View itemView = inflater.inflate(R.layout.fav_card_dialog, null);
        Button btnClose = itemView.findViewById(R.id.closePopup);
        getImages(itemView);

        btnClose.setOnClickListener(view -> Objects.requireNonNull(FavCardDialogFragment.this.getDialog()).cancel());

        Button btnRemoveFa = itemView.findViewById(R.id.removeFav);
        btnRemoveFa.setOnClickListener(view -> {
            FavoritesFragment.arrayList.remove(position);
            listView.invalidateViews();
            Objects.requireNonNull(FavCardDialogFragment.this.getDialog()).cancel();
            DatabaseManager db = new DatabaseManager(getContext());
            db.removeFavorite(favCard.getKey());
        });

        TextView mainText = itemView.findViewById(R.id.popupMainText);
        mainText.setText(favCard.getActivity());

        TextView typeText = itemView.findViewById(R.id.popupTypeText);
        typeText.setText(favCard.getType());

        int blue = ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.blue);

        double price = favCard.getPrice();
        if (price >= 0.1) {
            moneyImages.get(0).setColorFilter(blue);
        }
        if (price >= 0.3) {
            moneyImages.get(1).setColorFilter(blue);
        }
        if (price >= 0.45) {
            moneyImages.get(2).setColorFilter(blue);
        }
        if (price >= 0.6) {
            moneyImages.get(3).setColorFilter(blue);
        }

        int persons = favCard.getParticipants();
        if(persons >= 1) {
            personImages.get(0).setColorFilter(blue);
        }
        if(persons >= 2) {
            personImages.get(1).setColorFilter(blue);
        }
        if(persons >= 3) {
            personImages.get(2).setColorFilter(blue);
        }
        if(persons >= 4) {
            personImages.get(3).setColorFilter(blue);
        }

        ImageView bgPic = itemView.findViewById(R.id.cardImage);
        Picasso.get().load(favCard.getImgURL())
                .placeholder(R.drawable.leisure_logo_foreground)
                .into(bgPic);

        dialog.setContentView(itemView);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        DisplayMetrics dm = Resources.getSystem().getDisplayMetrics();
        dialog.getWindow().setLayout((int)(dm.widthPixels*0.8), (int)(dm.heightPixels*0.7));
        return dialog;
    }

    private void getImages(View view){
        personImages.add(view.findViewById(R.id.popupPersons1));
        personImages.add(view.findViewById(R.id.popupPersons2));
        personImages.add(view.findViewById(R.id.popupPersons3));
        personImages.add(view.findViewById(R.id.popupPersons4));

        moneyImages.add(view.findViewById(R.id.popupCost1));
        moneyImages.add(view.findViewById(R.id.popupCost2));
        moneyImages.add(view.findViewById(R.id.popupCost3));
        moneyImages.add(view.findViewById(R.id.popupCost4));
    }



}
