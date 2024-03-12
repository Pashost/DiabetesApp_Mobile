package com.example.diabetes_app_mobile;

import android.widget.EditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SettingsManager {
    private DatabaseReference databaseReference;
    public static double multiplier;

    public SettingsManager() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("settings");
        multiplier = getXBMultiplier();
    }

    private double getXBMultiplier() {
        return 0;
    }

    public void saveText(String text) {
        databaseReference.child("textValue").setValue(text);
    }

    public double getXBMultiplier(EditText editText) {
        String multiplierString = "1.0";
        if (editText != null) {
            multiplierString = editText.getText().toString();
        }
        try {
            double multiplier = Double.parseDouble(multiplierString);
            return multiplier;
        } catch (NumberFormatException e) {
            return 1.0;
        }
    }

    public void loadText(final EditText editText) {
        databaseReference.child("textValue").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String savedText = dataSnapshot.getValue(String.class);
                if (savedText != null) {
                    editText.setText(savedText);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}