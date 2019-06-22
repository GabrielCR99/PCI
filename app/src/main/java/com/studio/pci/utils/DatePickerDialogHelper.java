package com.studio.pci.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class DatePickerDialogHelper {

    private DatePickerDialogHelper() {
    }

    public static void setDatePickerDialog(View view, final Context context, final SimpleDateFormat dateFormatter) {

        final EditText dateEditText = (EditText) view;

        dateEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                dateEditText.callOnClick();
            }
        });

        dateEditText.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();

            if (!TextUtils.isEmpty(dateEditText.getText().toString())) {
                try {
                    Date date = dateFormatter.parse(dateEditText.getText().toString());
                    calendar.setTime(date);
                } catch (ParseException e) {
                    Log.e("DATE PICKER", "Erro ao converter a data");
                }
            }

            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);

            DatePickerDialog picker = new DatePickerDialog(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT,
                    (datePicker, year1, month1, day1) -> {
                        calendar.set(year1, month1, day1);
                        String date = dateFormatter.format(calendar.getTime());
                        dateEditText.setText(date);
                        dateEditText.clearFocus();
                    }, year, month, day);

            picker.show();
            hideKeyboard( context,dateEditText);
        });
    }

    private static void hideKeyboard(Context context,View view) {
        final InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        // TODO NAO FUNCIONA
    }
}
