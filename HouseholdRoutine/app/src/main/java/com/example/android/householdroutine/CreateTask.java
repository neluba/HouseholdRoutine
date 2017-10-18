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
import java.util.List;

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
    private List<String> checklistItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        checklistItems = new ArrayList<String>();

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
                checklistView.setVisibility(View.INVISIBLE);
            }
        });
        checklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checklistView.setVisibility(View.VISIBLE);
            }
        });


        // checklist type radio buttons
        checklistTypePredefined.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ownChecklistView.setVisibility(View.INVISIBLE);
                // predefined checklist VISIBLE
            }
        });
        checklistTypeOwn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ownChecklistView.setVisibility(View.VISIBLE);
                // predefined cheecklist INVISIBLE
            }
        });


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
     * @param v
     */
    public void addItem(View v) {
        String name = ownChecklistAddItemName.getText().toString();
        if(!TextUtils.isEmpty(name) && name != null) {
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
     * @param v
     */
    public void removeItem(View v) {
        View parent = (View) v.getParent();
        TextView itemTextView = (TextView) parent.findViewById(R.id.own_checklist_item_name);
        String name = itemTextView.getText().toString();

        for(int i = 0; i < checklistItems.size(); i++) {
            if(checklistItems.get(i).equals(name)) {
                checklistItems.remove(i);
                break;
            }
        }

        ownChecklistView.removeView((View) v.getParent());


    }
}
