package com.example.android.householdroutine;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ConstraintLayout mRemindersCompleted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_main);
        mRemindersCompleted = (ConstraintLayout) findViewById(R.id.reminders_completed);

        hideRecyclerView();

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

        if(id == R.id.action_settings) {
            // TODO
        }

        return super.onOptionsItemSelected(item);
    }
}
