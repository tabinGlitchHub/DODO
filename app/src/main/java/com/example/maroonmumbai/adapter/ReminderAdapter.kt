package com.example.maroonmumbai.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.maroonmumbai.R
import com.example.maroonmumbai.model.ReminderModelClass
import com.example.maroonmumbai.ui.fragments.ReminderFragment
import kotlinx.android.synthetic.main.model_reminder_item.view.*

class ReminderAdapter(frag: ReminderFragment): RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder>() {

    private val fragment = frag

    private val differCallBack = object : DiffUtil.ItemCallback<ReminderModelClass>() {
        override fun areItemsTheSame(
            oldItem: ReminderModelClass,
            newItem: ReminderModelClass
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ReminderModelClass,
            newItem: ReminderModelClass
        ): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallBack)

    inner class ReminderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder {
        val holder = LayoutInflater.from(parent.context).inflate(R.layout.model_reminder_item, parent, false)
        return ReminderViewHolder(holder)
    }

    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        val reminderList = differ.currentList
        val reminder = reminderList[position]

        holder.itemView.also {
            it.tvTitleReminder.text = reminder.title
            it.tvTimeReminer.text = reminder.time
            it.tvDateReminder.text = reminder.date
            it.tvCategoryReminder.text = reminder.category
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}