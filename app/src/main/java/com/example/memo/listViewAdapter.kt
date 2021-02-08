package com.example.memo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.folder_list.view.*

//data class ListViewItem(val name: String, val number_notes: Int)

class ListViewItem {
    var folder_id: String? = null
    var name: String? = null
    var number_notes: Int? = null
}

class ListViewAdapter(): BaseAdapter() {

    private var listViewItemList = ArrayList<ListViewItem>()

    override fun getCount(): Int = listViewItemList.size
    override fun getItem(position: Int): ListViewItem = listViewItemList[position]
    override fun getItemId(position: Int): Long = position.toLong()
    override fun getView(position: Int, view: View?, parent: ViewGroup?): View
    {
        var convertView = view
        if (convertView == null)
            convertView = LayoutInflater.from(parent?.context).inflate(R.layout.folder_list, parent, false)

        val item: ListViewItem = listViewItemList[position]
        convertView!!.name_folder.text = item.name
        convertView.number_notes.text = item.number_notes.toString()

        return convertView
    }
    fun addItem(folder_id: String, name: String, number_notes: Int) {
        val item = ListViewItem()

        item.folder_id = folder_id
        item.name = name
        item.number_notes = number_notes

        listViewItemList.add(item)
        notifyDataSetChanged();
    }
}
