package com.example.maroonmumbai

import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.model_todo_item.view.*

class TodoAdapter(val todoList: List<TodoModelClass>) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.model_todo_item, parent,false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val item = todoList.get(position)
        holder.itemView.also {
            it.tvTitle.text = item.title
            it.tvDescription.text = item.description
            setCardStatus(item.label, it)
        }
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    // this function changes card bg and label color depending on status of the task
    // H -> high priority task
    // M -> medium priority task
    // L -> low priority task
    fun setCardStatus(status: String, iv: View){
        when(status){
            "H" -> {
                iv.ivLabel.setImageResource(R.drawable.ic_label_most)
                iv.cvTodo.setCardBackgroundColor(Color.parseColor("#FFE6EE"))
            }
            "M" -> {
                iv.ivLabel.setImageResource(R.drawable.ic_label_moderate)
                iv.cvTodo.setCardBackgroundColor(Color.parseColor("#FFFFFDE6"))
            }
            "L" -> {
                iv.ivLabel.setImageResource(R.drawable.ic_label_default)
                iv.cvTodo.setCardBackgroundColor(Color.parseColor("#FFE6FFEE"))
            }
            else -> {
                iv.ivLabel.setImageResource(R.drawable.ic_label_completed)
                iv.cvTodo.setCardBackgroundColor(Color.parseColor("#FFD6D6D6"))
            }
        }

    }

    fun removeAt(pos: Int) {

    }

    inner class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{

        init {
            itemView.cbToDo.setOnClickListener(this)
        }

        // actions to be taken when checkbox is clicked on individual tasks
        override fun onClick(v: View?) {
            val todoItem = todoList.get(adapterPosition)
            if(!todoItem.isDone){
                itemView.also {
                    it.tvTitle.paintFlags = it.tvTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    it.tvDescription.paintFlags = it.tvDescription.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    setCardStatus("completed", it)
                }
            }else{
                itemView.also {
                    it.tvTitle.paintFlags = it.tvTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                    it.tvDescription.paintFlags = it.tvDescription.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                    setCardStatus(todoItem.label, it)
                }
            }
            todoItem.isDone = !todoItem.isDone
        }
    }

}