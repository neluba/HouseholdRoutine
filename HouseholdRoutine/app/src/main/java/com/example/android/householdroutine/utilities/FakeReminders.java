package com.example.android.householdroutine.utilities;

import android.content.ContentResolver;
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
        ContentResolver contentResolver = context.getContentResolver();
        List<ContentValues> fakeDate = new ArrayList<ContentValues>();
        for(int i = 0; i<20; i++) {
            fakeDate.add(createContentValues("Reminder: " + String.valueOf(i), "description"));
        }
        // remove old data
        contentResolver.delete(DbContract.RemindersEntry.CONTENT_URI, null, null);
        // insert new data
        contentResolver.bulkInsert(DbContract.RemindersEntry.CONTENT_URI,
                fakeDate.toArray(new ContentValues[20]));


        // predef reminders
        fakeDate.clear();
        contentResolver.delete(DbContract.PredefinedRemindersEntry.CONTENT_URI, null, null);
        for(int i = 0; i<20; i++) {
            fakeDate.add(createPredefRemindersContentValues(i, "description"));
        }
        contentResolver.bulkInsert(DbContract.PredefinedRemindersEntry.CONTENT_URI, fakeDate.toArray(new ContentValues[20]));

        // predef checklist
        List<ContentValues> checklist = new ArrayList<ContentValues>();
        ArrayList<String> items = new ArrayList<String>();
        for (int i = 0; i<5; i++) {
            items.add(String.valueOf(i));
        }
        String jsonItem = ConvertJsonArray.listToJsonArray(items);
        for(int i = 0; i<20; i++) {
            checklist.add(createPredefChecklistContentValues(jsonItem, i));
        }
        contentResolver.bulkInsert(DbContract.PredefinedChecklistEntry.CONTENT_URI, checklist.toArray(new ContentValues[20]));



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
        int type = (int) (Math.random()*2);
        testData.put(DbContract.RemindersEntry.COLUMN_TYPE, type);
        return testData;
    }

    private static ContentValues createPredefRemindersContentValues(int id, String description) {
        ContentValues testData = new ContentValues();
        testData.put(DbContract.PredefinedRemindersEntry.COLUMN_NAME, "Reminder: " + String.valueOf(id));
        testData.put(DbContract.PredefinedRemindersEntry.COLUMN_DESCRIPTION, description);
        testData.put(DbContract.PredefinedRemindersEntry.COLUMN_CHECKLIST, id);
        int type = (int) (Math.random()*2);
        testData.put(DbContract.PredefinedRemindersEntry.COLUMN_TYPE, type);
        return testData;
    }

    private static ContentValues createPredefChecklistContentValues(String items, int id) {
        ContentValues testData = new ContentValues();
        testData.put(DbContract.PredefinedChecklistEntry.COLUMN_ITEM_NAMES, items);
        return testData;
    }

}
