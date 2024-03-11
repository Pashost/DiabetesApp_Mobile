package com.example.diabetes_app_mobile.fragments;

import static android.content.ContentValues.TAG;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.diabetes_app_mobile.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class DatabaseReaderFragment extends Fragment {
    private DatabaseReference databaseReference;
    private LinearLayout containerLayout;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_values_reader, container, false);
        containerLayout = view.findViewById(R.id.containerLayout);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this::loadData);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("insulin_values");
        loadData();
    }

    private void loadData() {
        swipeRefreshLayout.setRefreshing(true);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                containerLayout.removeAllViews();

                // Loop through dataSnapshot to get each child
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String time = getTimeStringFromSnapshot(snapshot);
                    String sugarValue = String.valueOf(snapshot.child("SugarValue").getValue());
                    String foodCoefficient = String.valueOf(snapshot.child("FoodCoefficient").getValue());
                    String insulinValue = String.valueOf(snapshot.child("InsulinValue").getValue());

                    // Create a TextView to display the data
                    TextView textView = new TextView(requireContext());
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        textView.setTextAppearance(R.style.TextAppearance_AppCompat_Body1);
                    }
                    textView.setTextSize(16); // Set text size (sp)
                    textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
                    textView.setPadding(0, 8, 0, 8);
                    textView.setText(String.format(Locale.getDefault(), "%s\nSugarValue: %s\nFood Coefficient: %s\nInsulin Value: %s\n",
                            time, sugarValue, foodCoefficient, insulinValue));
                    containerLayout.addView(textView, 0);
                }

                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Failed to read value.", databaseError.toException());
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
    private String getTimeStringFromSnapshot(DataSnapshot snapshot) {
        Integer yearInteger = snapshot.child("Year").getValue(Integer.class);
        Integer monthInteger = snapshot.child("Month").getValue(Integer.class);
        Integer dayInteger = snapshot.child("Day").getValue(Integer.class);
        Integer hourInteger = snapshot.child("Hour").getValue(Integer.class);
        Integer minuteInteger = snapshot.child("Minute").getValue(Integer.class);
        Integer secondInteger = snapshot.child("Second").getValue(Integer.class);

        if (yearInteger == null || monthInteger == null || dayInteger == null ||
                hourInteger == null || minuteInteger == null || secondInteger == null) {
            return "";
        }

        int year = yearInteger.intValue();
        int month = monthInteger.intValue();
        int day = dayInteger.intValue();
        int hour = hourInteger.intValue();
        int minute = minuteInteger.intValue();
        int second = secondInteger.intValue();

        return String.format(Locale.getDefault(), "Date: %04d-%02d-%02d \n" +
                "Time: %02d:%02d:%02d", year, month, day, hour, minute, second);
    }
}
