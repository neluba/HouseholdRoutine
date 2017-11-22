package com.example.android.householdroutine;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.android.householdroutine.data.DbContract;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CreateTask extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final int ID_PREDEFINED_REMINDERS_LOADER = 14;
    public static final int ID_PREDEFINED_CHECKLIST_LOADER = 15;

    // Tag in the EditText View name, that saves a long value for the predefined checklist id
    public static final int TAG_PREDEFINED_CHECKLIST_ID = 13;

    private RecyclerView mPredefinedRemindersRecyclerView;
    private PredefinedRemindersRecyclerViewAdapter mPredefinedRemindersAdapter;
    private int mPredefinedRemindersPosition = RecyclerView.NO_POSITION;
    private RecyclerView mPredefinedChecklistRecyclerView;
    private PredefinedChecklistRecyclerViewAdapter mPredefinedChecklistAdapter;
    private int mPredefinedChecklistPosition = RecyclerView.NO_POSITION;

    private EditText name;
    private EditText description;
    private EditText endDate;
    private EditText endTime;
    private RadioButton reminder;
    private RadioButton checklist;
    private ConstraintLayout checklistView;
    private RadioButton checklistTypePredefined;
    private RadioButton checklistTypeOwn;
    private LinearLayout ownChecklistView;
    private EditText ownChecklistAddItemName;

    private Calendar calendar = Calendar.getInstance();
    private ArrayList<String> checklistItems;

    private static final String CHECKLIST_ITEMS_KEY = "items";
    private static final String CALENDAR_TIME_KEY = "cal_time";
    private static final String REMINDER_CHECKED_KEY = "reminder_checked";
    private static final String CHECKLIST_CHECKED_KEY = "checklist_checked";
    private static final String CHECKLIST_PREDEFINED_CHECKED_KEY = "cl_predefined_checked";
    private static final String CHECKLIST_OWN_CHECKED_KEY = "cl_own_checked";

    // Columns to use for the predefined reminders recycler view
    public static final String[] PREDEFINED_REMINDERS_PROJECTION = {
            DbContract.PredefinedRemindersEntry.COLUMN_NAME,
            DbContract.PredefinedRemindersEntry.COLUMN_DESCRIPTION};

    // Index values for the predefined reminders columns
    public static final int PREDEF_REMINDERS_INDEX_NAME = 0;
    public static final int PREDEF_REMINDERS_INDEX_DESCRIPTION = 1;

    public static final String[] PREDEFINED_CHECKLIST_PROJECTION = {
            DbContract.PredefinedChecklistEntry._ID,
            DbContract.PredefinedRemindersEntry.COLUMN_NAME,
            DbContract.PredefinedRemindersEntry.COLUMN_DESCRIPTION,
            DbContract.PredefinedChecklistEntry.COLUMN_ITEM_NAMES};

    // Index values for the predefined checklist columns
    public static final int PREDEF_CHECKLIST_INDEX_ID = 0;
    public static final int PREDEF_CHECKLIST_INDEX_NAME = 1;
    public static final int PREDEF_CHECKLIST_INDEX_DESCRIPTION = 2;
    public static final int PREDEF_CHECKLIST_INDEX_ITEM_NAME = 3;

    // TODO alles am ende in die db schreiben und zurück in die main activity
    // TODO Nach dem man einen eigenen checklisten eintrag hinzugefügt hat, soll die tastatur runter
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        name = (EditText) findViewById(R.id.new_task_name);
        description = (EditText) findViewById(R.id.new_task_description);
        endDate = (EditText) findViewById(R.id.new_task_end_date);
        endTime = (EditText) findViewById(R.id.new_task_end_time);
        reminder = (RadioButton) findViewById(R.id.new_task_type_button1);
        checklist = (RadioButton) findViewById(R.id.new_task_type_button2);
        checklistView = (ConstraintLayout) findViewById(R.id.new_task_checklist_view);
        checklistTypePredefined = (RadioButton) findViewById(R.id.new_task_checklist_type_button1);
        checklistTypeOwn = (RadioButton) findViewById(R.id.new_task_checklist_type_button2);
        ownChecklistView = (LinearLayout) findViewById(R.id.new_task_checklist_type_own);
        ownChecklistAddItemName = (EditText) findViewById(R.id.own_checklist_add_item_name);
        mPredefinedRemindersRecyclerView = (RecyclerView) findViewById(R.id.new_task_predefined_reminders);
        mPredefinedChecklistRecyclerView = (RecyclerView) findViewById(R.id.new_task_predefined_checklist);

        // initialize predefined reminder recycler view
        LinearLayoutManager layoutManagerReminders =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mPredefinedRemindersRecyclerView.setLayoutManager(layoutManagerReminders);
        mPredefinedRemindersRecyclerView.setHasFixedSize(true);
        mPredefinedRemindersAdapter = new PredefinedRemindersRecyclerViewAdapter(this, name, description);
        mPredefinedRemindersRecyclerView.setAdapter(mPredefinedRemindersAdapter);

        // initialize predefined checklist recycler view
        LinearLayoutManager layoutManagerChecklist =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mPredefinedChecklistRecyclerView.setLayoutManager(layoutManagerChecklist);
        mPredefinedChecklistRecyclerView.setHasFixedSize(true);
        mPredefinedChecklistAdapter = new PredefinedChecklistRecyclerViewAdapter(this, name, description);
        mPredefinedChecklistRecyclerView.setAdapter(mPredefinedChecklistAdapter);


        // Restore layout state after rotation
        if (savedInstanceState != null)
            restoreDataAfterRotation(savedInstanceState);
        else
            checklistItems = new ArrayList<String>();

        // end date date picker
        // source: https://stackoverflow.com/questions/14933330/datepicker-how-to-popup-datepicker-when-click-on-edittext
        endDate.setKeyListener(null);
        final DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);
                updateEndDate();
            }
        };

        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog picker = new DatePickerDialog(CreateTask.this,
                        dateListener,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                picker.getDatePicker().setMinDate(System.currentTimeMillis());
                picker.show();
            }
        });

        // end time time picker
        endTime.setKeyListener(null);
        final TimePickerDialog.OnTimeSetListener timeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);
                updateEndTime();
            }
        };

        final boolean hourFormat = android.text.format.DateFormat.is24HourFormat(getApplicationContext());
        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerDialog(CreateTask.this,
                        timeListener,
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        hourFormat
                ).show();
            }
        });

        // task type radio buttons
        reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideChecklistView();
            }
        });
        checklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChecklistView();
            }
        });


        // checklist type radio buttons
        checklistTypePredefined.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideOwnChecklistView();
            }
        });
        checklistTypeOwn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showOwnChecklistView();
            }
        });


        // initialize loader
        getSupportLoaderManager().initLoader(ID_PREDEFINED_REMINDERS_LOADER, null, this);
        getSupportLoaderManager().initLoader(ID_PREDEFINED_CHECKLIST_LOADER, null, this);
    }

    /**
     * Save state for screen rotation
     *
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (checklistItems != null)
            outState.putStringArrayList(CHECKLIST_ITEMS_KEY, checklistItems);

        outState.putLong(CALENDAR_TIME_KEY, calendar.getTimeInMillis());

        // radio boxes
        outState.putBoolean(REMINDER_CHECKED_KEY, reminder.isChecked());
        outState.putBoolean(CHECKLIST_CHECKED_KEY, checklist.isChecked());
        outState.putBoolean(CHECKLIST_PREDEFINED_CHECKED_KEY, checklistTypePredefined.isChecked());
        outState.putBoolean(CHECKLIST_OWN_CHECKED_KEY, checklistTypeOwn.isChecked());

    }

    /**
     * Restores the complete layout after a screen rotation
     *
     * @param savedInstanceState
     */
    private void restoreDataAfterRotation(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey(CHECKLIST_ITEMS_KEY))
            checklistItems = savedInstanceState.getStringArrayList(CHECKLIST_ITEMS_KEY);
        else
            checklistItems = new ArrayList<String>();

        if (savedInstanceState.containsKey(CALENDAR_TIME_KEY))
            calendar.setTimeInMillis(savedInstanceState.getLong(CALENDAR_TIME_KEY));

        // radio boxes
        if (savedInstanceState.containsKey(REMINDER_CHECKED_KEY)) {
            if (savedInstanceState.getBoolean(REMINDER_CHECKED_KEY)) {
                hideChecklistView();
            }
        }
        if (savedInstanceState.containsKey(CHECKLIST_CHECKED_KEY)) {
            if (savedInstanceState.getBoolean(CHECKLIST_CHECKED_KEY)) {
                showChecklistView();
            }
        }
        if (savedInstanceState.containsKey(CHECKLIST_PREDEFINED_CHECKED_KEY)) {
            if (savedInstanceState.getBoolean(CHECKLIST_PREDEFINED_CHECKED_KEY)) {
                // predefined checklist view anzeigen
            }
        }
        if (savedInstanceState.containsKey(CHECKLIST_OWN_CHECKED_KEY)) {
            if (savedInstanceState.getBoolean(CHECKLIST_OWN_CHECKED_KEY)) {
                showOwnChecklistView();
            }
        }
        // restore own checklist
        if (checklistItems != null && checklistItems.size() > 0) {
            LayoutInflater inflater = (LayoutInflater)
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            for (String item : checklistItems) {
                final View checklistItemView = inflater
                        .inflate(R.layout.create_task_checklist_item, null);

                TextView itemTextView = (TextView) checklistItemView
                        .findViewById(R.id.own_checklist_item_name);
                itemTextView.setText(item);
                ownChecklistView.addView(checklistItemView);
            }
        }


    }

    // create menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.create_task, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.save_task) {
            // Todo methode schreiben, mit der die aufgabe erstellt wird und es zurück zur main activity geht. Wenn ein feld nicht ausgefüllt wurde, kommt ein neuer Toast
            Toast.makeText(CreateTask.this, "Not ready yet", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }


    private void showChecklistView() {
        checklistView.setVisibility(View.VISIBLE);
        mPredefinedRemindersRecyclerView.setVisibility(View.INVISIBLE);
    }

    private void hideChecklistView() {
        checklistView.setVisibility(View.INVISIBLE);
        mPredefinedRemindersRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showOwnChecklistView() {
        ownChecklistView.setVisibility(View.VISIBLE);
        mPredefinedChecklistRecyclerView.setVisibility(View.INVISIBLE);
    }

    private void hideOwnChecklistView() {
        ownChecklistView.setVisibility(View.INVISIBLE);
        mPredefinedChecklistRecyclerView.setVisibility(View.VISIBLE);
    }

    /**
     * Updates the endDate view with the picked date
     */
    private void updateEndDate() {
        Date date = new Date(calendar.getTimeInMillis());
        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());
        endDate.setText(dateFormat.format(date));
    }

    /**
     * Updates the endTime view with the picked time
     */
    private void updateEndTime() {
        Date date = new Date(calendar.getTimeInMillis());
        DateFormat dateFormat = android.text.format.DateFormat.getTimeFormat(getApplicationContext());
        endTime.setText(dateFormat.format(date));
    }

    /**
     * Adds own items to the linear layout for the checklist items
     *
     * @param v
     */
    public void addItem(View v) {
        String name = ownChecklistAddItemName.getText().toString();
        if (!TextUtils.isEmpty(name)) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View checklistItemView = inflater.inflate(R.layout.create_task_checklist_item, null);

            TextView itemTextView = (TextView) checklistItemView.findViewById(R.id.own_checklist_item_name);
            itemTextView.setText(name);
            ownChecklistView.addView(checklistItemView);

            checklistItems.add(name);

            ownChecklistAddItemName.setText("");
        }
    }

    /**
     * Removes an item from the linear view for checklist items
     *
     * @param v
     */
    public void removeItem(View v) {
        View parent = (View) v.getParent();
        TextView itemTextView = (TextView) parent.findViewById(R.id.own_checklist_item_name);
        String name = itemTextView.getText().toString();

        for (int i = 0; i < checklistItems.size(); i++) {
            if (checklistItems.get(i).equals(name)) {
                checklistItems.remove(i);
                break;
            }
        }

        ownChecklistView.removeView((View) v.getParent());
    }


    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle bundle) {
        Uri contentUri;
        switch (loaderId) {
            case ID_PREDEFINED_REMINDERS_LOADER:
                contentUri = DbContract.PredefinedRemindersEntry.CONTENT_URI;

                return new CursorLoader(this,
                        contentUri,
                        PREDEFINED_REMINDERS_PROJECTION,
                        null,
                        null,
                        null);
            case ID_PREDEFINED_CHECKLIST_LOADER:
                contentUri = DbContract.PredefinedChecklistEntry.
                        FULL_PREDEFINED_CHECKLIST_CONTENT_URI;
                return new CursorLoader(this,
                        contentUri,
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
            case ID_PREDEFINED_REMINDERS_LOADER:
                mPredefinedRemindersAdapter.swapCursor(data);
                if (mPredefinedRemindersPosition == RecyclerView.NO_POSITION)
                    mPredefinedRemindersPosition = 0;
                mPredefinedRemindersRecyclerView.smoothScrollToPosition(mPredefinedRemindersPosition);
                break;
            case ID_PREDEFINED_CHECKLIST_LOADER:
                mPredefinedChecklistAdapter.swapCursor(data);
                if(mPredefinedChecklistPosition == RecyclerView.NO_POSITION)
                    mPredefinedChecklistPosition = 0;
                mPredefinedChecklistRecyclerView.smoothScrollToPosition(mPredefinedChecklistPosition);
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
        switch (loader.getId()) {
            case ID_PREDEFINED_REMINDERS_LOADER:
                mPredefinedRemindersAdapter.swapCursor(null);
                break;
            case ID_PREDEFINED_CHECKLIST_LOADER:
                mPredefinedChecklistAdapter.swapCursor(null);
                break;
        }
    }
}
