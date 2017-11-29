package com.example.android.householdroutine.utilities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.android.householdroutine.data.DbContract;

/**
 * Created by oliver on 28.11.2017.
 */

public class UserPoints {

    /**
     * Rewards the user with points after completing a reminder
     *
     * @param context     application context
     * @param name        name of the reminder
     * @param description description of the reminder
     */
    public static void rewardReminderCompletePoints(Context context, String name, String description) {
        // add points to the database
        ContentValues values = new ContentValues();
        values.put(DbContract.UserPointsEntry.COLUMN_TITLE, name);
        values.put(DbContract.UserPointsEntry.COLUMN_DESCRIPTION, description);
        values.put(DbContract.UserPointsEntry.COLUMN_POINTS, DbContract.UserPointsEntry.POINTS_REMINDER);
        values.put(DbContract.UserPointsEntry.COLUMN_DATE, System.currentTimeMillis());
        values.put(DbContract.UserPointsEntry.COLUMN_TYPE, DbContract.UserPointsEntry.TYPE_REMINDER);

        context.getContentResolver().insert(DbContract.UserPointsEntry.CONTENT_URI, values);

        //check the completed reminders count
        Cursor cursor = context.getContentResolver().query(
                DbContract.UserPointsEntry.USER_POINTS_REMINDERS_COUNT_CONTENT_URI,
                null,
                null,
                null,
                null);
        if (cursor == null)
            return;
        cursor.moveToFirst();
        int index = cursor.getColumnIndex(DbContract.UserPointsEntry.REMINDERS_COUNT);
        int remindersCount = cursor.getInt(index);
        cursor.close();

        // check if the user earned a new information
        if (remindersCount % 3 == 0)
            awardNewInformation(context);
    }

    /**
     * Rewards the user with a new information
     *
     * @param context application context
     */
    private static void awardNewInformation(Context context) {
        // get all available informations from the database
        final String[] projection = {
                DbContract.InformationSetsEntry._ID
        };
        final int INDEX_ID = 0;
        String[] selectionArg = new String[]{
                String.valueOf(DbContract.InformationSetsEntry.OBTAINABLE_TRUE),
                String.valueOf(DbContract.InformationSetsEntry.OBTAINED_FALSE)};
        Cursor cursor = context.getContentResolver().query(
                DbContract.InformationSetsEntry.CONTENT_URI,
                projection,
                DbContract.InformationSetsEntry.COLUMN_OBTAINABLE + "=? AND " +
                DbContract.InformationSetsEntry.COLUMN_OBTAINED + "=?",
                selectionArg,
                null);

        if(cursor == null)
            return;
        int count = cursor.getCount();
        if (count == 0)
            return;
        // select a random information
        int rewardedInformation = (int) (Math.random()*count);
        cursor.moveToPosition(rewardedInformation);
        int id = cursor.getInt(INDEX_ID);
        cursor.close();

        // update the information to be available to the user
        ContentValues values = new ContentValues();
        values.put(DbContract.InformationSetsEntry.COLUMN_OBTAINED,
                DbContract.InformationSetsEntry.OBTAINED_TRUE);
        context.getContentResolver().update(
                DbContract.InformationSetsEntry.buildUriWithId(id),
                values,
                null,
                null);
    }
}
