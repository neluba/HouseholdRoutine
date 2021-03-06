package com.example.android.householdroutine.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.householdroutine.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by oliver on 06.10.2017.
 *
 */

public class DbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "app.db";
    public static final int DATABASE_VERSION = 23;
    private Context context;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    /**
     * // Load sql file from resources
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        executeSql(sqLiteDatabase, R.raw.main);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if(oldVersion < 21) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DbContract.RemindersEntry.TABLE_NAME);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DbContract.ChecklistEntry.TABLE_NAME);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DbContract.PredefinedRemindersEntry.TABLE_NAME);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DbContract.PredefinedRemindersEntry.TABLE_NAME_DE);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DbContract.PredefinedChecklistEntry.TABLE_NAME);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DbContract.PredefinedChecklistEntry.TABLE_NAME_DE);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DbContract.InformationsEntry.TABLE_NAME);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DbContract.InformationsEntry.TABLE_NAME_DE);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DbContract.InformationSetsEntry.TABLE_NAME);
            // create a new database
            onCreate(sqLiteDatabase);
            return;
        }
        // update 1
        if (oldVersion < 23) {
            executeSql(sqLiteDatabase, R.raw.update1);
        }
    }

    private void executeSql(SQLiteDatabase sqLiteDatabase, int sqlResourceId) {
        InputStream inputStream = context.getResources().openRawResource(sqlResourceId);
        BufferedReader reader = null;
        StringBuilder stringBuilder = new StringBuilder();
        String queries = "";
        try {
            reader = new BufferedReader(new InputStreamReader(inputStream));
            while((queries = reader.readLine()) != null) {
                stringBuilder.append(queries);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        queries = stringBuilder.toString();
        for (String query : queries.split(";")) {
            sqLiteDatabase.execSQL(query);
        }
    }
}
