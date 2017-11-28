package com.example.android.householdroutine;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.householdroutine.data.DbContract;

/**
 * Created by oliver on 10.10.2017.
 */

public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.MainAdapterViewHolder> {

    private static final int VIEW_TYPE_REMINDER = 0;
    private static final int VIEW_TYPE_CHECKLIST = 1;

    private final Context mContext;
    private Cursor mCursor;


    public MainRecyclerViewAdapter(@NonNull Context context) {
        mContext = context;
    }

    /**
     * Creates view holders
     *
     * @param viewGroup
     * @param viewType
     * @return
     */
    @Override
    public MainAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        /**
         *  Vielleicht später nochmal
         *
            int layoutId;
            switch (viewType) {
                case VIEW_TYPE_REMINDER:
                    layoutId = R.layout.reminder_list_iten;
                    break;
                case VIEW_TYPE_CHECKLIST:
                    layoutId = R.layout.checklist_list_item;
                    break;
                default:
                    throw new IllegalArgumentException("Unknown viewType: " + viewType);
            }

            View view = LayoutInflater.from(mContext).inflate(layoutId, viewGroup, false);
         */

        View view = LayoutInflater.from(mContext).inflate(
                R.layout.reminder_list_iten, viewGroup, false);
        view.setFocusable(true);
        return new MainAdapterViewHolder(view);
    }

    /**
     * Fills the view holders automatically with the data for that position
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(MainAdapterViewHolder holder, int position) {
        mCursor.moveToPosition(position);

        // icon
        int reminderType = mCursor.getInt(MainActivity.INDEX_TYPE);
        switch (reminderType) {
            case DbContract.RemindersEntry.TYPE_REMINDER:
                holder.iconView.setImageResource(R.drawable.ic_access_time_black_48px);
                break;
            case DbContract.RemindersEntry.TYPE_CHECKLIST:
                holder.iconView.setImageResource(R.drawable.ic_check_box_black_48dp);
                break;
            default:
                throw new IllegalArgumentException("Unknown reminder type id " + reminderType);
        }
        // name
        String name = mCursor.getString(MainActivity.INDEX_NAME);
        holder.nameView.setText(name);
        // description
        String description = mCursor.getString(MainActivity.INDEX_DESCRIPTION);
        holder.descriptionView.setText(description);
        // end date
        long endDate = mCursor.getLong(MainActivity.INDEX_END_DATE);
        if(endDate <= System.currentTimeMillis()) {
            holder.dateView.setVisibility(View.INVISIBLE);
            holder.endDateLabelTextView.setText(mContext.getString(R.string.outdated));
        } else {
            holder.dateView.setVisibility(View.VISIBLE);
            holder.endDateLabelTextView.setText(mContext.getString(R.string.end_date));

            int flags = DateUtils.FORMAT_SHOW_DATE
                    | DateUtils.FORMAT_NUMERIC_DATE
                    | DateUtils.FORMAT_SHOW_YEAR
                    | DateUtils.FORMAT_SHOW_TIME;
            String endDateString = DateUtils.formatDateTime(mContext, endDate, flags);
            holder.dateView.setText(endDateString);
        }
        // id
        Long id = mCursor.getLong(MainActivity.INDEX_ID);
        holder.id = id;


    }

    @Override
    public int getItemCount() {
        if (mCursor == null)
            return 0;
        return mCursor.getCount();
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

    class MainAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ImageView iconView;
        final TextView dateView;
        final TextView nameView;
        final TextView descriptionView;
        final TextView endDateLabelTextView;
        Long id;

        MainAdapterViewHolder(View view) {
            super(view);

            iconView = (ImageView) view.findViewById(R.id.list_item_icon);
            dateView = (TextView) view.findViewById(R.id.list_Item_end_date);
            nameView = (TextView) view.findViewById(R.id.list_item_name);
            descriptionView = (TextView) view.findViewById(R.id.list_item_description);
            endDateLabelTextView = (TextView) view.findViewById(R.id.list_item_end_date_label);

            view.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            Intent reminderDetails = new Intent(mContext, TaskDetails.class);
            reminderDetails.putExtra(MainActivity.EXTRA_REMINDER_ID, id);
            mContext.startActivity(reminderDetails);

        }
    }

}
