package com.example.android.householdroutine.Notification;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.android.householdroutine.data.DbContract;

/**
 * Created by oliver on 05.12.2017.
 * JobService to restart all alarms for android 8+
 */


@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class RestartAlarmJob extends JobService {

    private static final String TAG = "RestartAlarmService";

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
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
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return true;
    }
}
