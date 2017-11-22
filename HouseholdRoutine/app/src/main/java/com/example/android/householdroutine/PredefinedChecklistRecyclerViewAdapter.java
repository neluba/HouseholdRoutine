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

import com.example.android.householdroutine.utilities.ConvertJsonArray;

import java.util.List;

/**
 * Created by olive on 21.11.2017.
 */

public class PredefinedChecklistRecyclerViewAdapter extends RecyclerView.Adapter<PredefinedChecklistRecyclerViewAdapter.PredefinedChecklistAdapterViewHolder> {
    private final Context mContext;
    private Cursor mCursor;
    private EditText nameEditText;
    private EditText descriptionEditText;


    public PredefinedChecklistRecyclerViewAdapter(@NonNull Context context, EditText name, EditText description) {
        mContext = context;
        nameEditText = name;
        descriptionEditText = description;
    }

    @Override
    public PredefinedChecklistAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.predefined_checklist_list_item, parent, false);
        view.setFocusable(false);
        return new PredefinedChecklistAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PredefinedChecklistAdapterViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        // id
        long id = mCursor.getLong(CreateTask.PREDEF_CHECKLIST_INDEX_ID);
        holder.id = id;
        // name
        String name = mCursor.getString(CreateTask.PREDEF_CHECKLIST_INDEX_NAME);
        holder.nameView.setText(name);
        // description
        String description = mCursor.getString(CreateTask.PREDEF_CHECKLIST_INDEX_DESCRIPTION);
        holder.descriptionView.setText(description);
        //items
        String items = mCursor.getString(CreateTask.PREDEF_CHECKLIST_INDEX_ITEM_NAME);
        List<String> itemList = ConvertJsonArray.jsonArrayToList(items);
        String itemString = "";
        for (int i = 0; i < itemList.size(); i++) {
            if (i == 0) {
                itemString = "• " + itemList.get(0);
            } else {
                itemString = itemString + "\n• " + itemList.get(i);
            }
        }

        holder.itemsView.setText(itemString);
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

    class PredefinedChecklistAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView nameView;
        final TextView descriptionView;
        final TextView itemsView;
        long id;

        PredefinedChecklistAdapterViewHolder(View view) {
            super(view);

            nameView = (TextView) view.findViewById(R.id.predef_checklist_name);
            descriptionView = (TextView) view.findViewById(R.id.predef_checklist_description);
            itemsView = (TextView) view.findViewById(R.id.predef_checklist_items);

            view.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            nameEditText.setText(nameView.getText().toString());
            nameEditText.setTag(id);
            descriptionEditText.setText(descriptionView.getText().toString());
        }

    }
}
