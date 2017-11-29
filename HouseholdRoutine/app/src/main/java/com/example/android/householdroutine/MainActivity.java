package com.example.android.householdroutine;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.android.householdroutine.data.DbContract;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    // TODO outdated reminders sollen ganz oben angezeigt werden und müssen besonders gekennzeichnet werden

    public static final int ID_MAINACTIVITY_LOADER = 13;
    public static final String EXTRA_REMINDER_ID = "reminder_id";

    // Recyclerview variables
    private RecyclerView mRecyclerView;
    private ConstraintLayout mRemindersCompleted;
    private MainRecyclerViewAdapter mAdapter;
    private int mPosition = RecyclerView.NO_POSITION;

    // Columns that will be used for showing data in the MainActivity
    public static final String[] MAIN_ACTIVITY_PROJECTION = {
            DbContract.RemindersEntry._ID,
            DbContract.RemindersEntry.COLUMN_NAME,
            DbContract.RemindersEntry.COLUMN_DESCRIPTION,
            DbContract.RemindersEntry.COLUMN_END_DATE,
            DbContract.RemindersEntry.COLUMN_TYPE};

    // Index values for the columns used for the recyclerview
    public static final int INDEX_ID = 0;
    public static final int INDEX_NAME = 1;
    public static final int INDEX_DESCRIPTION = 2;
    public static final int INDEX_END_DATE = 3;
    public static final int INDEX_TYPE = 4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_main);
        mRemindersCompleted = (ConstraintLayout) findViewById(R.id.reminders_completed);

        // initialize recyclerview
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new MainRecyclerViewAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        // TODO DEBUG ONLY - muss noch entfernt werden!!
        //FakeReminders.insertFakeData(this);

        // fab initialize
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addReminderIntent = new Intent(MainActivity.this, CreateTask.class);
                startActivity(addReminderIntent);
            }
        });

        // initialize the cursorLoader
        getSupportLoaderManager().initLoader(ID_MAINACTIVITY_LOADER, null, this);
    }

    /**
     * Hides the recyclerView and shows a message that no reminders are currently set
     */
    private void hideRecyclerView() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mRemindersCompleted.setVisibility(View.VISIBLE);
    }

    /**
     * Shows the recyclerView and hides the "no reminders set" message
     */
    private void showRecyclerView() {
        mRecyclerView.setVisibility(View.VISIBLE);
        mRemindersCompleted.setVisibility(View.INVISIBLE);
    }


    /**
     * Setting up the menu for the MainActivity
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    /**
     * On menu item select
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            // TODO menü fertig stellen
        }

        return super.onOptionsItemSelected(item);
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
            case ID_MAINACTIVITY_LOADER:
                Uri contentUri = DbContract.RemindersEntry.CONTENT_URI;
                String sortOrder = DbContract.RemindersEntry.COLUMN_END_DATE + " ASC";

                return new CursorLoader(this,
                        contentUri,
                        MAIN_ACTIVITY_PROJECTION,
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
        if (data.getCount() != 0) {
            mAdapter.swapCursor(data);
            if (mPosition == RecyclerView.NO_POSITION)
                mPosition = 0;
            mRecyclerView.smoothScrollToPosition(mPosition);
            showRecyclerView();
        } else
            hideRecyclerView();

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
