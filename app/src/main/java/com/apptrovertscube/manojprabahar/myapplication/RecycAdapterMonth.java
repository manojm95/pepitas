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

        import java.util.List;

public class RecycAdapterMonth extends RecyclerView.Adapter<RecycAdapterMonth.MyViewHolder> {

    private List<MonthForRecyc> monthList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView dd, day, total;

        public MyViewHolder(View view) {
            super(view);
            dd = (TextView) view.findViewById(R.id.recycfirstletter);
            day = (TextView) view.findViewById(R.id.recycdate);
            total = (TextView) view.findViewById(R.id.recyctotal);
        }
    }


    public RecycAdapterMonth(List<MonthForRecyc> monthList) {
        this.monthList = monthList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycitem, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MonthForRecyc month = monthList.get(position);
        holder.dd.setText((String.valueOf(month.getDay())).substring(0,2));
        Log.d("ghfghf",String.valueOf(month.getDate()));
        holder.dd.setTag(String.valueOf(month.getDate()));
        holder.day.setText(month.getDay());
        holder.total.setText(String.format("%.02f",Double.parseDouble(month.getTotal())));
        holder.total.setTag(month.getSource());
    }

    @Override
    public int getItemCount() {
        return monthList.size();
    }
}
