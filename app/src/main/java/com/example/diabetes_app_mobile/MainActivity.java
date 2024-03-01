package com.example.diabetes_app_mobile;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private EditText sugarEditText;
    private EditText xbEditText;
    private CheckBox checkBox;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sugarEditText = findViewById(R.id.sugarEditText);
        xbEditText = findViewById(R.id.xbEditText);
        checkBox = findViewById(R.id.checkBox);
        Button calculateButton = findViewById(R.id.calculateButton);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        database = firebaseDatabase.getReference("insulin_values");

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateInsulin();
            }
        });
    }

    private void calculateInsulin() {
        try {
            double sugarValue = Double.parseDouble(sugarEditText.getText().toString());
            double xbValue = Double.parseDouble(xbEditText.getText().toString());

            if (checkBox.isChecked()) {
                // Apply some logic here if checkbox is checked
            }

            double insulinValue = calculateInsulinValue(sugarValue, xbValue);
            saveData(insulinValue, sugarValue, xbValue);
            Toast.makeText(MainActivity.this, "Insulin Value: " + insulinValue, Toast.LENGTH_SHORT).show();
        } catch (NumberFormatException e) {
            Toast.makeText(MainActivity.this, "Invalid input. Please enter valid numbers.", Toast.LENGTH_SHORT).show();
        }
    }

    private double calculateInsulinValue(double sugar, double xb) {
        if (sugar >= 6) {
            return (sugar - 6) / 2 + xb;
        } else {
            return xb;
        }
    }

    private void saveData(double insulinValue, double sugarValue, double xbValue) {
        try {
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
                        public void onFailure(@NonNull Exception e) {
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
