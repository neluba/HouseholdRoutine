package com.example.android.householdroutine;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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


    @Override
    public MainAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(MainAdapterViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class MainAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView dateView;
        final TextView nameView;
        final TextView descriptionView;
        final TextView endDateView;

        MainAdapterViewHolder(View view) {
            super(view);

            dateView = (TextView) view.findViewById(R.id.list_Item_end_date);
            nameView = (TextView) view.findViewById(R.id.list_item_name);
            descriptionView = (TextView) view.findViewById(R.id.list_item_description);
            endDateView = (TextView) view.findViewById(R.id.list_Item_end_date);

            view.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            // TODO onClick verhalten für die RecyclerView view holder
        }
    }

}
