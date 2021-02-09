package com.example.memo

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.folder_list.view.*


private const val TAG = "listViewActivity"

class FolderAdapter(private val onClick: (Folder) -> Unit) :
        ListAdapter<Folder, FolderAdapter.FolderViewHolder>(FolderDiffCallback){
    //데이터들을 저장하는 변수
    var data = folderList() // mutableListOf<Folder>()

    /* ViewHolder for displaying header. */
    class FolderViewHolder(itemView : View, val onClick: (Folder) -> Unit) :
            RecyclerView.ViewHolder(itemView) {
        //private val flowerImageView: ImageView = itemView.findViewById(R.id.flower_image)
        private val  nameFolderTextView: TextView = itemView.findViewById(R.id.name_folder)
        private val  numberNotesTextView: TextView = itemView.findViewById(R.id.number_notes)
        private var currentFolder: Folder? = null

        init {
            itemView.setOnClickListener {
                currentFolder?.let {
                    onClick(it)
                }
            }
        }

        //View와 데이터를 연결시키는 함수
        fun bind(folder: Folder) {
            //Glide 라이브러리를 사용해서 외부 링크를 ImageView에 넣을 수 있음
            //Glide.with(itemView).load(folder.img_goods).into(img_goods)
            nameFolderTextView.text = folder.name
            numberNotesTextView.text = folder.number_notes.toString()
        }
    }

    //상속받으면 자동 생성
    //ViewHolder에 쓰일 Layout을 inflate하는 함수
    //변수로 받은 context를 사용하여 특정 화면에서 구현할 수 있도록 함
    /* Inflates view and returns HeaderViewHolder. */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.folder_list, parent, false)
        return FolderViewHolder(view, onClick)
    }

    //상속받으면 자동 생성
    //ViewHolder에서 데이터 묶는 함수가 실행되는 곳
    override fun onBindViewHolder(holder: FolderViewHolder, position: Int) {
        Log.d(TAG, "${position}")
        val folder = getItem(position)
        holder.bind(folder)
    }

    /* Returns number of items, since there is only one item in the header return one  */
    override fun getItemCount(): Int = data.size

    /* Updates header to display number of flowers when a flower is added or subtracted. */
    fun updateFolderList(updatedFlowerCount: Int) {
        //flowerCount = updatedFlowerCount
        notifyDataSetChanged()
    }

}

object FolderDiffCallback : DiffUtil.ItemCallback<Folder>() {
    override fun areItemsTheSame(oldItem: Folder, newItem: Folder): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Folder, newItem: Folder): Boolean {
        return oldItem.name == newItem.name
    }
}

/*


class ListViewAdapter(): BaseAdapter() {

    private var Folder = ArrayList<Folder>()

    override fun getCount(): Int = Folder.size
    override fun getItem(position: Int): Folder = Folder[position]
    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View
    {
        var convertView = view
        if (convertView == null)
            convertView = LayoutInflater.from(parent?.context).inflate(R.layout.folder_list, parent, false)

        val item: Folder = Folder[position]
        convertView!!.name_folder.text = item.name_folder
        convertView.number_notes.text = item.number_notes.toString()

        return convertView
    }
    fun addItem(folder_id: String, name_folder: String, number_notes: Int) {
        val item = Folder()

        item.folder_id = folder_id
        item.name_folder = name_folder
        item.number_notes = number_notes

        Folder.add(item)
        notifyDataSetChanged()
    }
}*/
