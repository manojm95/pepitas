package com.yalantis.multiselection.lib

import android.content.Context
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.yalantis.multiselection.R
import com.yalantis.multiselection.lib.adapter.BaseLeftAdapter
import com.yalantis.multiselection.lib.adapter.BaseRightAdapter

/**
 * Created by Artem Kholodnyi on 9/17/16.
 */
class MultiSelectBuilder<Any : Comparable<Any>>(val clazz: Class<Any>) {

    private lateinit var context: Context
    private lateinit var mountPoint: ViewGroup
    private lateinit var multiSelectView: MultiSelectImpl<Any>
    private lateinit var leftAdapter: BaseLeftAdapter<Any, out RecyclerView.ViewHolder>
    private lateinit var rightAdapter: BaseRightAdapter<Any, out RecyclerView.ViewHolder>
    private var sidebarWidth: Float = 0f

    fun withContext(context: Context): MultiSelectBuilder<Any> {
        this.context = context
        return this
    }

    fun mountOn(mountPoint: ViewGroup): MultiSelectBuilder<Any> {
        this.mountPoint = mountPoint
        return this
    }


    fun withSidebarWidth(sidebarWidthDp: Float): MultiSelectBuilder<Any> {
        this.sidebarWidth = sidebarWidthDp
        return this
    }

    fun withLeftAdapter(adapter: BaseLeftAdapter<Any, out RecyclerView.ViewHolder>): MultiSelectBuilder<Any> {
        this.leftAdapter = adapter
        return this
    }


    fun withRightAdapter(adapter: BaseRightAdapter<Any, out RecyclerView.ViewHolder>): MultiSelectBuilder<Any> {
        this.rightAdapter = adapter
        return this
    }

    private fun build(): MultiSelectImpl<Any> {
        this.multiSelectView = MultiSelectImpl<Any>(context, mountPoint)
        this.multiSelectView.id = R.id.yal_ms_multiselect
        this.multiSelectView.setSidebarWidthDp(this.sidebarWidth)
        this.multiSelectView.leftAdapter = this.leftAdapter
        this.multiSelectView.rightAdapter = this.rightAdapter
        this.mountPoint.addView(this.multiSelectView)
        return this.multiSelectView
    }

}