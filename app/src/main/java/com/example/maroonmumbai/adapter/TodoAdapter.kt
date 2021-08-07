package com.example.maroonmumbai.adapter

import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.maroonmumbai.R
import com.example.maroonmumbai.model.TodoModelClass
import com.example.maroonmumbai.ui.fragments.ToDoFragment
import kotlinx.android.synthetic.main.model_category_chips.view.*
import kotlinx.android.synthetic.main.model_todo_item.view.*

class TodoAdapter(frag: ToDoFragment) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    // shadow the fragment obtained from the parameter to use it to access viewmodel for db purposes
    var fragment = frag

    // differCallBack is used so that only the changed items from recyclerviews are updated and not
    // the whole recyclerview this improves efficiency
    private val differCallBack = object : DiffUtil.ItemCallback<TodoModelClass>() {
        override fun areItemsTheSame(oldItem: TodoModelClass, newItem: TodoModelClass): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TodoModelClass, newItem: TodoModelClass): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.model_todo_item, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todoList = differ.currentList
        val item = todoList[position]

        holder.itemView.also {
            it.tvTitle.text = item.title
            it.tvDescription.text = item.description
            //load the effects of the task as per the state of task
            if(item.isDone) taskCompletedEffects(it) else {
                taskNotCompletedEffects(it)
                setCardStatus(item.label, it)
            }
            setCardCategory(item.category, it)
        }
    }

    // set the chip visibility if no category was provided by the user else update text
    private fun setCardCategory(category: String?, iv: View) {
        if (category != null){
            iv.tvCategory.text = category
        }else{
            iv.tvCategory.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    //all the visual effects needed to be done when task was marked completed
    private fun taskCompletedEffects(iv: View){
        //change the color of layout and items
        iv.ivLabel.setImageResource(R.drawable.ic_label_completed)
        iv.cvTodo.setCardBackgroundColor(Color.parseColor("#FFD6D6D6"))
        iv.tvCategory.setBackgroundResource(R.drawable.bg_rounded_all_corner_completed_chip)
        //add a strike through effect on the title and description
        iv.tvTitle.paintFlags = iv.tvTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        iv.tvDescription.paintFlags =
            iv.tvDescription.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        //just in case the checkbox isn't updated
        iv.cbToDo.isChecked = true
    }

    private fun taskNotCompletedEffects(iv: View){
        //revert the strike through effect on title and description
        iv.tvTitle.paintFlags =
            iv.tvTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        iv.tvDescription.paintFlags =
            iv.tvDescription.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        //just in case the checkbox isn't updated
        iv.cbToDo.isChecked = false
    }

    // this function changes card bg and label color depending on status of the task
    // H -> high priority task
    // M -> medium priority task
    // L -> low priority task
    private fun setCardStatus(status: String, iv: View) {
        when (status) {
            "H" -> {
                iv.ivLabel.setImageResource(R.drawable.ic_label_most)
                iv.cvTodo.setCardBackgroundColor(Color.parseColor("#FFE6EE"))
                iv.tvCategory.setBackgroundResource(R.drawable.bg_rounded_all_corner_most_chip)
                taskNotCompletedEffects(iv)
            }
            "M" -> {
                iv.ivLabel.setImageResource(R.drawable.ic_label_moderate)
                iv.cvTodo.setCardBackgroundColor(Color.parseColor("#FFFFFDE6"))
                iv.tvCategory.setBackgroundResource(R.drawable.bg_rounded_all_corner_moderate_chip)
                taskNotCompletedEffects(iv)
            }
            "L" -> {
                iv.ivLabel.setImageResource(R.drawable.ic_label_default)
                iv.cvTodo.setCardBackgroundColor(Color.parseColor("#FFE6FFEE"))
                iv.tvCategory.setBackgroundResource(R.drawable.bg_rounded_all_corner_default_chip)
                taskNotCompletedEffects(iv)
            }
            else -> {
                taskCompletedEffects(iv)
            }
        }

    }

    inner class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        private val todoList: MutableList<TodoModelClass> = differ.currentList

        init {
            itemView.cbToDo.setOnClickListener(this)
        }

        // actions to be taken when checkbox is clicked on individual tasks
        override fun onClick(v: View?) {
            val todoItem = todoList[adapterPosition]
            if (!todoItem.isDone) {
                itemView.also {
                    //update and set the status of task to done in db
                    todoItem.id?.let { id -> fragment.viewModel.setTodoDone(id) }
                }
            } else {
                itemView.also {
                    //update and set the status of task to not done in db
                    todoItem.id?.let { id -> fragment.viewModel.setTodoNotDone(id) }
                }
            }
        }
    }

}