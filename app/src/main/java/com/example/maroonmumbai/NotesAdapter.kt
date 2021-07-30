package com.example.maroonmumbai

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.model_notes_item.view.*

class NotesAdapter(val notesList: ArrayList<NotesModelClass>): RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    inner class NotesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val holder = LayoutInflater.from(parent.context).inflate(R.layout.model_notes_item,parent,false)
        return NotesViewHolder(holder)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.itemView.also {
            it.tvTitleNotes.text = notesList[position].title
            it.tvDetailsNotes.text = notesList[position].details
        }
    }

    override fun getItemCount(): Int {
        return notesList.size
    }
}