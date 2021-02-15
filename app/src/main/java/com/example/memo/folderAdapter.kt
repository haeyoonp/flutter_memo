package com.example.memo

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


private const val TAG = "folderAdapter"

class FolderAdapter(private val onClick: (Folder) -> Unit) :
        ListAdapter<Folder, FolderAdapter.FolderViewHolder>(FolderDiffCallback){

    var data = mutableListOf<Folder>()
    val db = Firebase.firestore

    init {
        var folderList = mutableListOf<Folder>()
        db.collection("folders").get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        val folder = document.toObject(Folder::class.java)
                        folderList.add(folder)
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents: ", exception)
                }
                .addOnCompleteListener {
                    Log.d(TAG, "folderList : ${folderList}")
                    Log.d(TAG, "FolderAdapter data : ${data}")
                    data = folderList
                }

    }

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
                    Log.d(TAG, "onClick ")
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
            nameFolderTextView.text = folder.name
            numberNotesTextView.text = folder.number_notes.toString()
        }

    }

    /* Inflates view and returns HeaderViewHolder. */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.folder_list, parent, false)
        return FolderViewHolder(view, onClick)
    }

    //ViewHolder에서 데이터 묶는 함수가 실행되는 곳
    override fun onBindViewHolder(holder: FolderViewHolder, position: Int) {
        Log.d(TAG, "${position}")
        val folder = getItem(position)
        holder.bind(folder)
    }

    /* Returns number of items, since there is only one item in the header return one  */
    override fun getItemCount(): Int = data.size

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
