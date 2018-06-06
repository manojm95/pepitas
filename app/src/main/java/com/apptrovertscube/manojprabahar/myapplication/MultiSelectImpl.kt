package com.apptrovertscube.manojprabahar.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.Toolbar
import com.apptrovertscube.manojprabahar.myapplication.model.Track
import com.yalantis.multiselection.lib.MultiSelect
import com.yalantis.multiselection.lib.MultiSelectBuilder


class MultiSelectImpl : AppCompatActivity() {

    var ms: MultiSelect<Track>? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multi_select)
        var builder = MultiSelectBuilder(Track :: class.java)
                .withContext(this)
                .mountOn((findViewById(R.id.mount_point) as ViewGroup))
                .withSidebarWidth((46 + 8 * 2).toFloat())
        ms = builder.build()


    }

    private fun setUpAdapters(builder: MultiSelectBuilder<Track>)
    {
        //val leftAdapter =  LeftAdapterKotlin()
    }

    private fun setupToolBar(toolbar: Toolbar) {
        toolbar.inflateMenu(R.menu.menu)
        toolbar.setNavigationIcon(R.drawable.ic_back)
        toolbar.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.select) {
                var items = ms!!.selectedItems
                var itemcount: Int = items!!.size
                var msg: String
                when(itemcount)
                {
                    0 -> {
                        msg = "Nothing Selected"
                        ms!!.showNotSelectedPage()
                    }
                    else ->
                    {
                        msg = resources.getQuantityString(R.plurals.you_selected_x_songs,
                                itemcount, itemcount)
                        ms!!.showSelectedPage()
                    }

                }
                Snackbar.make(toolbar, msg, Snackbar.LENGTH_LONG).show()
                true
            } else
                false;

        }
    }

}
