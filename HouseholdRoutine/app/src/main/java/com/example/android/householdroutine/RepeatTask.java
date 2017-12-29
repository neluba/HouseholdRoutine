package com.example.android.householdroutine;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.android.householdroutine.Notification.StartReminder;
import com.example.android.householdroutine.data.DbContract;
import com.example.android.householdroutine.utilities.UserPoints;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class RepeatTask extends AppCompatActivity {

    private Calendar calendar = Calendar.getInstance();
    private EditText endDate;
    private EditText endTime;

    private String mName;
    private String mDescription;

    long reminderId = 0;
    int reminderType = DbContract.RemindersEntry.TYPE_REMINDER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repeat_task);

        if (getIntent().hasExtra(MainActivity.EXTRA_REMINDER_ID)) {
            reminderId = getIntent().getLongExtra(MainActivity.EXTRA_REMINDER_ID, -1);
        }
        if (reminderId == -1)
            finish();
        if(getIntent().hasExtra(MainActivity.EXTRA_REMINDER_TYPE)) {
            reminderType = getIntent().getIntExtra(MainActivity.EXTRA_REMINDER_TYPE,
                    DbContract.RemindersEntry.TYPE_REMINDER);
        }
        if(getIntent().hasExtra(MainActivity.EXTRA_REMINDER_NAME)) {
            mName = getIntent().getStringExtra(MainActivity.EXTRA_REMINDER_NAME);
        }
        if(getIntent().hasExtra(MainActivity.EXTRA_REMINDER_DESCRIPTION)) {
            mDescription = getIntent().getStringExtra(MainActivity.EXTRA_REMINDER_DESCRIPTION);
        }


        endDate = findViewById(R.id.repeat_end_date);
        endTime = findViewById(R.id.repeat_end_time);



        endDate.setKeyListener(null);
        final DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);
                updateEndDate();
            }
        };

        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog picker = new DatePickerDialog(RepeatTask.this,
                        dateListener,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                picker.getDatePicker().setMinDate(System.currentTimeMillis()-
                        TimeUnit.SECONDS.toMillis(1));
                picker.show();
            }
        });

        // end time time picker
        endTime.setKeyListener(null);
        final TimePickerDialog.OnTimeSetListener timeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);
                updateEndTime();
            }
        };

        final boolean hourFormat = android.text.format.DateFormat.is24HourFormat(getApplicationContext());
        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerDialog(RepeatTask.this,
                        timeListener,
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        hourFormat
                ).show();
            }
        });

        // fab initialize
        FloatingActionButton fab =  findViewById(R.id.repeat_save_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTask();
            }
        });
    }

    /**
     * Updates the endDate view with the picked date
     */
    private void updateEndDate() {
        Date date = new Date(calendar.getTimeInMillis());
        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());
        endDate.setText(dateFormat.format(date));
    }

    /**
     * Updates the endTime view with the picked time
     */
    private void updateEndTime() {
        Date date = new Date(calendar.getTimeInMillis());
        DateFormat dateFormat = android.text.format.DateFormat.getTimeFormat(getApplicationContext());
        endTime.setText(dateFormat.format(date));
    }

    /**
     * Saves the reminder with a new date
     */
    private void saveTask() {
        if (TextUtils.isEmpty(endDate.getText().toString())) {
            Toast.makeText(RepeatTask.this, R.string.new_task_date_missing, Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(endTime.getText().toString())) {
            Toast.makeText(RepeatTask.this, R.string.new_task_time_missing, Toast.LENGTH_SHORT).show();
            return;
        }

        // update the reminder in the database
        ContentValues reminderValues = new ContentValues();
        reminderValues.put(DbContract.RemindersEntry.COLUMN_END_DATE, calendar.getTimeInMillis());
        getContentResolver().update(
                DbContract.RemindersEntry.buildRemindersUriWithId(reminderId),
                reminderValues,
                null,
                null);
        // reset the checklist
        if(reminderType == DbContract.RemindersEntry.TYPE_CHECKLIST) {
            ContentValues checklistValues = new ContentValues();
            checklistValues.put(DbContract.ChecklistEntry.COLUMN_COMPLETED, DbContract.ChecklistEntry.COMPLETED_FALSE);
            getContentResolver().update(
                    DbContract.ChecklistEntry.CONTENT_URI,
                    checklistValues,
                    DbContract.ChecklistEntry.COLUMN_REMINDER_ID + "=?",
                    new String[]{String.valueOf(reminderId)});

        }

        // reward points
        UserPoints.rewardReminderCompletePoints(
                getApplicationContext(),
                mName,
                mDescription);

        // cancel the reminder if it is still open
        StartReminder.cancelReminder(reminderId, getApplicationContext());
        StartReminder.startReminder(reminderId, calendar.getTimeInMillis(), getApplicationContext());

        // finish up
        Toast.makeText(RepeatTask.this, R.string.new_task_saved, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
