package com.example.diabetes_app_mobile.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.diabetes_app_mobile.Data_saver;
import com.example.diabetes_app_mobile.InsulinCalculator;
import com.example.diabetes_app_mobile.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Values_taker extends Fragment {
    private EditText sugarEditText;
    private EditText xbEditText;
    private CheckBox checkBox;
    private DatabaseReference database;
    private double xbMultiplier = 1.0;

    public Values_taker() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Fetch the multiplier value synchronously from Firebase
        DatabaseReference settingsReference = FirebaseDatabase.getInstance().getReference("settings");
        settingsReference.child("textValue").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DataSnapshot dataSnapshot = task.getResult();
                if (dataSnapshot.exists()) {
                    String multiplierString = dataSnapshot.getValue(String.class);
                    try {
                        xbMultiplier = Double.parseDouble(multiplierString);
                    } catch (NumberFormatException e) {
                        xbMultiplier = 1.0;
                    }
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_values_taker, container, false);

        sugarEditText = view.findViewById(R.id.sugarEditText);
        xbEditText = view.findViewById(R.id.xbEditText);
        checkBox = view.findViewById(R.id.checkBox);
        Button calculateButton = view.findViewById(R.id.calculateButton);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        database = firebaseDatabase.getReference("insulin_values");

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double insulinValue = calculateInsulin();
                double sugarValue = Double.parseDouble(sugarEditText.getText().toString());
                double xbValue = Double.parseDouble(xbEditText.getText().toString());

                Data_saver dataSaver = new Data_saver();
                dataSaver.saveData(insulinValue, sugarValue, xbValue);
            }
        });

        return view;
    }

    public double calculateInsulin() {
        double insulinValue = InsulinCalculator.calculateInsulin(sugarEditText, xbEditText, checkBox, getContext());
        return insulinValue;
    }
}