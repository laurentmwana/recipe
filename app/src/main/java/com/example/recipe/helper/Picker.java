package com.example.recipe.helper;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.TextClock;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public abstract class Picker {

    public static TextClock date(TextClock clock) {





        return clock;
    }

    public static void onTime(TextView view) {
        TimePickerDialog dialog = new TimePickerDialog(view.getContext(), (timePicker, hours, minute) -> {
            String h = hours < 10 ? "0" + hours : String.valueOf(hours);
            String m = minute < 10 ? "0" + minute : String.valueOf(minute);
            String time = h + ":" + m;
            view.setText(time);
        }, 12, 30, true);
        dialog.show();
    }

    public static void onDate(EditText view) {

        final Calendar c = Calendar.getInstance();

        // on below line we are getting
        // our day, month and year.
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(view.getContext(), (datePicker, y, m, d) -> {
            String df = d < 10 ? "0" + d : String.valueOf(d);
            String mf = m < 10 ? "0" + (m + 1) : String.valueOf(m + 1);
            String  date = y + "-" + mf + "-" + df;
            view.setText(date);
        }, year, month, day);

        dialog.show();
    }


}
