package com.example.leisureapp.adapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.leisureapp.models.ItemModel;
import com.example.leisureapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FavCardAdapter extends BaseAdapter {
    private Activity context;
    private ArrayList<ItemModel> items;

    static class ViewHolder {
        protected TextView activity;
        protected ImageView image;

        protected ArrayList<ImageView> personImages = new ArrayList<>();
        protected ArrayList<ImageView> moneyImages = new ArrayList<>();
    }

    public FavCardAdapter(Activity context, ArrayList<ItemModel> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public ItemModel getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View rowView, ViewGroup parent) {
        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.fav_card_layout, null);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.activity = (TextView) rowView.findViewById(R.id.favText);
            viewHolder.image = (ImageView) rowView.findViewById(R.id.favPic);

            viewHolder.moneyImages.add(rowView.findViewById(R.id.favCostIcon1));
            viewHolder.moneyImages.add(rowView.findViewById(R.id.favCostIcon2));
            viewHolder.moneyImages.add(rowView.findViewById(R.id.favCostIcon3));
            viewHolder.moneyImages.add(rowView.findViewById(R.id.favCostIcon4));

            viewHolder.personImages.add(rowView.findViewById(R.id.favPersonsIcon1));
            viewHolder.personImages.add(rowView.findViewById(R.id.favPersonsIcon2));
            viewHolder.personImages.add(rowView.findViewById(R.id.favPersonsIcon3));
            viewHolder.personImages.add(rowView.findViewById(R.id.favPersonsIcon4));

            rowView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) rowView.getTag();
        ItemModel fav = items.get(position);
        holder.activity.setText("" + fav.getActivity());

        int blue = ContextCompat.getColor(rowView.getContext(), R.color.blue);

        int persons = fav.getParticipants();
        if (persons >= 1) {
            holder.personImages.get(0).setColorFilter(blue);
        }
        if (persons >= 2) {
            holder.personImages.get(1).setColorFilter(blue);
        }
        if (persons >= 3) {
            holder.personImages.get(2).setColorFilter(blue);
        }
        if (persons >= 4) {
            holder.personImages.get(3).setColorFilter(blue);
        }

        double price = fav.getPrice();
        if (price >= 0.1) {
            holder.moneyImages.get(0).setColorFilter(blue);
        }
        if (price >= 0.3) {
            holder.moneyImages.get(1).setColorFilter(blue);
        }
        if (price >= 0.45) {
            holder.moneyImages.get(2).setColorFilter(blue);
        }
        if (price >= 0.6) {
            holder.moneyImages.get(3).setColorFilter(blue);
        }

        Picasso.get().load(fav.getImgURL())
                .placeholder(R.drawable.leisure_logo_foreground)
                .into(holder.image);


        return rowView;
    }

}
