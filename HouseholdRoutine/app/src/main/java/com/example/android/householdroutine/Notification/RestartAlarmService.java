package com.example.android.householdroutine.Notification;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.Nullable;

import com.example.android.householdroutine.data.DbContract;

/**
 * Created by oliver on 05.12.2017.
 * Restart all alarms in a service and on boot for android < 8
 */

public class RestartAlarmService extends IntentService {

    public RestartAlarmService() {
        super("RestartAlarmService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        // get all open reminders from the database
        final String projection[] = {
                DbContract.RemindersEntry._ID,
                DbContract.RemindersEntry.COLUMN_END_DATE};

        String[] currentTimeSelection = new String[]{String.valueOf(System.currentTimeMillis())};

        ContentResolver contentResolver = getApplicationContext().getContentResolver();
        Cursor cursor = contentResolver.query(
                DbContract.RemindersEntry.CONTENT_URI,
                projection,
                DbContract.RemindersEntry.COLUMN_END_DATE + "> ?",
                currentTimeSelection,
                null);

        // cancel the old alarm if it was set and start a new one
        if (cursor.getCount() > 0) {
            long id = 0;
            long endDate = 0;
            while (cursor.moveToNext()) {
                id = cursor.getLong(0);
                endDate = cursor.getLong(1);
                StartReminder.cancelReminder(id, getApplicationContext());
                StartReminder.startReminder(id, endDate, getApplicationContext());
            }
        }
        cursor.close();
    }
}
