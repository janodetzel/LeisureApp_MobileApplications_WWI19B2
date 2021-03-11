package com.example.leisureapp.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.leisureapp.models.FavCard;
import com.example.leisureapp.R;

import java.util.ArrayList;

public class FavCardAdapter extends BaseAdapter {
    private Activity context;
    private ArrayList<FavCard> items;

    static class ViewHolder{
        public TextView activity;
        public ImageView image;
        public TextView iconCosts;
        public ImageView iconPersons;
        public TextView costs;
        public TextView persons;
    }

    public FavCardAdapter(Activity context, ArrayList<FavCard> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public FavCard getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.fav_card_layout, null);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.activity = (TextView) rowView.findViewById(R.id.favText);
            viewHolder.image = (ImageView) rowView.findViewById(R.id.favPic);
            viewHolder.iconCosts = (TextView) rowView.findViewById(R.id.favCostIcon);
            viewHolder.costs = (TextView) rowView.findViewById(R.id.favCostText);
            viewHolder.iconPersons = (ImageView) rowView.findViewById(R.id.favPersonsIcon);
            viewHolder.persons = (TextView) rowView.findViewById(R.id.favPersonsText);
            rowView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) rowView.getTag();
        FavCard fav = items.get(position);
        holder.activity.setText(fav.activity);
        holder.persons.setText(fav.persons);
        holder.costs.setText(fav.costs);
        //TODO: Change to true url! from FavCard
        holder.image.setImageResource(R.drawable.city_photo);


        return rowView;
    }

}
