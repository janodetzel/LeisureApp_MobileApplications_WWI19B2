package com.example.leisureapp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;

import com.example.leisureapp.R;
import com.example.leisureapp.database.DatabaseManager;
import com.example.leisureapp.utils.SharedPreferencesHelper;

import java.util.ArrayList;
import java.util.Objects;

public class SettingsFragment extends Fragment {
    ArrayList<ImageView> personImages = new ArrayList<>();
    ArrayList<ImageView> moneyImages = new ArrayList<>();

    public SettingsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        SharedPreferences sharedPref = Objects.requireNonNull(getActivity()).getPreferences(Context.MODE_PRIVATE);

        ImageView costsDel = (ImageView) view.findViewById(R.id.settingsCostsDel);
        ImageView personsDel = (ImageView) view.findViewById(R.id.settingsPersonsDel);
        getImages(view);

        SeekBar seekCosts = (SeekBar) view.findViewById(R.id.seekBarCosts);

        if (SharedPreferencesHelper.getDouble(sharedPref, R.id.seekBarCosts + "filterCostsMin", -1.0) == -1.0) {
            resetFilterCosts(seekCosts, sharedPref);
        } else {
            int progressCosts = sharedPref.getInt((String.valueOf(R.id.seekBarCosts)), 0);
            seekCosts.setProgress(progressCosts);
            setSeekCostsProgress(view, progressCosts);
        }

        seekCosts.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                setSeekCostsProgress(view, progress);
                DatabaseManager db = new DatabaseManager(getActivity());
                db.clearTmp();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        SeekBar seekPersons = (SeekBar) view.findViewById(R.id.seekBarPersons);

        if (sharedPref.getInt(R.id.seekBarPersons + "filterPersons", -1) == -1) {
            resetFilterPersons(seekPersons, sharedPref);
        } else {
            int progressPersons = sharedPref.getInt((String.valueOf(R.id.seekBarPersons)), 0);
            seekPersons.setProgress(progressPersons);
            setSeekPersonsProgress(view, progressPersons);
        }

        seekPersons.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                setSeekPersonsProgress(view, progress);
                DatabaseManager db = new DatabaseManager(getActivity());
                db.clearTmp();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        Button btnClear = view.findViewById(R.id.btnClear);
        btnClear.setOnClickListener(view1 -> {
            DeleteFavsDialogFragment dialog = new DeleteFavsDialogFragment();
            dialog.show(getFragmentManager(), "deleteFavsDialog");
        });

        Spinner dropDown = (Spinner) view.findViewById(R.id.settingsTypeDropDown);

        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(Objects.requireNonNull(this.getContext()), R.array.settingsType,
                        R.layout.spinner_item);
        staticAdapter.setDropDownViewResource(R.layout.spinner_item);

        dropDown.setAdapter(staticAdapter);

        int selectedTypePosition = sharedPref.getInt(R.id.settingsTypeDropDown + "filterTypePosition", 0);
        dropDown.setSelection(selectedTypePosition);
        dropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setFilterType(dropDown, position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        costsDel.setOnClickListener(v -> {
            setSeekCostsProgress(view, 0);
            seekCosts.setProgress(0);
            resetFilterCosts(seekCosts, sharedPref);
        });

        personsDel.setOnClickListener(v -> {
            setSeekPersonsProgress(view, 0);
            seekPersons.setProgress(0);
            resetFilterPersons(seekPersons, sharedPref);
        });
    }

    private void setSeekCostsProgress(View view, int progress) {
        SharedPreferences sharedPref = Objects.requireNonNull(getActivity()).getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefEditor = sharedPref.edit();

        int white = ContextCompat.getColor(view.getContext(), R.color.white);
        int blue = ContextCompat.getColor(view.getContext(), R.color.blue);

        SeekBar seekCosts = (SeekBar) view.findViewById(R.id.seekBarCosts);
        seekCosts.getThumb().setColorFilter(blue, PorterDuff.Mode.SRC_IN);

        moneyImages.forEach(personImage -> personImage.setColorFilter(white));
        if (progress == 0) {
            moneyImages.forEach(personImage -> personImage.setColorFilter(white));
            SharedPreferencesHelper.putDouble(sharedPrefEditor, R.id.seekBarCosts + "filterCostsMin", 0.0);
            SharedPreferencesHelper.putDouble(sharedPrefEditor, R.id.seekBarCosts + "filterCostsMax", 0.0);
        }

        if (progress > 0) {
            moneyImages.get(0).setColorFilter(blue);
            SharedPreferencesHelper.putDouble(sharedPrefEditor, R.id.seekBarCosts + "filterCostsMin", 0.1);
            SharedPreferencesHelper.putDouble(sharedPrefEditor, R.id.seekBarCosts + "filterCostsMax", 0.29);
        }

        if (progress > 33) {
            moneyImages.get(0).setColorFilter(blue);
            moneyImages.get(1).setColorFilter(blue);
            SharedPreferencesHelper.putDouble(sharedPrefEditor, R.id.seekBarCosts + "filterCostsMin", 0.3);
            SharedPreferencesHelper.putDouble(sharedPrefEditor, R.id.seekBarCosts + "filterCostsMax", 0.44);
        }

        if (progress > 66) {
            moneyImages.get(0).setColorFilter(blue);
            moneyImages.get(1).setColorFilter(blue);
            moneyImages.get(2).setColorFilter(blue);
            SharedPreferencesHelper.putDouble(sharedPrefEditor, R.id.seekBarCosts + "filterCostsMin", 0.45);
            SharedPreferencesHelper.putDouble(sharedPrefEditor, R.id.seekBarCosts + "filterCostsMax", 0.59);
        }

        if (progress == 100) {
            moneyImages.forEach(personImage -> personImage.setColorFilter(blue));
            SharedPreferencesHelper.putDouble(sharedPrefEditor, R.id.seekBarCosts + "filterCostsMin", 0.6);
            SharedPreferencesHelper.putDouble(sharedPrefEditor, R.id.seekBarCosts + "filterCostsMax", 1.0);
        }

        sharedPrefEditor.putInt(String.valueOf(R.id.seekBarCosts), progress);
        sharedPrefEditor.apply();
    }

    private void setSeekPersonsProgress(View view, int progress) {
        SharedPreferences sharedPref = Objects.requireNonNull(getActivity()).getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefEditor = sharedPref.edit();

        int white = ContextCompat.getColor(view.getContext(), R.color.white);
        int blue = ContextCompat.getColor(view.getContext(), R.color.blue);

        SeekBar seekPersons = (SeekBar) view.findViewById(R.id.seekBarPersons);
        seekPersons.getThumb().setColorFilter(blue, PorterDuff.Mode.SRC_IN);

        sharedPrefEditor.putBoolean("selectAllPersons", false);

        personImages.forEach(personImage -> personImage.setColorFilter(white));
        switch (progress) {
            case 0:
                personImages.get(0).setColorFilter(blue);
                sharedPrefEditor.putInt(R.id.seekBarPersons + "filterPersons", 1);
                break;
            case 1:
                personImages.get(0).setColorFilter(blue);
                personImages.get(1).setColorFilter(blue);
                sharedPrefEditor.putInt(R.id.seekBarPersons + "filterPersons", 2);

                break;
            case 2:
                personImages.get(0).setColorFilter(blue);
                personImages.get(1).setColorFilter(blue);
                personImages.get(2).setColorFilter(blue);
                sharedPrefEditor.putInt(R.id.seekBarPersons + "filterPersons", 3);
                break;
            case 3:
                personImages.forEach(personImage -> personImage.setColorFilter(blue));
                sharedPrefEditor.putInt(R.id.seekBarPersons + "filterPersons", 4);
        }

        sharedPrefEditor.putInt(String.valueOf(R.id.seekBarPersons), progress);
        sharedPrefEditor.apply();
    }

    private void setFilterType(Spinner dropdown, int position) {
        SharedPreferences sharedPref = Objects.requireNonNull(getActivity()).getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefEditor = sharedPref.edit();

        sharedPrefEditor.putInt(R.id.settingsTypeDropDown + "filterTypePosition", position);

        if (position == 0) {
            sharedPrefEditor.putString(R.id.settingsTypeDropDown + "filterTypeValue", "");
        } else {
            String selectedTypeValue = (String) dropdown.getItemAtPosition(position);
            sharedPrefEditor.putString(R.id.settingsTypeDropDown + "filterTypeValue", selectedTypeValue);
        }

        if (position != sharedPref.getInt(R.id.settingsTypeDropDown + "filterTypePosition", 0)) {
            DatabaseManager db = new DatabaseManager(getActivity());
            db.clearTmp();
        }

        sharedPrefEditor.apply();
    }

    private void getImages(View view){
        personImages.add(view.findViewById(R.id.settingsPersons1));
        personImages.add(view.findViewById(R.id.settingsPersons2));
        personImages.add(view.findViewById(R.id.settingsPersons3));
        personImages.add(view.findViewById(R.id.settingsPersons4));

        moneyImages.add(view.findViewById(R.id.settingsCosts1));
        moneyImages.add(view.findViewById(R.id.settingsCosts2));
        moneyImages.add(view.findViewById(R.id.settingsCosts3));
        moneyImages.add(view.findViewById(R.id.settingsCosts4));
    }

    private void resetFilterCosts(SeekBar seekBar, SharedPreferences sharedPref) {
        int grey = ContextCompat.getColor(getContext(), R.color.light_grey);
        moneyImages.forEach(moneyImage -> moneyImage.setColorFilter(grey));
        seekBar.getThumb().setColorFilter(grey, PorterDuff.Mode.SRC_IN);

        SharedPreferences.Editor sharedPrefEditor = sharedPref.edit();
        SharedPreferencesHelper.putDouble(sharedPrefEditor, R.id.seekBarCosts + "filterCostsMin", -1.0);
        SharedPreferencesHelper.putDouble(sharedPrefEditor, R.id.seekBarCosts + "filterCostsMax", -1.0);
        sharedPrefEditor.apply();
    }

    private void resetFilterPersons(SeekBar seekBar, SharedPreferences sharedPref) {
        int grey = ContextCompat.getColor(getContext(), R.color.light_grey);
        personImages.forEach(personImage -> personImage.setColorFilter(grey));
        seekBar.getThumb().setColorFilter(grey, PorterDuff.Mode.SRC_IN);

        SharedPreferences.Editor sharedPrefEditor = sharedPref.edit();
        sharedPrefEditor.putInt(R.id.seekBarPersons + "filterPersons", -1);
        sharedPrefEditor.apply();
    }
}