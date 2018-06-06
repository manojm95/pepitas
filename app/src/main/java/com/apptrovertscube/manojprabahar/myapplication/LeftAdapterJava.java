package com.apptrovertscube.manojprabahar.myapplication;

import android.support.annotation.NonNull;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apptrovertscube.manojprabahar.myapplication.model.Track;
//import com.yalantis.multiselectdemo.R;
import com.apptrovertscube.manojprabahar.myapplication.model.TrackParseFile;
import com.yalantis.multiselection.lib.adapter.BaseLeftAdapter;

/**
 * Created by Artem Kholodnyi on 9/3/16.
 */
public class LeftAdapterJava extends BaseLeftAdapter<TrackParseFile, ViewHolder1>{

    private final Callback callback;

    public LeftAdapterJava(Callback callback) {
        super(TrackParseFile.class);
        this.callback = callback;
    }

    @Override
    public ViewHolder1 onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        return new ViewHolder1(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder1 holder, int position) {
        super.onBindViewHolder(holder, position);


        ViewHolder1.Companion.bind(holder, getItemAt(position));

        holder.itemView.setOnClickListener(view -> {
            view.setPressed(true);
            view.postDelayed(() -> {
                view.setPressed(false);
                callback.onClick(holder.getAdapterPosition());
            }, 200);
        });

    }



}
