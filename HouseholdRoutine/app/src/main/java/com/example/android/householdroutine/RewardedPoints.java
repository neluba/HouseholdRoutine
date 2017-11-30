package com.example.android.householdroutine;

import android.database.Cursor;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.android.householdroutine.data.DbContract;

public class RewardedPoints extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final int ID_POINTS_LOADER = 13;

    // user points projection
    public static final String[] POINTS_PROJECTION = {
            DbContract.UserPointsEntry._ID,
            DbContract.UserPointsEntry.COLUMN_TITLE,
            DbContract.UserPointsEntry.COLUMN_DESCRIPTION,
            DbContract.UserPointsEntry.COLUMN_POINTS,
            DbContract.UserPointsEntry.COLUMN_DATE,
            DbContract.UserPointsEntry.COLUMN_TYPE};

    // user points index values for the cursor
    public static final int INDEX_ID = 0;
    public static final int INDEX_TITLE = 1;
    public static final int INDEX_DESCRIPTION = 2;
    public static final int INDEX_POINTS = 3;
    public static final int INDEX_DATE = 4;
    public static final int INDEX_TYPE = 5;

    private RecyclerView mRecyclerView;
    private RewardedPointsRecyclerViewAdapter mAdapter;
    private ConstraintLayout mNoRewardedPointsContraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewarded_points);

        mRecyclerView = (RecyclerView) findViewById(R.id.rewarded_points_recycler_view);
        mNoRewardedPointsContraintLayout = (ConstraintLayout) findViewById(R.id.no_rewarded_points);

        // initialize recyclerview
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(false);
        mAdapter = new RewardedPointsRecyclerViewAdapter(this);
        mRecyclerView.setAdapter(mAdapter);


        getSupportLoaderManager().initLoader(ID_POINTS_LOADER, null, this);
    }

    /**
     * Shows the recyclerview and hide the message, that the user hasn't earned any points yet
     */
    private void showRecyclerView() {
        mRecyclerView.setVisibility(View.VISIBLE);
        mNoRewardedPointsContraintLayout.setVisibility(View.INVISIBLE);
    }

    /**
     * Shows a message that the user hasn't earned any points yet and hides the recyclerview
     */
    private void hideRecyclerView() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mNoRewardedPointsContraintLayout.setVisibility(View.VISIBLE);
    }

    /**
     * Gets automatically started by a background thread to load the task details from the database
     *
     * @param loaderId
     * @param bundle
     * @return
     */
    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle bundle) {

        switch (loaderId) {
            case ID_POINTS_LOADER:
                Uri contentUri = DbContract.UserPointsEntry.CONTENT_URI;
                String sortOrder = DbContract.UserPointsEntry.COLUMN_DATE + " DESC";
                return new CursorLoader(this,
                        contentUri,
                        POINTS_PROJECTION,
                        null,
                        null,
                        sortOrder);
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
            case ID_POINTS_LOADER:
                if (data.getCount() > 0) {
                    mAdapter.swapCursor(data);
                    showRecyclerView();
                } else
                    hideRecyclerView();
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
