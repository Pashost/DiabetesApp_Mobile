package com.example.diabetes_app_mobile;

import android.app.Activity;
import android.content.Context;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class InsulinCalculator {
    public static double calculateInsulin(EditText sugarEditText, EditText xbEditText, CheckBox checkBox, double xbMultiplier, Context context) {
        double insulinValue = 0;
        try {
            double sugarValue = Double.parseDouble(sugarEditText.getText().toString());
            double existingXBValue = Double.parseDouble(xbEditText.getText().toString());
            double xbValue = existingXBValue;

            if (checkBox.isChecked()) {
                xbValue = calculateXBValue(existingXBValue, xbMultiplier);
            }

            insulinValue = calculateInsulinValue(sugarValue, xbValue);
            showToastOnUIThread(context, "Insulin Value: " + insulinValue);
        } catch (NumberFormatException e) {
            showToastOnUIThread(context, "Invalid input. Please enter valid numbers.");
        }

        return insulinValue;
    }
    private static double calculateInsulinValue(double sugar, double xb) {
        if (sugar >= 6) {
            return (sugar - 6) / 2 + xb;
        } else {
            return xb;
        }
    }

    private static double calculateXBValue(double existingXB, double multiplier) {
        return existingXB * multiplier;
    }

    private static void showToastOnUIThread(final Context context, final String message) {
        if (context != null) {
            ((Activity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
