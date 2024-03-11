package com.example.diabetes_app_mobile;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class Data_saver  {
    private final DatabaseReference database;

    public Data_saver() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        database = firebaseDatabase.getReference("insulin_values");
    }

    public void saveData(double insulinValue, double sugarValue, double xbValue) {

        DatabaseReference newEntryRef = database.push();

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        newEntryRef.child("Year").setValue(year);
        newEntryRef.child("Month").setValue(month);
        newEntryRef.child("Day").setValue(day);
        newEntryRef.child("Hour").setValue(hour);
        newEntryRef.child("Minute").setValue(minute);
        newEntryRef.child("Second").setValue(second);
        newEntryRef.child("SugarValue").setValue(sugarValue);
        newEntryRef.child("FoodCoefficient").setValue(xbValue);
        newEntryRef.child("InsulinValue").setValue(insulinValue)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                    }
                });
    }
}
