package com.example.android.householdroutine.Notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.example.android.householdroutine.MainActivity;

/**
 * Created by oliver on 28.11.2017.
 */

public class StartReminder {

    /**
     * Starts a reminder with the reminder id, time when the notification shows up and the application context
     *
     * @param reminderId
     * @param endDate
     * @param context
     */
    public static void startReminder(long reminderId, long endDate, Context context) {
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(MainActivity.EXTRA_REMINDER_ID, reminderId);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        manager.set(AlarmManager.RTC, endDate, pendingIntent);
    }

    public static void cancelReminder(long reminderId, Context context) {
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(MainActivity.EXTRA_REMINDER_ID, reminderId);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        try {
            manager.cancel(pendingIntent);
        } catch (NullPointerException e) {
            // Alarm got already canceled
        }
    }
}
