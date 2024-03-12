package com.example.diabetes_app_mobile;

import android.widget.EditText;

import androidx.annotation.NonNull;

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
    }

    public void initMultiplier(final Callback<Double> callback) {
        DatabaseReference multiplierRef = databaseReference.child("textValue");
        multiplierRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String xBMultiplierString = dataSnapshot.getValue(String.class);
                    xBMultiplierString = xBMultiplierString.replace(",", ".");
                    try {
                        multiplier = Double.parseDouble(xBMultiplierString);
                    } catch (NumberFormatException e) {
                        multiplier = 0.0;
                    }
                } else {
                    multiplier = 0.0;
                }
                callback.onCallback(multiplier);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                multiplier = 0.0;
                callback.onCallback(multiplier);
            }
        });
    }

    public void saveText(String text) {
        databaseReference.child("textValue").setValue(text);
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

    public interface Callback<T> {
        void onCallback(T data);
    }
}
