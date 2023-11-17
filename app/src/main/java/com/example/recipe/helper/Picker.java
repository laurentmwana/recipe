package com.example.recipe.helper;

import android.app.TimePickerDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.TextClock;
import android.widget.TextView;

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

}
