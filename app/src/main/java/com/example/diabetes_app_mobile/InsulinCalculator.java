package com.example.diabetes_app_mobile;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Context;

public class InsulinCalculator {

    public static double calculateInsulin(EditText sugarEditText, EditText xbEditText, CheckBox checkBox, Context context) {
        double insulinValue = 0;
        double xbValue;
        try {
            double sugarValue = Double.parseDouble(sugarEditText.getText().toString());
            double existingXBValue = Double.parseDouble(xbEditText.getText().toString());
            xbValue = existingXBValue;

            if (checkBox.isChecked()) {
                xbValue = calculateXBValue(existingXBValue);
            }

            insulinValue = calculateInsulinValue(sugarValue, xbValue);
            Toast.makeText(context, "Insulin Value: " + insulinValue, Toast.LENGTH_SHORT).show();
        } catch (NumberFormatException e) {
            Toast.makeText(context, "Invalid input. Please enter valid numbers.", Toast.LENGTH_SHORT).show();
        }
        return insulinValue;
    }

    public static double calculateInsulinValue(double sugar, double xb) {
        if (sugar >= 6) {
            return (sugar - 6) / 2 + xb;
        } else {
            return xb;
        }
    }

    private static double calculateXBValue(double existingXB) {

        return existingXB;
    }
}
