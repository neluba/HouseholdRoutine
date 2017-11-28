package com.example.android.householdroutine.Notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.example.android.householdroutine.MainActivity;
import com.example.android.householdroutine.R;
import com.example.android.householdroutine.TaskDetails;
import com.example.android.householdroutine.data.DbContract;

/**
 * Created by oliver on 28.11.2017.
 */

public class AlarmReceiver extends BroadcastReceiver {

    private String notificationChannel = "com.example.android.householdroutine.ONE";

    @Override
    public void onReceive(Context context, Intent intent) {
        long reminderId = -1;

        // check if the intent has the reminder id and get it
        if (intent.hasExtra(MainActivity.EXTRA_REMINDER_ID)) {
            reminderId = intent.getLongExtra(MainActivity.EXTRA_REMINDER_ID, -1);

            if(reminderId == -1) {
                return;
            }


            // Get the name and description out of the database
            final String[] PROJECTION = {
                    DbContract.RemindersEntry.COLUMN_NAME,
                    DbContract.RemindersEntry.COLUMN_DESCRIPTION};
            final int INDEX_NAME = 0;
            final int INDEX_DESCRIPTION = 1;

            Cursor cursor = context.getContentResolver().query(DbContract.RemindersEntry.buildRemindersUriWithId(reminderId),
                    PROJECTION,
                    null,
                    null,
                    null);
            if(cursor == null || cursor.getCount() == 0)
                return;
            cursor.moveToFirst();
            String name = cursor.getString(INDEX_NAME);
            String description = cursor.getString(INDEX_DESCRIPTION);
            cursor.close();

            // build the intent for the notification that sends the user to the details view
            Intent detailsIntent = new Intent(context, TaskDetails.class);
            detailsIntent.putExtra(MainActivity.EXTRA_REMINDER_ID, reminderId);
            TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(context);
            taskStackBuilder.addNextIntentWithParentStack(detailsIntent);
            PendingIntent resultPendingIntent = taskStackBuilder
                    .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

            // build the notification
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, notificationChannel);
            builder.setAutoCancel(true)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(getNotificationIcon())
                    .setContentTitle(name)
                    .setContentText(description)
                    .setContentIntent(resultPendingIntent)
                    .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);


            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            // create a notification channel (android 8+ only)
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(notificationChannel,
                        context.getString(R.string.notification_channel_name),
                        NotificationManager.IMPORTANCE_DEFAULT);
                notificationManager.createNotificationChannel(channel);
            }
            notificationManager.notify(1, builder.build());

            // cancel the alarm
            StartReminder.cancelReminder(reminderId, context);
        }
    }

    private int getNotificationIcon() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return R.drawable.ic_notification_icon;
        } else {
            return R.mipmap.ic_launcher_round;
        }
    }
}
