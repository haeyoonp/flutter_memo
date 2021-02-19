package com.example.memo

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


private const val TAG = "NoteAdapter"

class NoteAdapter(private val onClick: (Note) -> Unit) :
        ListAdapter<Note, NoteAdapter.NoteViewHolder>(NoteDiffCallback){

    var data = mutableListOf<Note>()

    /* ViewHolder for displaying header. */
    class NoteViewHolder(itemView : View, val onClick: (Note) -> Unit) :
            RecyclerView.ViewHolder(itemView) {
        //private val flowerImageView: ImageView = itemView.findViewById(R.id.flower_image)
        private val  titleNoteTextView: TextView = itemView.findViewById(R.id.note_title)
        private val  dateNoteTextView: TextView = itemView.findViewById(R.id.date)
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
            val formatter = SimpleDateFormat("yyyy.dd.MM") //formating according to my need
            val date: String = formatter.format(note.last_edit)
            currentNote = note
            titleNoteTextView.text = note.name
            dateNoteTextView.text = date
            var showContents = note.contents?.split("\n")?.get(0)
            contentNotesTextView.text = showContents
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


    override fun submitList(list: MutableList<Note>?) {
        this.data = ArrayList(list)
        notifyDataSetChanged()
        super.submitList(list?.let { ArrayList(it) })
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
