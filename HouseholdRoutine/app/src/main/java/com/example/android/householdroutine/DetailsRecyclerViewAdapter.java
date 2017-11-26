package com.example.android.householdroutine;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * Created by olive on 26.11.2017.
 */

public class DetailsRecyclerViewAdapter extends RecyclerView.Adapter<DetailsRecyclerViewAdapter.DetailsAdapterViewHolder> {

    private final Context mContext;
    private Cursor mCursor;


    public DetailsRecyclerViewAdapter(@NonNull Context context) {
        mContext = context;
    }


    @Override
    public DetailsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.details_list_item, parent, false);
        view.setFocusable(false);
        return new DetailsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DetailsAdapterViewHolder holder, int position) {
        mCursor.moveToPosition(position);

        // item name
        String name= mCursor.getString(TaskDetails.CHECKLIST_INDEX_ITEM_NAME);
        holder.nameView.setText(name);

        // checkbox
        int completed = mCursor.getInt(TaskDetails.CHECKLIST_INDEX_COMPLETED);
        if(completed == 1) {
            holder.checked = true;
            holder.checkBoxView.setChecked(true);
        } else {
            holder.checked = false;
            holder.checkBoxView.setChecked(false);
        }

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

    class DetailsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView nameView;
        final CheckBox checkBoxView;
        boolean checked;

        DetailsAdapterViewHolder(View view) {
            super(view);

            nameView = (TextView) view.findViewById(R.id.details_checklist_item_name);
            checkBoxView = (CheckBox) view.findViewById(R.id.details_checklist_item_checkBox);

            view.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            if (checked)
                checked = false;
            else
                checked = true;
            checkBoxView.setChecked(checked);

        }

    }
}
