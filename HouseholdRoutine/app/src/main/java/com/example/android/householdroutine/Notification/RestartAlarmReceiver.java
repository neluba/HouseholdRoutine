package com.example.android.householdroutine.Notification;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

/**
 * Created by oliver on 05.12.2017.
 */

public class RestartAlarmReceiver extends BroadcastReceiver {
    String tag = "HouseholdRoutine BootService";

    @Override
    public void onReceive(Context context, Intent intent) {
        if("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            Intent serviceIntent = new Intent(context, RestartAlarmService.class);
            ComponentName service;
            // android 8 can't start services on boot, so it has to be done in a jobservice
            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {
                service = new ComponentName(context, RestartAlarmJob.class);
                JobInfo.Builder builder = new JobInfo.Builder(0, service);
                builder.setMinimumLatency(0); // 0 seconds
                builder.setOverrideDeadline(3 * 1000); // 30 seconds
                JobScheduler jobScheduler = context.getSystemService(JobScheduler.class);
                jobScheduler.schedule(builder.build());
            } else {
                service = context.startService(serviceIntent);
            }
            if (service == null) {
                Log.e(tag, "Couldn't start the service");
            } else {
                Log.e(tag, "Boot service started");
            }
        }
    }
}
