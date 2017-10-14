package com.example.android.householdroutine.utilities;

import android.content.ContentValues;
import android.content.Context;

import com.example.android.householdroutine.data.DbContract;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by oliver on 14.10.2017.
 */

public class FakeReminders {
    public static void insertFakeData(Context context) {
        List<ContentValues> fakeDate = new ArrayList<ContentValues>();
        for(int i = 0; i<20; i++) {
            fakeDate.add(createContentValues("Reminder: " + String.valueOf(i), "description"));
        }
        // remove old data
        context.getContentResolver().delete(DbContract.RemindersEntry.CONTENT_URI, null, null);
        // insert new data
        context.getContentResolver().bulkInsert(DbContract.RemindersEntry.CONTENT_URI,
                fakeDate.toArray(new ContentValues[20]));
    }

    private static ContentValues createContentValues(String name, String description) {
        ContentValues testData = new ContentValues();
        long today = System.currentTimeMillis();
        long fakeEndDate;


        testData.put(DbContract.RemindersEntry.COLUMN_NAME, name);
        testData.put(DbContract.RemindersEntry.COLUMN_DESCRIPTION, description);
        testData.put(DbContract.RemindersEntry.COLUMN_START_DATE, today);
        fakeEndDate = today + TimeUnit.DAYS.toMillis((int) (Math.random()*10));
        testData.put(DbContract.RemindersEntry.COLUMN_END_DATE, fakeEndDate);
        testData.put(DbContract.RemindersEntry.COLUMN_CHECKLIST, -1);
        testData.put(DbContract.RemindersEntry.COLUMN_OUTDATED, 0);
        int type = (int) (Math.random()*2);
        testData.put(DbContract.RemindersEntry.COLUMN_TYPE, type);
        return testData;
    }

}
