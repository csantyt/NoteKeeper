package com.tuapp.notekeper.ui.notes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tuapp.notekeper.databinding.ItemNoteBinding
import com.tuapp.notekeper.model.Note
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NoteAdapter(
    private val onClick: (Note) -> Unit
) : ListAdapter<Note, NoteAdapter.NoteViewHolder>(NoteDiffCallback()) {

    private val dateFormat = SimpleDateFormat("d MMM yyyy, HH:mm", Locale("es"))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class NoteViewHolder(
        private val binding: ItemNoteBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(note: Note) {
            binding.tvTitle.text = note.title
            binding.tvContent.text = note.content
            binding.tvDate.text = dateFormat.format(Date(note.updatedAt))
            binding.root.setOnClickListener { onClick(note) }
        }
    }

    class NoteDiffCallback : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean = oldItem == newItem
    }
}
