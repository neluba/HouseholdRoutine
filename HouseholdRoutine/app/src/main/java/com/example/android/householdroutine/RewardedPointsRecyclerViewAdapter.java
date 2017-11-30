package com.example.android.householdroutine;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.householdroutine.data.DbContract;

/**
 * Created by oliver on 30.11.2017.
 */

public class RewardedPointsRecyclerViewAdapter extends RecyclerView.Adapter<RewardedPointsRecyclerViewAdapter.RewardedPointsAdapterViewHolder>{

    private final Context mContext;
    private Cursor mCursor;

    public RewardedPointsRecyclerViewAdapter(@NonNull Context context) {
        mContext = context;
    }


    @Override
    public RewardedPointsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.points_list_item, parent, false);
        view.setFocusable(false);
        return new RewardedPointsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RewardedPointsAdapterViewHolder holder, int position) {
        mCursor.moveToPosition(position);

        // id
        long id = mCursor.getLong(RewardedPoints.INDEX_ID);
        holder.id = id;
        // title
        String name= mCursor.getString(RewardedPoints.INDEX_TITLE);
        holder.titleView.setText(name);
        // description
        String description = mCursor.getString(RewardedPoints.INDEX_DESCRIPTION);
        if(TextUtils.isEmpty(description))
            holder.descriptionView.setText(mContext.getString(R.string.points_no_description));
        else
            holder.descriptionView.setText(description);
        // date
        long date = mCursor.getLong(RewardedPoints.INDEX_DATE);
        int flags = DateUtils.FORMAT_SHOW_DATE
                | DateUtils.FORMAT_NUMERIC_DATE
                | DateUtils.FORMAT_SHOW_YEAR
                | DateUtils.FORMAT_SHOW_TIME;
        String dateString = DateUtils.formatDateTime(mContext, date, flags);
        holder.dateView.setText(dateString);
        // points
        String points = mCursor.getString(RewardedPoints.INDEX_POINTS);
        holder.pointsView.setText(points);
        // type
        String type = mCursor.getString(RewardedPoints.INDEX_TYPE);
        if(type.equals(DbContract.UserPointsEntry.TYPE_REMINDER)) {
            holder.typeView.setText(mContext.getString(R.string.points_type_reminder));
        } else
            holder.typeView.setText(type);
    }

    @Override
    public int getItemCount() {
        if (mCursor == null)
            return 0;
        return mCursor.getCount();
    }

    void swapCursor(Cursor newCursor) {
        mCursor = newCursor;
        notifyDataSetChanged();
    }

    class RewardedPointsAdapterViewHolder extends RecyclerView.ViewHolder {
        long id;
        final TextView titleView;
        final TextView descriptionView;
        final TextView dateView;
        final TextView pointsView;
        final TextView typeView;

        RewardedPointsAdapterViewHolder(View view) {
            super(view);

            titleView = (TextView) view.findViewById(R.id.points_title);
            descriptionView = (TextView) view.findViewById(R.id.points_description);
            dateView = (TextView) view.findViewById(R.id.points_date);
            pointsView = (TextView) view.findViewById(R.id.points_points);
            typeView = (TextView) view.findViewById(R.id.points_type);
        }
    }
}
