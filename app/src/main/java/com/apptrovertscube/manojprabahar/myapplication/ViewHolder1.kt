package com.apptrovertscube.manojprabahar.myapplication

import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import android.widget.TextView
import android.view.View
import com.apptrovertscube.manojprabahar.myapplication.model.TrackParseFile
import com.parse.ParseFile
import com.squareup.picasso.Picasso


/**
 * Created by mna on 7/15/17.
 */

internal open class ViewHolder1(view: View): RecyclerView.ViewHolder(view) {
    var name: TextView? = null
    var comment: TextView? = null
    var avatar: ImageView? = null

    init {
        name = view.findViewById(R.id.track)
        comment = view.findViewById(R.id.artist)
        avatar = view.findViewById(R.id.profile_image_rl)
    }

    companion object {

        fun bind(viewHolder:  ViewHolder1, contact: TrackParseFile) {
            viewHolder.name?.setText(contact.trackName)
            viewHolder.name?.setTag(contact.trackName)
            var pf : ParseFile = contact.album
            var url : String = "";
            url = pf.url
            var imageuri : Uri = Uri.parse(url)
            Picasso.with(DemoActivity.con).load(imageuri.toString()).into(viewHolder.avatar)

            //viewHolder.avatar?.setImageResource(contact.album)
            viewHolder.comment?.setText("INR: ${contact.artist}")


        }


    }

    }

