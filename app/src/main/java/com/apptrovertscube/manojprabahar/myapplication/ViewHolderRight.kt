package com.apptrovertscube.manojprabahar.myapplication

import android.graphics.Color
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.view.View
import com.apptrovertscube.manojprabahar.myapplication.model.TrackParseFile
import com.michaelmuenzer.android.scrollablennumberpicker.ScrollableNumberPicker
import com.parse.ParseFile
import com.squareup.picasso.Picasso

/**
 * Created by mna on 7/15/17.
 */

internal open class ViewHolderRight(view: View): RecyclerView.ViewHolder(view) {
    var name: TextView? = null
    var comment: TextView? = null
    var avatar: ImageView? = null
    var np : ScrollableNumberPicker? = null

    init {
        name = view.findViewById(R.id.popupitemtv)
        comment = view.findViewById(R.id.popupprice)
        avatar = view.findViewById(R.id.profile_image)
        np = view.findViewById(R.id.number_picker_horizontal)
    }

    companion object {

        fun bind(viewHolder:  ViewHolderRight, contact: TrackParseFile) {

            viewHolder.name?.setText(contact.trackName)
            viewHolder.name?.setTag(contact.trackName)
            //viewHolder.name?.setTextColor(Color.parseColor("#FFFFFF"))
            var pf : ParseFile = contact.album
            var url : String = "";
            url = pf.url
            var imageuri : Uri = Uri.parse(url)
            Picasso.with(DemoActivity.con).load(imageuri.toString()).into(viewHolder.avatar)
            //viewHolder.avatar?.setImageResource(contact.album)
            //viewHolder.avatar?.setBackgroundColor(Color.parseColor("#00797D7F"))
            viewHolder.comment?.setText("INR: ${contact.artist}")
            viewHolder.name?.setTextColor(Color.parseColor("#000000"))
            val chk: Int? = DemoActivity.buildlayout.get(contact.trackName)
            if(chk != null)
            {
                viewHolder.np?.value = chk

                viewHolder.comment?.setText("INR: ${(LoginActivity.round(chk * java.lang.Double.parseDouble(contact.artist),2))}")
            }
            viewHolder.np?.setListener { value ->
                viewHolder.comment?.setText("INR: ${String.format("%.2f",(value * java.lang.Double.parseDouble(contact.artist)))}")
                //val chk: Int? = DemoActivity.hm.get(contact.trackName)
//                if(chk != null)
//                {
//                    DemoActivity.hm.put(contact.trackName,value)
//                }
//                else {
                    DemoActivity.hm.put(contact.trackName, value);
                Log.d("ytrewqc", "${DemoActivity.hm.size}");
                //Log.d("opopopo","${DemoActivity.hm.get(contact.trackName)}")
//                }
            }


        }
    }

    }

