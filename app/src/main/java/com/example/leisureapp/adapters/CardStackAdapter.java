package com.example.leisureapp.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.leisureapp.models.ItemModel;
import com.example.leisureapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CardStackAdapter extends RecyclerView.Adapter<CardStackAdapter.ViewHolder> {
    private List<ItemModel> items;

    public CardStackAdapter(List<ItemModel> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
        if (items != null) {
            return items.size();
        }
        return 0;
    }

    public List<ItemModel> getItems() {
        return items;
    }

    public void addItem(int index, ItemModel newItem) {
        items.add(index, newItem);
        this.notifyItemInserted(index);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        TextView type;
        ArrayList<ImageView> personImages = new ArrayList<>();
        ArrayList<ImageView> moneyImages = new ArrayList<>();

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.type);
            text = itemView.findViewById(R.id.activityText);

            personImages.add(itemView.findViewById(R.id.imgPersons1));
            personImages.add(itemView.findViewById(R.id.imgPersons2));
            personImages.add(itemView.findViewById(R.id.imgPersons3));
            personImages.add(itemView.findViewById(R.id.imgPersons4));

            moneyImages.add(itemView.findViewById(R.id.imgMoney1));
            moneyImages.add(itemView.findViewById(R.id.imgMoney2));
            moneyImages.add(itemView.findViewById(R.id.imgMoney3));
            moneyImages.add(itemView.findViewById(R.id.imgMoney4));
        }

        void setData(ItemModel data) {
            text.setText(data.getActivity());
            type.setText(data.getType());

            int white = ContextCompat.getColor(itemView.getContext(), R.color.white);
            int blue = ContextCompat.getColor(itemView.getContext(), R.color.blue);
            
            personImages.forEach( personImage -> personImage.setColorFilter(white));

            moneyImages.forEach(moneyImage -> moneyImage.setColorFilter(white));

            int persons = data.getParticipants();
            if (persons >= 1) {
                personImages.get(0).setColorFilter(blue);
            }
            if (persons >= 2) {
                personImages.get(1).setColorFilter(blue);
            }
            if (persons >= 3) {
                personImages.get(2).setColorFilter(blue);
            }
            if (persons >= 4) {
                personImages.get(3).setColorFilter(blue);
            }

            double price = data.getPrice();
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

            ImageView imageView = (ImageView) itemView.findViewById(R.id.cardImage);
            Picasso.get().load(data.getImgURL())
                    .placeholder(R.drawable.leisure_logo_foreground)
                    .error(R.drawable.leisure_logo_foreground)
                    .into(imageView);
        }
    }
}
