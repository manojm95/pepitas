package com.apptrovertscube.manojprabahar.myapplication

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.apptrovertscube.manojprabahar.myapplication.model.Track
import com.apptrovertscube.manojprabahar.myapplication.model.TrackParseFile
import com.yalantis.multiselection.lib.adapter.BaseRightAdapter

/**
 * Created by mna on 7/16/17.
 */


internal class RightAdapterKotlin(private val callback: Callback): BaseRightAdapter<TrackParseFile, ViewHolderRight>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderRight {
        Log.d("hhhhhjjjjjRAK","Reached here");
        var view: View = LayoutInflater.from(parent.context).inflate(R.layout.popupitem,parent,false)
        return ViewHolderRight(view)    }

    override fun onBindViewHolder(holder: ViewHolderRight, position: Int, payloads: MutableList<Any>?) {
        super.onBindViewHolder(holder, position, payloads)

        ViewHolderRight.bind(holder, getItemAt(position))

//        if(position % 2==1)
//        {
//            holder.itemView.setBackgroundColor(Color.parseColor("#33424949"));
//        }
//        else holder.itemView.setBackgroundColor(Color.parseColor("#66424949"));

        holder.itemView.setOnClickListener { view ->
            val tv: TextView = view.findViewById(R.id.popupitemtv);
            //Log.d("jytreee","The hm value is ${DemoActivity.hm.size} and value is ${tv.text}");
            DemoActivity.hm.remove(tv.text);
            DemoActivity.trackmap.remove(tv.text)
            view.isPressed = true
            view.postDelayed({
                view.isPressed = false
                callback.onClick(holder.adapterPosition)

            }, 200)
        }
    }
}