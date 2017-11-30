package com.example.android.householdroutine;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by oliver on 30.11.2017.
 */

public class ExtrasRecyclerViewAdapter extends RecyclerView.Adapter<ExtrasRecyclerViewAdapter.ExtrasAdapterViewHolder>{

    private final Context mContext;
    private Cursor mCursor;

    public ExtrasRecyclerViewAdapter(@NonNull Context context) {
        mContext = context;
    }

    @Override
    public ExtrasAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.extras_list_item, parent, false);
        view.setFocusable(false);
        return new ExtrasAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ExtrasAdapterViewHolder holder, int position) {
        mCursor.moveToPosition(position);

        // id
        long id = mCursor.getLong(Extras.INDEX_ID);
        holder.id = id;
        // information title
        String name= mCursor.getString(Extras.INDEX_NAME);
        holder.nameView.setText(name);
        // information description
        String description = mCursor.getString(Extras.INDEX_DESCRIPTION);
        holder.descriptionView.setText(description);
        // information source
        String source = mCursor.getString(Extras.INDEX_SOURCE);
        holder.sourceView.setText(source);
        // information url
        String url = mCursor.getString(Extras.INDEX_URL);
        holder.url = url;
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

    class ExtrasAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        long id;
        final TextView nameView;
        final TextView descriptionView;
        final TextView sourceView;
        String url;

        ExtrasAdapterViewHolder(View view) {
            super(view);

            nameView = (TextView) view.findViewById(R.id.information_name);
            descriptionView = (TextView) view.findViewById(R.id.information_description);
            sourceView = (TextView) view.findViewById(R.id.information_source);

            view.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            // open the source website
            Uri website = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, website);
            if (intent.resolveActivity(mContext.getPackageManager()) != null) {
                mContext.startActivity(intent);
            }

        }

    }
}
