package com.example.android.householdroutine;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
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

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CreateTask extends AppCompatActivity {

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
    private static final String NAME_KEY = "name";
    private static final String DESCRIPTION_KEY = "description";
    private static final String END_DATE_KEY = "end_date";
    private static final String END_TIME_KEY = "end_time";
    private static final String CALENDAR_TIME_KEY = "cal_time";
    private static final String REMINDER_CHECKED_KEY = "reminder_checked";
    private static final String CHECKLIST_CHECKED_KEY = "checklist_checked";
    private static final String CHECKLIST_PREDEFINED_CHECKED_KEY = "cl_predefined_checked";
    private static final String CHECKLIST_OWN_CHECKED_KEY = "cl_own_checked";


    // Todo Es muss alles gespeichert bleiben, wenn das smartphone gedreht wird.
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
        String nameString = name.getText().toString();
        // first editText views
        if (!TextUtils.isEmpty(nameString) && nameString != null)
            outState.putString(NAME_KEY, nameString);
        String descriptionString = description.getText().toString();
        if (!TextUtils.isEmpty(descriptionString) && descriptionString != null)
            outState.putString(DESCRIPTION_KEY, descriptionString);
        String endDateString = endDate.getText().toString();
        if (!TextUtils.isEmpty(endDateString) && endDateString != null)
            outState.putString(END_DATE_KEY, endDateString);
        String endTimeString = endTime.getText().toString();
        if (!TextUtils.isEmpty(endTimeString) && endTimeString != null)
            outState.putString(END_TIME_KEY, endTimeString);
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
        // first editText views
        if (savedInstanceState.containsKey(NAME_KEY))
            name.setText(savedInstanceState.getString(NAME_KEY));
        if (savedInstanceState.containsKey(DESCRIPTION_KEY))
            description.setText(savedInstanceState.getString(DESCRIPTION_KEY));
        if (savedInstanceState.containsKey(END_DATE_KEY))
            endDate.setText(savedInstanceState.getString(END_DATE_KEY));
        if (savedInstanceState.containsKey(END_TIME_KEY))
            endTime.setText(savedInstanceState.getString(END_TIME_KEY));
        if (savedInstanceState.containsKey(CALENDAR_TIME_KEY))
            calendar.setTimeInMillis(savedInstanceState.getLong(CALENDAR_TIME_KEY));
        // radio boxes
        if (savedInstanceState.containsKey(REMINDER_CHECKED_KEY)) {
            if (savedInstanceState.getBoolean(REMINDER_CHECKED_KEY)) {
                reminder.setChecked(true);
                checklist.setChecked(false);
                // view anzeigen
            }
        }
        if (savedInstanceState.containsKey(CHECKLIST_CHECKED_KEY)) {
            if (savedInstanceState.getBoolean(CHECKLIST_CHECKED_KEY)) {
                reminder.setChecked(false);
                checklist.setChecked(true);
                showChecklistView();
            }
        }
        if (savedInstanceState.containsKey(CHECKLIST_PREDEFINED_CHECKED_KEY)) {
            if (savedInstanceState.getBoolean(CHECKLIST_PREDEFINED_CHECKED_KEY)) {
                checklistTypePredefined.setChecked(true);
                checklistTypeOwn.setChecked(false);
                showOwnChecklistView();
            }
        }
        if (savedInstanceState.containsKey(CHECKLIST_OWN_CHECKED_KEY)) {
            if (savedInstanceState.getBoolean(CHECKLIST_OWN_CHECKED_KEY)) {
                checklistTypePredefined.setChecked(false);
                checklistTypeOwn.setChecked(true);
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
    }

    private void hideChecklistView() {
        checklistView.setVisibility(View.INVISIBLE);
    }

    private void showOwnChecklistView() {
        ownChecklistView.setVisibility(View.VISIBLE);
        // predefined cheecklist INVISIBLE
    }

    private void hideOwnChecklistView() {
        ownChecklistView.setVisibility(View.INVISIBLE);
        // predefined checklist VISIBLE
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
        if (!TextUtils.isEmpty(name) && name != null) {
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
}
