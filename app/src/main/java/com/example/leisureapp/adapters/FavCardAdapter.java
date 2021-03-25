package com.example.leisureapp.adapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.leisureapp.models.ItemModel;
import com.example.leisureapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FavCardAdapter extends BaseAdapter {
    private Activity context;
    private ArrayList<ItemModel> items;

    static class ViewHolder{
        public TextView activity;
        public ImageView image;
        public ImageView iconCosts1;
        public ImageView iconCosts2;
        public ImageView iconCosts3;
        public ImageView iconCosts4;
        public ImageView iconPersons1;
        public ImageView iconPersons2;
        public ImageView iconPersons3;
        public ImageView iconPersons4;
        public TextView costs;
        public TextView persons;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.fav_card_layout, null);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.activity = (TextView) rowView.findViewById(R.id.favText);
            viewHolder.image = (ImageView) rowView.findViewById(R.id.favPic);
            viewHolder.iconCosts1 = (ImageView) rowView.findViewById(R.id.favCostIcon1);
            viewHolder.iconCosts2 = (ImageView) rowView.findViewById(R.id.favCostIcon2);
            viewHolder.iconCosts3 = (ImageView) rowView.findViewById(R.id.favCostIcon3);
            viewHolder.iconCosts4 = (ImageView) rowView.findViewById(R.id.favCostIcon4);
            viewHolder.iconPersons1 = (ImageView) rowView.findViewById(R.id.favPersonsIcon1);
            viewHolder.iconPersons2 = (ImageView) rowView.findViewById(R.id.favPersonsIcon2);
            viewHolder.iconPersons3 = (ImageView) rowView.findViewById(R.id.favPersonsIcon3);
            viewHolder.iconPersons4 = (ImageView) rowView.findViewById(R.id.favPersonsIcon4);
            rowView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) rowView.getTag();
        ItemModel fav = items.get(position);
        holder.activity.setText(""+fav.getActivity());

        Picasso.get().load(fav.getImgURL())
                .placeholder(R.drawable.leisure_logo_foreground)
                .into(holder.image);

        double price = fav.getPrice();
        if (price > 0.0) {
            holder.iconCosts1.setColorFilter(rowView.getResources().getColor(R.color.blue));
        }
        if (price >= 0.3) {
            holder.iconCosts2.setColorFilter(rowView.getResources().getColor(R.color.blue));
        }
        if (price >= 0.45) {
            holder.iconCosts3.setColorFilter(rowView.getResources().getColor(R.color.blue));
        }
        if (price >= 0.6) {
            holder.iconCosts4.setColorFilter(rowView.getResources().getColor(R.color.blue));
        }


        int persons = fav.getParticipants();
        if(persons >= 1) {
            holder.iconPersons1.setColorFilter(rowView.getResources().getColor(R.color.blue));
        }
        if(persons >= 2) {
            holder.iconPersons2.setColorFilter(rowView.getResources().getColor(R.color.blue));
        }
        if(persons >= 3) {
            holder.iconPersons3.setColorFilter(rowView.getResources().getColor(R.color.blue));
        }
        if(persons >= 4) {
            holder.iconPersons4.setColorFilter(rowView.getResources().getColor(R.color.blue));
        }




        return rowView;
    }

}
