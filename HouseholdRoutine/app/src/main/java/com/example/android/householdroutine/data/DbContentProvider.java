package com.example.android.householdroutine.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by oliver on 06.10.2017.
 * This content provider implementation is close to the one that I learned on
 * https://www.udacity.com/course/new-android-fundamentals--ud851
 */

public class DbContentProvider extends ContentProvider {

    public static final int CODE_REMINDERS = 100;
    public static final int CODE_REMINDERS_WITH_ID = 101;
    public static final int CODE_CHECKLIST = 200;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private DbHelper mDbOpenHelper;

    /**
     * Returns a UriMatcher that matches each URI to to the table codes
     */
    public static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = DbContract.CONTENT_AUTHORITY;

        // URI : content://CONTENT_AUTHORITY/reminders
        matcher.addURI(authority, DbContract.PATH_REMINDERS, CODE_REMINDERS);
        // URI : content://CONTENT_AUTHORITY/reminders/ */
        matcher.addURI(authority, DbContract.PATH_REMINDERS + "/#", CODE_REMINDERS_WITH_ID);
        // URI : content//CONTENT_AUTHORITY/checklist
        matcher.addURI(authority, DbContract.PATH_CHECKLIST, CODE_CHECKLIST);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mDbOpenHelper = new DbHelper(getContext());
        return true;
    }

    /**
     * Inserts a set of content values into the database
     */
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        final SQLiteDatabase db = mDbOpenHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int rows = 0;
        switch (match) {
            case CODE_REMINDERS:
                db.beginTransaction();

                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(DbContract.RemindersEntry.TABLE_NAME, null, value);
                        if(_id != -1) {
                            rows++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }

                if (rows > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return rows;
            case CODE_CHECKLIST:
                db.beginTransaction();

                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(DbContract.ChecklistEntry.TABLE_NAME, null, value);
                        if(_id != -1) {
                            rows++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }

                if (rows > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return rows;

            default:
                return super.bulkInsert(uri, values);
        }
    }


    /**
     * Handles uri requests to return data from the database
     */
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case CODE_REMINDERS:
                cursor = mDbOpenHelper.getReadableDatabase().query(
                    DbContract.RemindersEntry.TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    sortOrder);
                break;
            case CODE_REMINDERS_WITH_ID:
                String id = uri.getLastPathSegment();
                String[] selectionArg = new String[]{id};
                cursor = mDbOpenHelper.getReadableDatabase().query(
                        DbContract.RemindersEntry.TABLE_NAME,
                        projection,
                        DbContract.RemindersEntry._ID + "=?",
                        selectionArg,
                        null,
                        null,
                        sortOrder);
                break;

            case CODE_CHECKLIST:
                cursor = mDbOpenHelper.getReadableDatabase().query(
                        DbContract.ChecklistEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;

            default:
                throw new UnsupportedOperationException("Unkown Uri: " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        // not needed right now
        return null;
    }


    /**
     * Insert a single line into the database
     */
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase db = mDbOpenHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        long _id = -1;
        switch (match) {
            case CODE_REMINDERS:
                _id = db.insert(DbContract.RemindersEntry.TABLE_NAME, null, contentValues);
                if(_id >= 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                    return DbContract.RemindersEntry.buildRemindersUriWithId(_id);
                } else
                    throw new SQLException("Insert error for uri: " + uri);
            case CODE_CHECKLIST:
                _id = db.insert(DbContract.ChecklistEntry.TABLE_NAME, null, contentValues);
                if(_id >= 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                    return DbContract.ChecklistEntry.buildChecklistUriWithId(_id);
                } else
                    throw new SQLException("Insert error for uri: " + uri);
            default:
                throw new IllegalArgumentException("Unkown uri: " + uri);
        }
    }

    /**
     * Deletes rows in the database for a given URI. Deletes the whole tables when selection is null.
     */
    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int match = sUriMatcher.match(uri);
        int deletedRows = 0;

        if (selection == null)
            selection = "1";

        switch (match) {
            case CODE_REMINDERS:
                deletedRows = mDbOpenHelper.getWritableDatabase().delete(
                        DbContract.RemindersEntry.TABLE_NAME,
                        selection,
                        selectionArgs);
                break;
            case CODE_CHECKLIST:
                deletedRows = mDbOpenHelper.getWritableDatabase().delete(
                        DbContract.ChecklistEntry.TABLE_NAME,
                        selection,
                        selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unkown uri: " + uri);
        }
        if(deletedRows != 0)
            getContext().getContentResolver().notifyChange(uri, null);
        return deletedRows;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues,
                      @Nullable String selection, @Nullable String[] selectionArgs) {
        // TODO update Methode schreiben
        return 0;
    }
}
