package com.example.memo.utils

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.memo.R
import com.example.memo.model.Folder


private const val TAG = "folderAdapter"

class FolderAdapter(private val onClick: (Folder) -> Unit) :
        ListAdapter<Folder, FolderAdapter.FolderViewHolder>(
            FolderDiffCallback
        ){

    var data = mutableListOf<Folder>()

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

                    val pos = adapterPosition
                    if (pos != RecyclerView.NO_POSITION) {
                        // TODO : use pos.
                        //val item: RecyclerItem = data.get(pos)
                    }
                    onClick(it)
                }
            }
        }

        fun bind(folder: Folder) {
            currentFolder = folder
            nameFolderTextView.text = folder.folderName
            numberNotesTextView.text = folder.numberNotes.toString()
        }

    }

    /* Inflates view and returns HeaderViewHolder. */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.folder_list, parent, false)
        return FolderViewHolder(
            view,
            onClick
        )
    }

    //ViewHolder에서 데이터 묶는 함수가 실행되는 곳
    override fun onBindViewHolder(holder: FolderViewHolder, position: Int) {
        val folder = getItem(position)
        holder.bind(folder)
    }

    /* Returns number of items, since there is only one item in the header return one  */
    override fun getItemCount(): Int = data.size

    fun updateFolderList(list: MutableList<Folder>) {
        Log.d(TAG, "updateFolderList $list")
        this.data = list
        notifyDataSetChanged()
    }

    override fun submitList(list: MutableList<Folder>?) {
        Log.d(TAG, "submitList $list")
        this.data = ArrayList(list)
        notifyDataSetChanged()
        super.submitList(list?.let { ArrayList(it) })
    }

}

object FolderDiffCallback : DiffUtil.ItemCallback<Folder>() {
    override fun areItemsTheSame(oldItem: Folder, newItem: Folder): Boolean {
        Log.d(TAG, "FolderDiffCallback areItemsTheSame")
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Folder, newItem: Folder): Boolean {
        Log.d(TAG, "FolderDiffCallback areContentsTheSame ")
        return oldItem.folderName == newItem.folderName
    }
}
