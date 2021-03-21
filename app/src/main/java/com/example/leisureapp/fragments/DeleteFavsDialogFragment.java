package com.example.leisureapp.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import com.example.leisureapp.database.DatabaseManager;

public class DeleteFavsDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Wait!")
                .setMessage("Do you really want to delete your favorite activities?")
                .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        FavoritesFragment.arrayList.clear();
                        DatabaseManager db = new DatabaseManager(getContext());
                        db.clearFavorites();
                        DeleteFavsDialogFragment.this.getDialog().cancel();
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DeleteFavsDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

}
