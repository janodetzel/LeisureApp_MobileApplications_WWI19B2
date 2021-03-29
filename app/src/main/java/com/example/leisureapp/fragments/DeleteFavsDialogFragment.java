package com.example.leisureapp.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import com.example.leisureapp.R;
import com.example.leisureapp.database.DatabaseManager;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class DeleteFavsDialogFragment extends DialogFragment {
    @Override
    @NotNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        Resources r = getResources();

        String title = r.getString(R.string.delete_favs_dialog_title),
                message = r.getString(R.string.delete_favs_dialog_message),
                positiveButtonText = r.getString(R.string.delete_favs_dialog_posbtn_text),
                negativeButtonText = r.getString(R.string.delete_favs_dialog_negbtn_text);

        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveButtonText, (dialog, id) -> {
                    FavoritesFragment.arrayList.clear();
                    DatabaseManager db = new DatabaseManager(getContext());
                    db.clearFavorites();
                    Objects.requireNonNull(DeleteFavsDialogFragment.this.getDialog()).cancel();
                })
                .setNegativeButton(negativeButtonText, (dialog, id) -> Objects.requireNonNull(DeleteFavsDialogFragment.this.getDialog()).cancel());

        return builder.create();
    }

}
