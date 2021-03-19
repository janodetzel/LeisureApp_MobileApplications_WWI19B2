package com.example.leisureapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.example.leisureapp.activities.DeleteFavsPopup;
import com.example.leisureapp.utils.SharedPreferencesHelper;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {

    private static final String TAG = SettingsFragment.class.getSimpleName();

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);


        ImageView costsDel = (ImageView) view.findViewById(R.id.settingsCostsDel);
        ImageView personsDel = (ImageView) view.findViewById(R.id.settingsPersonsDel);

        // Progress SeekBar
        SeekBar seekCosts = (SeekBar) view.findViewById(R.id.seekBarCosts);


        int progressCosts = sharedPref.getInt((String.valueOf(R.id.seekBarCosts)), 0);
        seekCosts.setProgress(progressCosts);
        setSeekCostsProgress(view, progressCosts);

        seekCosts.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                setSeekCostsProgress(view, progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Auto-generated
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Auto-generated
            }
        });

        SeekBar seekPersons = (SeekBar) view.findViewById(R.id.seekBarPersons);

        int progressPersons = sharedPref.getInt((String.valueOf(R.id.seekBarPersons)), 0);
        seekPersons.setProgress(progressPersons);
        setSeekPersonsProgress(view, progressPersons);

        seekPersons.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                setSeekPersonsProgress(view, progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Auto-generated
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Auto-generated
            }
        });

        // Delete Database entries
        Button btnClear = view.findViewById(R.id.btnClear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingsFragment.this.getContext(), DeleteFavsPopup.class));
            }
        });

        Spinner dropDown = (Spinner) view.findViewById(R.id.settingsTypeDropDown);

        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(this.getContext(), R.array.settingsType,
                        android.R.layout.simple_spinner_item);
        staticAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        dropDown.setAdapter(staticAdapter);


        int selectedTypePosition = sharedPref.getInt(String.valueOf(R.id.settingsTypeDropDown) + "filterTypePosition", 0);

        Log.d("selectedTypePosition", String.valueOf(selectedTypePosition));
        dropDown.setSelection(selectedTypePosition);

        dropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    setFilterType(dropDown, position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        // On Click Listeners

        costsDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSeekCostsProgress(view, 0);
            }
        });

        personsDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSeekPersonsProgress(view, 0);
            }
        });
    }

    private void setSeekCostsProgress(View view, int progress) {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefEditor = sharedPref.edit();

        ImageView imgCost1 = (ImageView) view.findViewById(R.id.settingsCosts1);
        ImageView imgCost2 = (ImageView) view.findViewById(R.id.settingsCosts2);
        ImageView imgCost3 = (ImageView) view.findViewById(R.id.settingsCosts3);
        ImageView imgCost4 = (ImageView) view.findViewById(R.id.settingsCosts4);

        if (progress == 0) {
            imgCost1.setColorFilter(view.getResources().getColor(R.color.white));
            imgCost2.setColorFilter(view.getResources().getColor(R.color.white));
            imgCost3.setColorFilter(view.getResources().getColor(R.color.white));
            imgCost4.setColorFilter(view.getResources().getColor(R.color.white));

            SharedPreferencesHelper.putDouble(sharedPrefEditor, String.valueOf(R.id.seekBarCosts) + "filterCostsMin", 0.0);
            SharedPreferencesHelper.putDouble(sharedPrefEditor, String.valueOf(R.id.seekBarCosts) + "filterCostsMax", 1);

        }

        if (progress > 0) {
            imgCost1.setColorFilter(view.getResources().getColor(R.color.blue));
            imgCost2.setColorFilter(view.getResources().getColor(R.color.white));
            imgCost3.setColorFilter(view.getResources().getColor(R.color.white));
            imgCost4.setColorFilter(view.getResources().getColor(R.color.white));

            SharedPreferencesHelper.putDouble(sharedPrefEditor, String.valueOf(R.id.seekBarCosts) + "filterCostsMin", 0.1);
            SharedPreferencesHelper.putDouble(sharedPrefEditor, String.valueOf(R.id.seekBarCosts) + "filterCostsMax", 0.3);

        }

        if (progress > 33) {
            imgCost1.setColorFilter(view.getResources().getColor(R.color.blue));
            imgCost2.setColorFilter(view.getResources().getColor(R.color.blue));
            imgCost3.setColorFilter(view.getResources().getColor(R.color.white));
            imgCost4.setColorFilter(view.getResources().getColor(R.color.white));
            SharedPreferencesHelper.putDouble(sharedPrefEditor, String.valueOf(R.id.seekBarCosts) + "filterCostsMin", 0.3);
            SharedPreferencesHelper.putDouble(sharedPrefEditor, String.valueOf(R.id.seekBarCosts) + "filterCostsMax", 0.45);

        }

        if (progress > 66) {
            imgCost1.setColorFilter(view.getResources().getColor(R.color.blue));
            imgCost2.setColorFilter(view.getResources().getColor(R.color.blue));
            imgCost3.setColorFilter(view.getResources().getColor(R.color.blue));
            imgCost4.setColorFilter(view.getResources().getColor(R.color.white));
            SharedPreferencesHelper.putDouble(sharedPrefEditor, String.valueOf(R.id.seekBarCosts) + "filterCostsMin", 0.45);
            SharedPreferencesHelper.putDouble(sharedPrefEditor, String.valueOf(R.id.seekBarCosts) + "filterCostsMax", 0.6);
        }

        if (progress == 100) {
            imgCost1.setColorFilter(view.getResources().getColor(R.color.blue));
            imgCost2.setColorFilter(view.getResources().getColor(R.color.blue));
            imgCost3.setColorFilter(view.getResources().getColor(R.color.blue));
            imgCost4.setColorFilter(view.getResources().getColor(R.color.blue));
            SharedPreferencesHelper.putDouble(sharedPrefEditor, String.valueOf(R.id.seekBarCosts) + "filterCostsMin", 0.6);
            SharedPreferencesHelper.putDouble(sharedPrefEditor, String.valueOf(R.id.seekBarCosts) + "filterCostsMax", 1.0);

        }

        sharedPrefEditor.putInt(String.valueOf(R.id.seekBarCosts), progress);
        sharedPrefEditor.apply();
    }

    private void setSeekPersonsProgress(View view, int progress) {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefEditor = sharedPref.edit();

        ImageView imgPerson1 = (ImageView) view.findViewById(R.id.settingsPersons1);
        ImageView imgPerson2 = (ImageView) view.findViewById(R.id.settingsPersons2);
        ImageView imgPerson3 = (ImageView) view.findViewById(R.id.settingsPersons3);
        ImageView imgPerson4 = (ImageView) view.findViewById(R.id.settingsPersons4);

        switch (progress) {
            case 0:
                // Set all images white for delete button functionality
                imgPerson1.setColorFilter(view.getResources().getColor(R.color.blue));
                imgPerson2.setColorFilter(view.getResources().getColor(R.color.white));
                imgPerson3.setColorFilter(view.getResources().getColor(R.color.white));
                imgPerson4.setColorFilter(view.getResources().getColor(R.color.white));
                sharedPrefEditor.putInt(String.valueOf(R.id.seekBarPersons) + "filterPersons", 1);
                break;
            case 1:
                imgPerson1.setColorFilter(view.getResources().getColor(R.color.blue));
                imgPerson2.setColorFilter(view.getResources().getColor(R.color.blue));
                imgPerson3.setColorFilter(view.getResources().getColor(R.color.white));
                imgPerson4.setColorFilter(view.getResources().getColor(R.color.white));
                sharedPrefEditor.putInt(String.valueOf(R.id.seekBarPersons) + "filterPersons", 2);

                break;
            case 2:
                imgPerson1.setColorFilter(view.getResources().getColor(R.color.blue));
                imgPerson2.setColorFilter(view.getResources().getColor(R.color.blue));
                imgPerson3.setColorFilter(view.getResources().getColor(R.color.blue));
                imgPerson4.setColorFilter(view.getResources().getColor(R.color.white));
                sharedPrefEditor.putInt(String.valueOf(R.id.seekBarPersons) + "filterPersons", 3);
                break;
            case 3:
                imgPerson1.setColorFilter(view.getResources().getColor(R.color.blue));
                imgPerson2.setColorFilter(view.getResources().getColor(R.color.blue));
                imgPerson3.setColorFilter(view.getResources().getColor(R.color.blue));
                imgPerson4.setColorFilter(view.getResources().getColor(R.color.blue));
                sharedPrefEditor.putInt(String.valueOf(R.id.seekBarPersons) + "filterPersons", 4);
        }

        sharedPrefEditor.putInt(String.valueOf(R.id.seekBarPersons), progress);
        sharedPrefEditor.apply();
    }

    private void setFilterType(Spinner dropdown, int position) {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefEditor = sharedPref.edit();

        sharedPrefEditor.putInt(String.valueOf(R.id.settingsTypeDropDown) + "filterTypePosition", position);

        if (position == 0) {
            sharedPrefEditor.putString(String.valueOf(R.id.settingsTypeDropDown) + "filterTypeValue", "");
        } else {
            String selectedTypeValue = (String) dropdown.getItemAtPosition(position);
            sharedPrefEditor.putString(String.valueOf(R.id.settingsTypeDropDown) + "filterTypeValue", selectedTypeValue);
        }


        sharedPrefEditor.apply();

    }
}