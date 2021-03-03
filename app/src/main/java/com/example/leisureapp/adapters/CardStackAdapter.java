package com.example.leisureapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.leisureapp.models.ItemModel;
import com.example.leisureapp.R;

import java.util.List;

public class CardStackAdapter extends RecyclerView.Adapter<CardStackAdapter.ViewHolder> {
    private List<ItemModel> items;

    public CardStackAdapter(List<ItemModel> items) {
        this.items = items;
    }

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
        return items.size();
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
        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.itemName);
        }

        void setData(ItemModel data) {
            name.setText(data.getName());
        }
    }
}
