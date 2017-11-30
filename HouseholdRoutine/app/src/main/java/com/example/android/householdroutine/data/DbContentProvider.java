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

import java.util.Locale;

/**
 * Created by oliver on 06.10.2017.
 * This content provider implementation is close to the one that I learned on
 * https://www.udacity.com/course/new-android-fundamentals--ud851
 */

public class DbContentProvider extends ContentProvider {

    public static final int CODE_REMINDERS = 100;
    public static final int CODE_REMINDERS_WITH_ID = 101;
    public static final int CODE_CHECKLIST = 200;
    public static final int CODE_CHECKLIST_WITH_ID = 201;
    public static final int CODE_CHECKLIST_WITH_REMINDER_ID = 202;
    public static final int CODE_PREDEFINED_REMINDERS = 300;
    public static final int CODE_PREDEFINED_REMINDERS_WITH_ID = 301;
    public static final int CODE_PREDEFINED_CHECKLIST = 400;
    public static final int CODE_PREDEFINED_CHECKLIST_WITH_ID = 401;
    public static final int CODE_FULL_PREDEFINED_CHECKLIST = 500;
    public static final int CODE_USER_POINTS = 600;
    public static final int CODE_USER_POINTS_SUM = 601;
    public static final int CODE_USER_POINTS_REMINDERS_COUNT = 602;
    public static final int CODE_INFORMATION_SETS = 700;
    public static final int CODE_INFORMATION_SETS_WITH_ID = 701;
    public static final int CODE_FULL_INFORMATION = 702;

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
        // URI : content://CONTENT_AUTHORITY/checklist/ */
        matcher.addURI(authority, DbContract.PATH_CHECKLIST + "/#", CODE_CHECKLIST_WITH_ID);
        // URI : content://CONTENT_AUTHORITY/single_checklist/ */
        matcher.addURI(authority, DbContract.PATH_SINGLE_CHECKLIST + "/#", CODE_CHECKLIST_WITH_REMINDER_ID);
        // URI : content://CONTENT_AUTHORITY/predefined_reminders
        matcher.addURI(authority, DbContract.PATH_PREDEFINED_REMINDERS, CODE_PREDEFINED_REMINDERS);
        // URI : content://CONTENT_AUTHORITY/predefined_reminders/ */
        matcher.addURI(authority, DbContract.PATH_PREDEFINED_REMINDERS + "/#/", CODE_PREDEFINED_REMINDERS_WITH_ID);
        // URI : content//CONTENT_AUTHORITY/predefined_checklist
        matcher.addURI(authority, DbContract.PATH_PREDEFINED_CHECKLIST, CODE_PREDEFINED_CHECKLIST);
        // URI : content://CONTENT_AUTHORITY/predefined_checklist/ */
        matcher.addURI(authority, DbContract.PATH_PREDEFINED_CHECKLIST + "/#", CODE_PREDEFINED_CHECKLIST_WITH_ID);
        // URI : content://CONTENT_AUTHORITY/full_predefined_checklist
        matcher.addURI(authority, DbContract.PATH_FULL_PREDEFINED_CHECKLIST, CODE_FULL_PREDEFINED_CHECKLIST);
        // URI : content://CONTENT_AUTHORITY/user_points
        matcher.addURI(authority, DbContract.PATH_USER_POINTS, CODE_USER_POINTS);
        // URI : content://CONTENT_AUTHORITY/user_points_sum
        matcher.addURI(authority, DbContract.PATH_USER_POINTS_SUM, CODE_USER_POINTS_SUM);
        // URI : content://CONTENT_AUTHORITY/user_points_reminders_count
        matcher.addURI(authority, DbContract.PATH_USER_POINTS_REMINDERS_COUNT, CODE_USER_POINTS_REMINDERS_COUNT);
        // URI : content://CONTENT_AUTHORITY/information_sets
        matcher.addURI(authority, DbContract.PATH_INFORMATION_SETS, CODE_INFORMATION_SETS);
        // URI : content://CONTENT_AUTHORITY/information_sets/ */
        matcher.addURI(authority, DbContract.PATH_INFORMATION_SETS + "/#/", CODE_INFORMATION_SETS_WITH_ID);
        // URI : URI : content://CONTENT_AUTHORITY/full_information
        matcher.addURI(authority, DbContract.PATH_FULL_INFORMATION, CODE_FULL_INFORMATION);


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
                        if (_id != -1) {
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
                        if (_id != -1) {
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
            case CODE_PREDEFINED_REMINDERS:
                String predefinedRemindersTableName;
                switch (Locale.getDefault().getLanguage()) {
                    case "de":
                        predefinedRemindersTableName = DbContract.PredefinedRemindersEntry.TABLE_NAME_DE;
                        break;
                    default:
                        predefinedRemindersTableName = DbContract.PredefinedRemindersEntry.TABLE_NAME;
                }
                db.beginTransaction();
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(predefinedRemindersTableName, null, value);
                        if (_id != -1) {
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
            case CODE_PREDEFINED_CHECKLIST:
                String predefinedChecklistTableName;
                switch (Locale.getDefault().getLanguage()) {
                    case "de":
                        predefinedChecklistTableName = DbContract.PredefinedChecklistEntry.TABLE_NAME_DE;
                        break;
                    default:
                        predefinedChecklistTableName = DbContract.PredefinedChecklistEntry.TABLE_NAME;
                }
                db.beginTransaction();
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(predefinedChecklistTableName, null, value);
                        if (_id != -1) {
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
                String reminder_id = uri.getLastPathSegment();
                String[] reminder_selectionArg = new String[]{reminder_id};
                cursor = mDbOpenHelper.getReadableDatabase().query(
                        DbContract.RemindersEntry.TABLE_NAME,
                        projection,
                        DbContract.RemindersEntry._ID + "=?",
                        reminder_selectionArg,
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

            case CODE_CHECKLIST_WITH_ID:
                String checklist_id = uri.getLastPathSegment();
                String[] checklist_selectionArg = new String[]{checklist_id};
                cursor = mDbOpenHelper.getReadableDatabase().query(
                        DbContract.ChecklistEntry.TABLE_NAME,
                        projection,
                        DbContract.ChecklistEntry._ID + "=?",
                        checklist_selectionArg,
                        null,
                        null,
                        sortOrder);
                break;
            case CODE_CHECKLIST_WITH_REMINDER_ID:
                String single_checklist_id = uri.getLastPathSegment();
                String[] single_checklist_selectionArg = new String[]{single_checklist_id};
                cursor = mDbOpenHelper.getReadableDatabase().query(
                        DbContract.ChecklistEntry.TABLE_NAME,
                        projection,
                        DbContract.ChecklistEntry.COLUMN_REMINDER_ID + "=?",
                        single_checklist_selectionArg,
                        null,
                        null,
                        sortOrder);
                break;

            case CODE_PREDEFINED_REMINDERS:
                String predefinedRemindersTableName;
                switch (Locale.getDefault().getLanguage()) {
                    case "de":
                        predefinedRemindersTableName = DbContract.PredefinedRemindersEntry.TABLE_NAME_DE;
                        break;
                    default:
                        predefinedRemindersTableName = DbContract.PredefinedRemindersEntry.TABLE_NAME;
                }
                cursor = mDbOpenHelper.getReadableDatabase().query(
                        predefinedRemindersTableName,
                        projection,
                        DbContract.PredefinedRemindersEntry.COLUMN_TYPE + "=0",
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case CODE_PREDEFINED_REMINDERS_WITH_ID:
                String predefinedRemindersIdTableName;
                switch (Locale.getDefault().getLanguage()) {
                    case "de":
                        predefinedRemindersIdTableName = DbContract.PredefinedRemindersEntry.TABLE_NAME_DE;
                        break;
                    default:
                        predefinedRemindersIdTableName = DbContract.PredefinedRemindersEntry.TABLE_NAME;
                }
                String predefined_reminder_id = uri.getLastPathSegment();
                String[] predefined_reminder_selectionArg = new String[]{predefined_reminder_id};
                cursor = mDbOpenHelper.getReadableDatabase().query(
                        predefinedRemindersIdTableName,
                        projection,
                        DbContract.PredefinedRemindersEntry._ID + "=? AND " +
                                DbContract.PredefinedRemindersEntry.COLUMN_TYPE + "=0",
                        predefined_reminder_selectionArg,
                        null,
                        null,
                        sortOrder);
                break;

            case CODE_PREDEFINED_CHECKLIST:
                String predefinedChecklistTableName;
                switch (Locale.getDefault().getLanguage()) {
                    case "de":
                        predefinedChecklistTableName = DbContract.PredefinedChecklistEntry.TABLE_NAME_DE;
                        break;
                    default:
                        predefinedChecklistTableName = DbContract.PredefinedChecklistEntry.TABLE_NAME;
                }
                cursor = mDbOpenHelper.getReadableDatabase().query(
                        predefinedChecklistTableName,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;

            case CODE_PREDEFINED_CHECKLIST_WITH_ID:
                String predefinedChecklistIdTableName;
                switch (Locale.getDefault().getLanguage()) {
                    case "de":
                        predefinedChecklistIdTableName = DbContract.PredefinedChecklistEntry.TABLE_NAME_DE;
                        break;
                    default:
                        predefinedChecklistIdTableName = DbContract.PredefinedChecklistEntry.TABLE_NAME;
                }
                String predefined_checklist_id = uri.getLastPathSegment();
                String[] predefined_checklist_selectionArg = new String[]{predefined_checklist_id};
                cursor = mDbOpenHelper.getReadableDatabase().query(
                        predefinedChecklistIdTableName,
                        projection,
                        DbContract.PredefinedChecklistEntry._ID + "=?",
                        predefined_checklist_selectionArg,
                        null,
                        null,
                        sortOrder);
                break;

            case CODE_FULL_PREDEFINED_CHECKLIST:
                String predefRemindersTable;
                String predefChecklistTable;
                switch (Locale.getDefault().getLanguage()) {
                    case "de":
                        predefRemindersTable = DbContract.PredefinedRemindersEntry.TABLE_NAME_DE;
                        predefChecklistTable = DbContract.PredefinedChecklistEntry.TABLE_NAME_DE;
                        break;
                    default:
                        predefRemindersTable = DbContract.PredefinedRemindersEntry.TABLE_NAME;
                        predefChecklistTable = DbContract.PredefinedChecklistEntry.TABLE_NAME;
                }

                String fullChecklistQuery = " select " + predefRemindersTable + "." + DbContract.PredefinedRemindersEntry._ID + ", " +
                        predefRemindersTable + "." + DbContract.PredefinedRemindersEntry.COLUMN_NAME + ", " +
                        predefRemindersTable + "." + DbContract.PredefinedRemindersEntry.COLUMN_DESCRIPTION + ", " +
                        predefChecklistTable + "." + DbContract.PredefinedChecklistEntry.COLUMN_ITEM_NAMES +
                        " from " + predefRemindersTable +
                        " inner join " + predefChecklistTable +
                        " on " + predefRemindersTable + "." + DbContract.PredefinedRemindersEntry._ID +
                        " = " + predefChecklistTable + "." + DbContract.PredefinedChecklistEntry._ID +
                        " where type = 1;";
                cursor = mDbOpenHelper.getReadableDatabase().rawQuery(fullChecklistQuery, null);
                break;

            case CODE_USER_POINTS_REMINDERS_COUNT:
                // select count(points) as reminder from user_points where type='reminder';
                String userPointsRemindersCountQuery = " select count(" +
                        DbContract.UserPointsEntry.COLUMN_POINTS + ") as " +
                        DbContract.UserPointsEntry.REMINDERS_COUNT + " from " +
                        DbContract.UserPointsEntry.TABLE_NAME + " where " +
                        DbContract.UserPointsEntry.COLUMN_TYPE + "='" +
                        DbContract.UserPointsEntry.TYPE_REMINDER + "';";
                cursor = mDbOpenHelper.getReadableDatabase().rawQuery(
                        userPointsRemindersCountQuery,
                        null);
                break;

            case CODE_USER_POINTS_SUM:
                // select SUM(points) as points from user_points
                String userPointsSumQuery = " select SUM(" +
                        DbContract.UserPointsEntry.COLUMN_POINTS + ") as " +
                        DbContract.UserPointsEntry.REWARDED_POINTS + " from " +
                        DbContract.UserPointsEntry.TABLE_NAME + ";";
                cursor = mDbOpenHelper.getReadableDatabase().rawQuery(
                        userPointsSumQuery,
                        null);
                break;

            case CODE_INFORMATION_SETS:
                cursor = mDbOpenHelper.getReadableDatabase().query(
                        DbContract.InformationSetsEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case CODE_FULL_INFORMATION:
                String informationSetsTable = DbContract.InformationSetsEntry.TABLE_NAME;
                String informationsTable;
                switch (Locale.getDefault().getLanguage()) {
                    case "de":
                        informationsTable = DbContract.InformationsEntry.TABLE_NAME_DE;
                        break;
                    default:
                        informationsTable = DbContract.InformationsEntry.TABLE_NAME;
                }
                // select information_sets._id, informations.name, informations.description,
                // information_sets.url, information_sets.source from information_sets inner join
                // informations ON information_sets.information_id = informations._id where
                // information_sets.obtained = 1;
                String fullInformationQuery = "select " +
                        informationSetsTable + "." + DbContract.InformationSetsEntry._ID + ", " +
                        informationsTable + "." + DbContract.InformationsEntry.COLUMN_NAME + ", " +
                        informationsTable + "." + DbContract.InformationsEntry.COLUMN_DESCRIPTION + ", " +
                        informationSetsTable + "." + DbContract.InformationSetsEntry.COLUMN_URL + ", " +
                        informationSetsTable + "." + DbContract.InformationSetsEntry.COLUMN_SOURCE +
                        " from " + informationSetsTable + " inner join " + informationsTable + " ON " +
                        informationSetsTable + "." + DbContract.InformationSetsEntry.COLUMN_INFORMATION_ID +
                        " = " + informationsTable + "." + DbContract.InformationsEntry._ID +
                        " where " + informationSetsTable + "." + DbContract.InformationSetsEntry.COLUMN_OBTAINED +
                        " = " + DbContract.InformationSetsEntry.OBTAINABLE_TRUE + ";";
                cursor = mDbOpenHelper.getReadableDatabase().rawQuery(
                        fullInformationQuery,
                        null);
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
                if (_id >= 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                    return DbContract.RemindersEntry.buildRemindersUriWithId(_id);
                } else
                    throw new SQLException("Insert error for uri: " + uri);
            case CODE_CHECKLIST:
                _id = db.insert(DbContract.ChecklistEntry.TABLE_NAME, null, contentValues);
                if (_id >= 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                    return DbContract.ChecklistEntry.buildChecklistUriWithId(_id);
                } else
                    throw new SQLException("Insert error for uri: " + uri);
            case CODE_USER_POINTS:
                _id = db.insert(DbContract.UserPointsEntry.TABLE_NAME, null, contentValues);
                if (_id >= 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                    return DbContract.UserPointsEntry.buildUriWithId(_id);
                } else {
                    throw new SQLException("Insert error for uri: " + uri);
                }
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

            case CODE_REMINDERS_WITH_ID:
                String reminderId = uri.getLastPathSegment();
                String[] reminderSelectionArg = new String[]{reminderId};
                deletedRows = mDbOpenHelper.getWritableDatabase().delete(
                        DbContract.RemindersEntry.TABLE_NAME,
                        DbContract.RemindersEntry._ID + "=? ",
                        reminderSelectionArg);
                break;

            case CODE_CHECKLIST:
                deletedRows = mDbOpenHelper.getWritableDatabase().delete(
                        DbContract.ChecklistEntry.TABLE_NAME,
                        selection,
                        selectionArgs);
                break;

            case CODE_CHECKLIST_WITH_REMINDER_ID:
                String checklistReminderId = uri.getLastPathSegment();
                String[] checklistReminderIdSelectionArg = new String[]{checklistReminderId};
                deletedRows = mDbOpenHelper.getWritableDatabase().delete(
                        DbContract.ChecklistEntry.TABLE_NAME,
                        DbContract.ChecklistEntry._ID + "=?",
                        checklistReminderIdSelectionArg);
                break;

            case CODE_PREDEFINED_REMINDERS:
                String predefinedRemindersTableName;
                switch (Locale.getDefault().getLanguage()) {
                    case "de":
                        predefinedRemindersTableName = DbContract.PredefinedRemindersEntry.TABLE_NAME_DE;
                        break;
                    default:
                        predefinedRemindersTableName = DbContract.PredefinedRemindersEntry.TABLE_NAME;
                }
                deletedRows = mDbOpenHelper.getWritableDatabase().delete(
                        predefinedRemindersTableName,
                        selection,
                        selectionArgs);
                break;

            case CODE_PREDEFINED_CHECKLIST:
                String predefinedChecklistTableName;
                switch (Locale.getDefault().getLanguage()) {
                    case "de":
                        predefinedChecklistTableName = DbContract.PredefinedChecklistEntry.TABLE_NAME_DE;
                        break;
                    default:
                        predefinedChecklistTableName = DbContract.PredefinedChecklistEntry.TABLE_NAME;
                }
                deletedRows = mDbOpenHelper.getWritableDatabase().delete(
                        predefinedChecklistTableName,
                        selection,
                        selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Unkown uri: " + uri);
        }
        if (deletedRows != 0)
            getContext().getContentResolver().notifyChange(uri, null);
        return deletedRows;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues,
                      @Nullable String selection, @Nullable String[] selectionArgs) {
        int match = sUriMatcher.match(uri);
        int updatedRows = 0;

        switch (match) {
            case CODE_CHECKLIST_WITH_ID:
                String checklist_id = uri.getLastPathSegment();
                String[] idSelectionArg = new String[]{checklist_id};

                updatedRows = mDbOpenHelper.getWritableDatabase().update(
                        DbContract.ChecklistEntry.TABLE_NAME,
                        contentValues,
                        DbContract.ChecklistEntry._ID + "=?",
                        idSelectionArg
                );
                break;
            case CODE_INFORMATION_SETS_WITH_ID:
                String information_id = uri.getLastPathSegment();
                String[] informationSelectionArg = new String[]{information_id};
                updatedRows = mDbOpenHelper.getWritableDatabase().update(
                        DbContract.InformationSetsEntry.TABLE_NAME,
                        contentValues,
                        DbContract.InformationSetsEntry._ID + "=?",
                        informationSelectionArg
                );
                break;
            default:
                throw new IllegalArgumentException("Unkown uri: " + uri);
        }
        if (updatedRows != 0)
            getContext().getContentResolver().notifyChange(uri, null);
        return updatedRows;
    }
}
