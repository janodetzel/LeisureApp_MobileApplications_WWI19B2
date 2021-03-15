package com.example.leisureapp.fragments;

import android.content.Intent;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {

    // 0 means no filter
    public static int filterCosts = 0;
    public static int filterPersons = 0;

    public static int selectedTypePosition = 0;
    // is null if get all
    public static String selectedTypeText;

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
        ImageView imgCost1 = (ImageView) view.findViewById(R.id.settingsCosts1);
        ImageView imgCost2 = (ImageView) view.findViewById(R.id.settingsCosts2);
        ImageView imgCost3 = (ImageView) view.findViewById(R.id.settingsCosts3);
        ImageView imgCost4 = (ImageView) view.findViewById(R.id.settingsCosts4);
        ImageView imgPerson1 = (ImageView) view.findViewById(R.id.settingsPersons1);
        ImageView imgPerson2 = (ImageView) view.findViewById(R.id.settingsPersons2);
        ImageView imgPerson3 = (ImageView) view.findViewById(R.id.settingsPersons3);
        ImageView imgPerson4 = (ImageView) view.findViewById(R.id.settingsPersons4);
        ImageView costsDel = (ImageView) view.findViewById(R.id.settingsCostsDel);
        ImageView personsDel = (ImageView) view.findViewById(R.id.settingsPersonsDel);

        // Progress SeekBar
        SeekBar seekCosts = (SeekBar) view.findViewById(R.id.seekBarCosts);
        seekCosts.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                if (progress == 0) {
                    imgCost1.setColorFilter(view.getResources().getColor(R.color.white));
                    imgCost2.setColorFilter(view.getResources().getColor(R.color.white));
                    imgCost3.setColorFilter(view.getResources().getColor(R.color.white));
                    imgCost4.setColorFilter(view.getResources().getColor(R.color.white));

                    filterCosts = 0;
                }

                if (progress >= 20) {
                    imgCost1.setColorFilter(view.getResources().getColor(R.color.blue));
                    imgCost2.setColorFilter(view.getResources().getColor(R.color.white));
                    filterCosts = 1;
                }

                if (progress >= 40) {
                    imgCost2.setColorFilter(view.getResources().getColor(R.color.blue));
                    imgCost3.setColorFilter(view.getResources().getColor(R.color.white));
                    filterCosts = 2;
                }

                if (progress >= 60) {
                    imgCost3.setColorFilter(view.getResources().getColor(R.color.blue));
                    imgCost4.setColorFilter(view.getResources().getColor(R.color.white));
                    filterCosts = 3;
                }

                if (progress >= 80) {
                    imgCost4.setColorFilter(view.getResources().getColor(R.color.blue));
                    filterCosts = 4;
                }
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
        seekPersons.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                switch (progress) {
                    case 0:
                        // Set all images white for delete button functionality
                        imgPerson1.setColorFilter(view.getResources().getColor(R.color.white));
                        imgPerson2.setColorFilter(view.getResources().getColor(R.color.white));
                        imgPerson3.setColorFilter(view.getResources().getColor(R.color.white));
                        imgPerson4.setColorFilter(view.getResources().getColor(R.color.white));

                        filterPersons = 0;
                        break;
                    case 1:
                        imgPerson1.setColorFilter(view.getResources().getColor(R.color.blue));
                        imgPerson2.setColorFilter(view.getResources().getColor(R.color.white));
                        filterPersons = 1;
                        break;
                    case 2:
                        imgPerson2.setColorFilter(view.getResources().getColor(R.color.blue));
                        imgPerson3.setColorFilter(view.getResources().getColor(R.color.white));
                        filterPersons = 2;
                        break;
                    case 3:
                        imgPerson3.setColorFilter(view.getResources().getColor(R.color.blue));
                        imgPerson4.setColorFilter(view.getResources().getColor(R.color.white));
                        filterPersons = 3;
                        break;
                    case 4:
                        imgPerson4.setColorFilter(view.getResources().getColor(R.color.blue));
                        filterPersons = 4;
                }
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

        dropDown.setSelection(selectedTypePosition);

        dropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                selectedTypePosition = position;
                if (position == 0) {
                    selectedTypeText = null;
                } else {
                    selectedTypeText = (String) parent.getItemAtPosition(position);
                }
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
                seekCosts.setProgress(0);
                filterCosts = 0;
            }
        });

        personsDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seekPersons.setProgress(0);
                filterPersons = 0;
            }
        });
    }
}