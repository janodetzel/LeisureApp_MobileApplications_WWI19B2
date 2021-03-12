package com.example.leisureapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
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
                if(position == 0) {
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

        ImageView costs1 = (ImageView) view.findViewById(R.id.settingsCosts1);
        ImageView costs2 = (ImageView) view.findViewById(R.id.settingsCosts2);
        ImageView costs3 = (ImageView) view.findViewById(R.id.settingsCosts3);
        ImageView costs4 = (ImageView) view.findViewById(R.id.settingsCosts4);
        ImageView costsdel = (ImageView) view.findViewById(R.id.settingsCostsDel);

        ImageView persons1 = (ImageView) view.findViewById(R.id.settingsPersons1);
        ImageView persons2 = (ImageView) view.findViewById(R.id.settingsPersons2);
        ImageView persons3 = (ImageView) view.findViewById(R.id.settingsPersons3);
        ImageView persons4 = (ImageView) view.findViewById(R.id.settingsPersons4);
        ImageView personsdel = (ImageView) view.findViewById(R.id.settingsPersonsDel);

        // set colors onCreate (init is white, so simply change if necessary)
        if(filterCosts >= 4) {
            costs4.setColorFilter(view.getResources().getColor(R.color.blue));
        }
        if(filterCosts >= 3) {
            costs3.setColorFilter(view.getResources().getColor(R.color.blue));
        }
        if(filterCosts >= 2) {
            costs2.setColorFilter(view.getResources().getColor(R.color.blue));
        }
        if(filterCosts >= 1) {
            costs1.setColorFilter(view.getResources().getColor(R.color.blue));
        }

        if(filterPersons >= 4) {
            persons4.setColorFilter(view.getResources().getColor(R.color.blue));
        }
        if(filterPersons >= 3) {
            persons3.setColorFilter(view.getResources().getColor(R.color.blue));
        }
        if(filterPersons >= 2) {
            persons2.setColorFilter(view.getResources().getColor(R.color.blue));
        }
        if(filterPersons >= 1) {
            persons1.setColorFilter(view.getResources().getColor(R.color.blue));
        }

        // On Click Listeners
        costs1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView iv = ((ImageView) view);
                costs4.setColorFilter(view.getResources().getColor(R.color.white));
                costs3.setColorFilter(view.getResources().getColor(R.color.white));
                costs2.setColorFilter(view.getResources().getColor(R.color.white));
                costs1.setColorFilter(view.getResources().getColor(R.color.blue));
                filterCosts = 1;
            }
        });
        costs2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                costs4.setColorFilter(view.getResources().getColor(R.color.white));
                costs3.setColorFilter(view.getResources().getColor(R.color.white));
                costs2.setColorFilter(view.getResources().getColor(R.color.blue));
                costs1.setColorFilter(view.getResources().getColor(R.color.blue));
                filterCosts = 2;
            }
        });
        costs3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                costs4.setColorFilter(view.getResources().getColor(R.color.white));
                costs3.setColorFilter(view.getResources().getColor(R.color.blue));
                costs2.setColorFilter(view.getResources().getColor(R.color.blue));
                costs1.setColorFilter(view.getResources().getColor(R.color.blue));
                filterCosts = 3;
            }
        });
        costs4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                costs4.setColorFilter(view.getResources().getColor(R.color.blue));
                costs3.setColorFilter(view.getResources().getColor(R.color.blue));
                costs2.setColorFilter(view.getResources().getColor(R.color.blue));
                costs1.setColorFilter(view.getResources().getColor(R.color.blue));
                filterCosts = 4;
            }
        });
        costsdel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                costs4.setColorFilter(view.getResources().getColor(R.color.white));
                costs3.setColorFilter(view.getResources().getColor(R.color.white));
                costs2.setColorFilter(view.getResources().getColor(R.color.white));
                costs1.setColorFilter(view.getResources().getColor(R.color.white));
                filterCosts = 0;
            }
        });


        persons1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                persons4.setColorFilter(view.getResources().getColor(R.color.white));
                persons3.setColorFilter(view.getResources().getColor(R.color.white));
                persons2.setColorFilter(view.getResources().getColor(R.color.white));
                persons1.setColorFilter(view.getResources().getColor(R.color.blue));
                filterPersons = 1;
            }
        });
        persons2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                persons4.setColorFilter(view.getResources().getColor(R.color.white));
                persons3.setColorFilter(view.getResources().getColor(R.color.white));
                persons2.setColorFilter(view.getResources().getColor(R.color.blue));
                persons1.setColorFilter(view.getResources().getColor(R.color.blue));
                filterPersons = 2;
            }
        });
        persons3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                persons4.setColorFilter(view.getResources().getColor(R.color.white));
                persons3.setColorFilter(view.getResources().getColor(R.color.blue));
                persons2.setColorFilter(view.getResources().getColor(R.color.blue));
                persons1.setColorFilter(view.getResources().getColor(R.color.blue));
                filterPersons = 3;
            }
        });
        persons4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView iv = ((ImageView) view);
                persons4.setColorFilter(view.getResources().getColor(R.color.blue));
                persons3.setColorFilter(view.getResources().getColor(R.color.blue));
                persons2.setColorFilter(view.getResources().getColor(R.color.blue));
                persons1.setColorFilter(view.getResources().getColor(R.color.blue));
                filterPersons = 4;
            }
        });
        personsdel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView iv = ((ImageView) view);
                persons4.setColorFilter(view.getResources().getColor(R.color.white));
                persons3.setColorFilter(view.getResources().getColor(R.color.white));
                persons2.setColorFilter(view.getResources().getColor(R.color.white));
                persons1.setColorFilter(view.getResources().getColor(R.color.white));
                filterPersons = 0;
            }
        });

    }
}