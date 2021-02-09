package com.example.memo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.folder_list.view.*

class folderListViewItem {
    var folder_id: String? = null
    var name: String? = null
    var number_notes: Int? = null
}

class ListViewAdapter(): BaseAdapter() {

    private var folderListViewItemList = ArrayList<folderListViewItem>()

    override fun getCount(): Int = folderListViewItemList.size
    override fun getItem(position: Int): folderListViewItem = folderListViewItemList[position]
    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View
    {
        var convertView = view
        if (convertView == null)
            convertView = LayoutInflater.from(parent?.context).inflate(R.layout.folder_list, parent, false)

        val item: folderListViewItem = folderListViewItemList[position]
        convertView!!.name_folder.text = item.name
        convertView.number_notes.text = item.number_notes.toString()

        return convertView
    }
    fun addItem(folder_id: String, name: String, number_notes: Int) {
        val item = folderListViewItem()

        item.folder_id = folder_id
        item.name = name
        item.number_notes = number_notes

        folderListViewItemList.add(item)
        notifyDataSetChanged()
    }
}
