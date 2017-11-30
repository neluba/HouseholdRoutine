package com.example.android.householdroutine;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.example.android.householdroutine.data.DbContract;

public class Extras extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final int ID_INFORMATIONS_LOADER = 13;
    public static final int ID_POINTS_LOADER = 14;
    public static final int ID_REMINDERS_LOADER = 15;

    // index values for the informations cursor
    public static final int INDEX_ID = 0;
    public static final int INDEX_NAME = 1;
    public static final int INDEX_DESCRIPTION = 2;
    public static final int INDEX_URL = 3;
    public static final int INDEX_SOURCE = 4;
    // index values for the user points cursor
    public static final int INDEX_USER_POINTS = 0;
    // index values for the reminders cursor
    public static final int INDEX_REMINDERS = 0;

    private TextView points;
    private TextView reminders;
    private RecyclerView mRecyclerView;
    private ExtrasRecyclerViewAdapter mAdapter;
    private ConstraintLayout mNoInformationConstraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extras);

        points = (TextView) findViewById(R.id.extras_points);
        reminders = (TextView) findViewById(R.id.extras_reminders);
        mRecyclerView = (RecyclerView) findViewById(R.id.extras_recycler_view);
        mNoInformationConstraintLayout = (ConstraintLayout) findViewById(R.id.extras_no_information);

        // initialize recyclerview
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        mAdapter = new ExtrasRecyclerViewAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        // start the cursor loaders
        getSupportLoaderManager().initLoader(ID_INFORMATIONS_LOADER, null, this);
        getSupportLoaderManager().initLoader(ID_POINTS_LOADER, null, this);
        getSupportLoaderManager().initLoader(ID_REMINDERS_LOADER, null, this);
    }

    /**
     * Updates the points text view to show the correct value
     */
    private void fillPoints(Cursor data) {
        String userPoints;
        if (data.getCount() == 0)
            userPoints = "0";
        else
            userPoints = data.getString(INDEX_USER_POINTS);

        if(TextUtils.isEmpty(userPoints))
            userPoints = "0";

        points.setText(userPoints);
    }

    /**
     * Updates the completed reminders text view to show the correct values
     */
    private void fillReminders(Cursor data) {
        String remindersCount;
        if(data.getCount() == 0)
            remindersCount = "0";
        else
            remindersCount = data.getString(INDEX_REMINDERS);

        if(TextUtils.isEmpty(remindersCount))
            remindersCount = "0";

        reminders.setText(remindersCount);
    }

    /**
     * Shows the recyclerview and hides that "no information" message
     */
    private void showRecyclerView() {
        mRecyclerView.setVisibility(View.VISIBLE);
        mNoInformationConstraintLayout.setVisibility(View.INVISIBLE);
    }

    /**
     * Shows the "no information" message and hides the recyclerview
     */
    private void hideRecyclerView() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mNoInformationConstraintLayout.setVisibility(View.VISIBLE);
    }

    /**
     * Gets automatically started by a background thread to load all informations and user points
     *
     * @param loaderId
     * @param bundle
     * @return
     */
    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle bundle) {

        switch (loaderId) {
            case ID_INFORMATIONS_LOADER:
                Uri contentUri = DbContract.InformationSetsEntry.FULL_INFORMATION_CONTENT_URI;

                return new CursorLoader(this,
                        contentUri,
                        null,
                        null,
                        null,
                        null);
            case ID_POINTS_LOADER:
                Uri pointsUri = DbContract.UserPointsEntry.USER_POINTS_SUM_CONTENT_URI;
                return new CursorLoader(this,
                        pointsUri,
                        null,
                        null,
                        null,
                        null);
            case ID_REMINDERS_LOADER:
                Uri remindersUri = DbContract.UserPointsEntry.USER_POINTS_REMINDERS_COUNT_CONTENT_URI;
                return new CursorLoader(this,
                        remindersUri,
                        null,
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
            case ID_INFORMATIONS_LOADER:
                if (data.getCount() > 0) {
                    data.moveToFirst();
                    mAdapter.swapCursor(data);
                    showRecyclerView();
                } else
                    hideRecyclerView();
                break;
            case ID_POINTS_LOADER:
                data.moveToFirst();
                fillPoints(data);
                break;
            case ID_REMINDERS_LOADER:
                data.moveToFirst();
                fillReminders(data);
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
        mAdapter.swapCursor(null);
    }
}
