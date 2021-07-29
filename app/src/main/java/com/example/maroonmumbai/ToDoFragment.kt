package com.example.maroonmumbai

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_todo.*

class ToDoFragment: Fragment(R.layout.fragment_todo) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val todoList = ArrayList<TodoModelClass>()
        todoList.add(TodoModelClass("Groceries", "pick up Groceries", false, "H",""))
        todoList.add(TodoModelClass("Study", "study for JEE", false, "M",""))
        todoList.add(TodoModelClass("Sleep", "get 7 hour sleep", false, "L",""))

        val adapter = TodoAdapter(todoList)

        rvToDO.also {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(activity)
        }

        object: SwipeHelper(activity, rvToDO, true){
            override fun instantiateUnderlayButton(
                viewHolder: RecyclerView.ViewHolder?,
                underlayButtons: MutableList<UnderlayButton>?
            ) {
                underlayButtons?.add(SwipeHelper.UnderlayButton(
                    "Delete",
                    activity?.let {
                        AppCompatResources.getDrawable(
                            it.applicationContext,
                            R.drawable.ic_delete
                        )
                    },
                    Color.parseColor("#FFF44336"), Color.parseColor("#ffffff"),
                    UnderlayButtonClickListener { pos: Int ->
                        todoList.removeAt(pos)
                        adapter.notifyItemRemoved(pos)
                    }
                ))
            }

        }
    }

}