package com.example.maroonmumbai.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.maroonmumbai.R
import com.example.maroonmumbai.model.NotesModelClass
import com.example.maroonmumbai.ui.fragments.NotesFragment
import kotlinx.android.synthetic.main.model_notes_item.view.*

class NotesAdapter(frag: NotesFragment) : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    //take the fragment passed during instantiation, this will be used to access viewmodel
    //and make changes in the database through it
    private val fragment = frag

    // differCallBack is used so that only the changed items from recyclerviews are updated and not
    // the whole recyclerview this improves efficiency
    private val differCallBack = object : DiffUtil.ItemCallback<NotesModelClass>() {
        override fun areItemsTheSame(oldItem: NotesModelClass, newItem: NotesModelClass): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(
            oldItem: NotesModelClass,
            newItem: NotesModelClass
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val holder =
            LayoutInflater.from(parent.context).inflate(R.layout.model_notes_item, parent, false)
        return NotesViewHolder(holder)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val notesList = differ.currentList

        holder.itemView.also {
            it.tvTitleNotes.text = notesList[position].title
            it.tvDetailsNotes.text = notesList[position].details
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        //get the list currently in use from the differ callback
        private val notesList: MutableList<NotesModelClass> = differ.currentList

        init {
            itemView.deleteNoteButton.setOnClickListener(this)
        }

        override fun onClick(v: View?) {

            //use the fragment passed in the argument to access the db
            fragment.viewModel.deleteNote(differ.currentList[adapterPosition])
            notifyItemChanged(adapterPosition)
        }
    }
}
