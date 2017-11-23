package com.example.android.householdroutine;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;

import com.example.android.householdroutine.data.DbContract;

public class TaskDetails extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final int ID_TASK_DETAILS_LOADER = 13;
    public static final int ID_TASK_DETAILS_CHECKLIST_LOADER = 14;

    private long reminderId = -1;

    // Reminder projection
    public static final String[] DETAILS_PROJECTION = {
            DbContract.RemindersEntry.COLUMN_NAME,
            DbContract.RemindersEntry.COLUMN_DESCRIPTION,
            DbContract.RemindersEntry.COLUMN_START_DATE,
            DbContract.RemindersEntry.COLUMN_END_DATE,
            DbContract.RemindersEntry.COLUMN_OUTDATED,
            DbContract.RemindersEntry.COLUMN_TYPE};

    // Index values for the reminder details
    public static final int INDEX_NAME = 0;
    public static final int INDEX_DESCRIPTION = 1;
    public static final int INDEX_START_DATE = 2;
    public static final int INDEX_END_DATE = 3;
    public static final int INDEX_OUTDATED = 4;
    public static final int INDEX_TYPE = 5;

    // Checklist projection
    public static final String[] CHECKLIST_PROJECTION = {
            DbContract.ChecklistEntry._ID,
            DbContract.ChecklistEntry.COLUMN_ITEM_NAME,
            DbContract.ChecklistEntry.COLUMN_COMPLETED_ID
    };

    // Index values for the checklist items
    public static final int CHECKLIST_INDEX_ID = 0;
    public static final int CHECKLIST_INDEX_ITEM_NAME = 1;
    public static final int CHECKLIST_INDEX_COMPLETED = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

        if (getIntent().hasExtra(MainActivity.EXTRA_REMINDER_ID)) {
            reminderId = getIntent().getLongExtra(MainActivity.EXTRA_REMINDER_ID, -1);
        }
        if (reminderId == -1)
            finish();

        getSupportLoaderManager().initLoader(ID_TASK_DETAILS_LOADER, null, this);
    }


    /**
     * Initializes the ui with the cursor data
     *
     * @param cursor
     */
    private void buildUi(Cursor cursor) {

    }

    /**
     * Initializes the checklist ui with the cursor data
     * @param cursor
     */
    private void buildChecklistUi(Cursor cursor) {

    }

    /**
     * Gets automatically started by a background thread to load all reminders, that are not outdated, from the database
     *
     * @param loaderId
     * @param bundle
     * @return
     */
    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle bundle) {

        switch (loaderId) {
            case ID_TASK_DETAILS_LOADER:
                Uri contentUri = DbContract.RemindersEntry.buildRemindersUriWithId(reminderId);

                return new CursorLoader(this,
                        contentUri,
                        DETAILS_PROJECTION,
                        null,
                        null,
                        null);
            case ID_TASK_DETAILS_CHECKLIST_LOADER:
                Uri singleChecklistUri = DbContract.ChecklistEntry.buildChecklistUriWithReminderId(reminderId);
                return new CursorLoader(this,
                        singleChecklistUri,
                        CHECKLIST_PROJECTION,
                        null,
                        null,
                        null);

            default:
                throw new RuntimeException("Unknown loader id: " + loaderId);
        }
    }

    /**
     * Will be executed, when the loader has finished loading its data.
     * Swaps the RecyclerView cursor and scrolls to the top of the list
     *
     * @param loader
     * @param data
     */
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()) {
            case ID_TASK_DETAILS_LOADER:
                data.moveToFirst();
                if (data.getInt(INDEX_TYPE) == 1) {
                    getSupportLoaderManager().initLoader(ID_TASK_DETAILS_CHECKLIST_LOADER, null, this);
                }
                buildUi(data);
                break;
            case ID_TASK_DETAILS_CHECKLIST_LOADER:
                data.moveToFirst();
                buildChecklistUi(data);
                break;
        }
    }

    /**
     * Automatically executed, when a loader is got reset. Makes the data invalid.
     *
     * @param loader
     */
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
