package com.example.android.householdroutine;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by olive on 20.11.2017.
 */

public class PredefinedRemindersRecyclerViewAdapter extends RecyclerView.Adapter<PredefinedRemindersRecyclerViewAdapter.PredefinedReminderAdapterViewHolder> {

    private final Context mContext;
    private Cursor mCursor;
    private EditText nameEditText;
    private EditText descriptionEditText;


    public PredefinedRemindersRecyclerViewAdapter(@NonNull Context context, EditText name, EditText description) {
        mContext = context;
        nameEditText = name;
        descriptionEditText = description;
    }

    @Override
    public PredefinedReminderAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.predefined_reminder_list_item, parent, false);
        view.setFocusable(false);
        return new PredefinedReminderAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PredefinedReminderAdapterViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        // name
        String name = mCursor.getString(CreateTask.PREDEF_REMINDERS_INDEX_NAME);
        holder.nameView.setText(name);
        // description
        String description = mCursor.getString(CreateTask.PREDEF_REMINDERS_INDEX_DESCRIPTION);
        holder.descriptionView.setText(description);
    }

    /**
     * Swaaps and sets a new cursor for the recyclerview to load data from.
     *
     * @param newCursor
     */
    void swapCursor(Cursor newCursor) {
        mCursor = newCursor;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mCursor == null)
            return 0;
        return mCursor.getCount();
    }

    class PredefinedReminderAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView nameView;
        final TextView descriptionView;

        PredefinedReminderAdapterViewHolder(View view) {
            super(view);

            nameView = (TextView) view.findViewById(R.id.predef_reminder_name);
            descriptionView = (TextView) view.findViewById(R.id.predef_reminder_description);

            view.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            nameEditText.setText(nameView.getText().toString());
            descriptionEditText.setText(descriptionView.getText().toString());
        }

    }
}



