package com.apptrovertscube.manojprabahar.myapplication

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.apptrovertscube.manojprabahar.myapplication.model.TrackParseFile
import com.yalantis.multiselection.lib.adapter.BaseLeftAdapter

/**
 * Created by mna on 7/16/17.
 */


internal  class LeftAdapterKotlin(private val callback: Callback): BaseLeftAdapter<TrackParseFile, ViewHolder1> (TrackParseFile::class.java){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder1 {
        var view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_view,parent,false)
        return ViewHolder1(view)
    }

    override fun onBindViewHolder(holder: ViewHolder1, position: Int, payloads: MutableList<Any>?) {
        super.onBindViewHolder(holder, position, payloads)
        ViewHolder1.bind(holder, getItemAt(position))
        if(position % 2==1)
        {
            holder.itemView.setBackgroundColor(Color.parseColor("#F4F6F6"));
        }
        else holder.itemView.setBackgroundColor(Color.parseColor("#E5E7E9"));

        holder.itemView.setOnClickListener { view ->
            var tv  = view.findViewById<TextView>(R.id.track)
            var str: String = tv.getTag().toString()
            if(DemoActivity.trackmap.containsKey(str))
            {
                Toast.makeText(DemoActivity.con,"${str} already added to cart.",Toast.LENGTH_SHORT).show()
            }
            else {
                DemoActivity.hm.put(str, 1);
                view.isPressed = true
                view.postDelayed({
                    view.isPressed = false
                    callback.onClick(holder.adapterPosition)
                }, 200)
            }
            if(!DemoActivity.hm.containsKey(str))
            {
                Log.d("ytrewqc", "${DemoActivity.hm.size}");
                //DemoActivity.hm.put(str, 1);

            }
        }
    }
}