package com.example.diabetes_app_mobile.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.diabetes_app_mobile.R;
import com.example.diabetes_app_mobile.SettingsManager;

public class SettingsFragment extends Fragment {

    private SettingsManager settingsManager;
    private EditText editText;
    private Button saveButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        settingsManager = new SettingsManager();
        editText = view.findViewById(R.id.editText);
        saveButton = view.findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveText();
            }
        });
        settingsManager.loadText(editText);

        return view;
    }

    private void saveText() {
        String text = editText.getText().toString();
        settingsManager.saveText(text);
    }

}