package com.example.android.householdroutine;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.TextView;

import com.example.android.householdroutine.data.DbContract;

public class TaskDetails extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final int ID_TASK_DETAILS_LOADER = 13;
    public static final int ID_TASK_DETAILS_CHECKLIST_LOADER = 14;

    private TextView mName;
    private TextView mDescription;
    private TextView mStartDate;
    private TextView mEndDate;
    private TextView mChecklistLabel;
    private RecyclerView mRecyclerView;
    private DetailsRecyclerViewAdapter mAdapter;

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
            DbContract.ChecklistEntry.COLUMN_COMPLETED
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
        mName = (TextView) findViewById(R.id.details_name);
        mDescription = (TextView) findViewById(R.id.details_description);
        mStartDate = (TextView) findViewById(R.id.details_start_date);
        mEndDate = (TextView) findViewById(R.id.details_end_date);

        mName.setText(cursor.getString(INDEX_NAME));
        mDescription.setText(cursor.getString(INDEX_DESCRIPTION));
        long startDate = cursor.getLong(INDEX_START_DATE);
        int flags = DateUtils.FORMAT_SHOW_DATE
                | DateUtils.FORMAT_NUMERIC_DATE
                | DateUtils.FORMAT_SHOW_YEAR
                | DateUtils.FORMAT_SHOW_TIME;
        String startDateString = DateUtils.formatDateTime(this, startDate, flags);
        mStartDate.setText(startDateString);
        long endDate = cursor.getLong(INDEX_END_DATE);
        String endDateString = DateUtils.formatDateTime(this, endDate, flags);
        mEndDate.setText(endDateString);

    }

    /**
     * Initializes the checklist ui with the cursor data
     *
     * @param cursor
     */
    private void buildChecklistUi(Cursor cursor) {


        mRecyclerView = (RecyclerView) findViewById(R.id.details_recycler_view);
        mChecklistLabel = (TextView) findViewById(R.id.details_checklist_label);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        mAdapter = new DetailsRecyclerViewAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.swapCursor(cursor);

        mRecyclerView.setVisibility(View.VISIBLE);
        mChecklistLabel.setVisibility(View.VISIBLE);
    }

    // TODO menü mit löschen, als erledigt markieren und vielleicht bearbeiten

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
