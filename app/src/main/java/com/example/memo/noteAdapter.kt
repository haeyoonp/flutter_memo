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


private const val TAG = "NoteAdapter"

class NoteAdapter(private val onClick: (Note) -> Unit) :
        ListAdapter<Note, NoteAdapter.NoteViewHolder>(NoteDiffCallback){

    var data = mutableListOf<Note>()
    val db = Firebase.firestore

    init {
        var noteList = mutableListOf<Note>()
        db.collection("notes").get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        val note = document.toObject(Note::class.java)
                        noteList.add(note)
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents: ", exception)
                }
                .addOnCompleteListener {
                    Log.d(TAG, "noteList : ${noteList}")
                    Log.d(TAG, "NoteAdapter data : ${data}")
                    data = noteList
                }

    }

    /* ViewHolder for displaying header. */
    class NoteViewHolder(itemView : View, val onClick: (Note) -> Unit) :
            RecyclerView.ViewHolder(itemView) {
        //private val flowerImageView: ImageView = itemView.findViewById(R.id.flower_image)
        private val  titleNoteTextView: TextView = itemView.findViewById(R.id.note_title)
        private val  dateNoteTextView: TextView = itemView.findViewById(R.id.note_title)
        private val  contentNotesTextView: TextView = itemView.findViewById(R.id.note_content)
        private var currentNote: Note? = null

        init {
            itemView.setOnClickListener {
                currentNote?.let {
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

        fun bind(note: Note) {
            currentNote = note
            titleNoteTextView.text = note.name
            contentNotesTextView.text = note.contents.toString()
        }

    }

    /* Inflates view and returns HeaderViewHolder. */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_list, parent, false)
        return NoteViewHolder(view, onClick)
    }

    //ViewHolder에서 데이터 묶는 함수가 실행되는 곳
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        Log.d(TAG, "${position}")
        val note = getItem(position)
        holder.bind(note)
    }

    /* Returns number of items, since there is only one item in the header return one  */
    override fun getItemCount(): Int = data.size

    fun updateNoteList(updatedFlowerCount: Int) {
        //flowerCount = updatedFlowerCount
        notifyDataSetChanged()
    }

}

object NoteDiffCallback : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.name == newItem.name
    }
}
