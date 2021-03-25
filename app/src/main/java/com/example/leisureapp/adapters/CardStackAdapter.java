package com.example.leisureapp.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.leisureapp.models.ItemModel;
import com.example.leisureapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CardStackAdapter extends RecyclerView.Adapter<CardStackAdapter.ViewHolder> {
    private List<ItemModel> items;

    public CardStackAdapter(List<ItemModel> items) {
        this.items = items;
    }
    public CardStackAdapter(){};

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for this view
        LayoutInflater inflater = LayoutInflater.from((parent.getContext()));
        View view = inflater.inflate(R.layout.item_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(items.get(position));
    }

    @Override
    public int getItemCount() {
        // Get size of item stack
        if (items != null) {
            return items.size();
        }
        return 0;
    }

    // Get all items of stack
    public List<ItemModel> getItems() {
        return items;
    }

    // Set items for new stack
    public void setItems(List<ItemModel> newItems) {
        items = newItems;
    }

    public void addItem(int index, ItemModel newItem) {
        items.add(index, newItem);
        this.notifyItemInserted(index);
    }

    public void addItems(int index, List<ItemModel> newItems) {
        items.addAll(index, newItems);
        this.notifyItemRangeInserted(index, newItems.size());
    }

    // Class for setting data in card
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        TextView type;
        ImageView imgP1;
        ImageView imgP2;
        ImageView imgP3;
        ImageView imgP4;
        ImageView imgM1;
        ImageView imgM2;
        ImageView imgM3;
        ImageView imgM4;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.type);
            text = itemView.findViewById(R.id.activityText);
            imgP1 = itemView.findViewById(R.id.imgPersons1);
            imgP2 = itemView.findViewById(R.id.imgPersons2);
            imgP3 = itemView.findViewById(R.id.imgPersons3);
            imgP4 = itemView.findViewById(R.id.imgPersons4);
            imgM1 = itemView.findViewById(R.id.imgMoney1);
            imgM2 = itemView.findViewById(R.id.imgMoney2);
            imgM3 = itemView.findViewById(R.id.imgMoney3);
            imgM4 = itemView.findViewById(R.id.imgMoney4);
        }

        void setData(ItemModel data) {
            text.setText(data.getActivity());
            type.setText(data.getType());
            Log.i("Activity", data.getActivity() + " | Price: " + data.getPrice() + " | Persons: " + data.getParticipants());

            // Reset Colors
            imgP1.setColorFilter(itemView.getResources().getColor(R.color.white));
            imgP2.setColorFilter(itemView.getResources().getColor(R.color.white));
            imgP3.setColorFilter(itemView.getResources().getColor(R.color.white));
            imgP4.setColorFilter(itemView.getResources().getColor(R.color.white));
            imgM1.setColorFilter(itemView.getResources().getColor(R.color.white));
            imgM2.setColorFilter(itemView.getResources().getColor(R.color.white));
            imgM3.setColorFilter(itemView.getResources().getColor(R.color.white));
            imgM4.setColorFilter(itemView.getResources().getColor(R.color.white));


            // Color Symbols for participants and price
            int persons = data.getParticipants();
            if (persons >= 1) {
                imgP1.setColorFilter(itemView.getResources().getColor(R.color.blue));
            }
            if (persons >= 2) {
                imgP2.setColorFilter(itemView.getResources().getColor(R.color.blue));
            }
            if (persons >= 3) {
                imgP3.setColorFilter(itemView.getResources().getColor(R.color.blue));
            }
            if (persons >= 4) {
                imgP4.setColorFilter(itemView.getResources().getColor(R.color.blue));
            }

            double price = data.getPrice();
            if (price >= 0.1) {
                imgM1.setColorFilter(itemView.getResources().getColor(R.color.blue));
            }
            if (price >= 0.3) {
                imgM2.setColorFilter(itemView.getResources().getColor(R.color.blue));
            }
            if (price >= 0.45) {
                imgM3.setColorFilter(itemView.getResources().getColor(R.color.blue));
            }
            if (price >= 0.6) {
                imgM4.setColorFilter(itemView.getResources().getColor(R.color.blue));
            }

            ImageView imageView = (ImageView) itemView.findViewById(R.id.cardImage);

            Picasso.get().load(data.getImgURL())
                    .placeholder(R.drawable.leisure_logo_foreground)
                    .error(R.drawable.leisure_logo_foreground)
                    .into(imageView);
        }
    }
}
