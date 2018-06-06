package com.apptrovertscube.manojprabahar.myapplication;

/**
 * Created by mna on 8/6/17.
 */

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apptrovertscube.manojprabahar.myapplication.model.Search;

import java.util.ArrayList;
import java.util.List;

public class RecycAdapterSearch extends RecyclerView.Adapter<RecycAdapterSearch.MyViewHolder> {

    private List<Search> monthList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView ii, name, price;

        public MyViewHolder(View view) {
            super(view);
            ii = (TextView) view.findViewById(R.id.recycfirstletter);
            name = (TextView) view.findViewById(R.id.recycitemname);
            price = (TextView) view.findViewById(R.id.recycitemprice);
        }
    }

    public void setFilter(List<Search> items)
    {
        monthList = new ArrayList<>();
        monthList.addAll(items);
        notifyDataSetChanged();
    }

    public RecycAdapterSearch(List<Search> monthList) {
        this.monthList = monthList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycitemsearch, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Search item = monthList.get(position);
        holder.ii.setText((String.valueOf(item.getName())).substring(0,1));
        holder.ii.setTag(String.valueOf(item.getName()));
        holder.name.setText(item.getName());
        holder.price.setText(String.format("%.02f",Double.parseDouble(item.getPrice())));
        holder.price.setTag(String.format("%.02f",Double.parseDouble(item.getPrice())));
    }

    @Override
    public int getItemCount() {
        return monthList.size();
    }
}
