package com.example.maroonmumbai.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.maroonmumbai.R
import com.example.maroonmumbai.util.SwipeHelper
import com.example.maroonmumbai.adapter.TodoAdapter
import com.example.maroonmumbai.model.TodoModelClass
import com.example.maroonmumbai.ui.activities.HomeActivity
import com.example.maroonmumbai.ui.viewmodels.HomeViewModel
import kotlinx.android.synthetic.main.fragment_todo.*

class ToDoFragment: Fragment(R.layout.fragment_todo) {

    lateinit var viewModel: HomeViewModel
    lateinit var todoAdapter: TodoAdapter
    var todoList = ArrayList<TodoModelClass>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // attach viewmodel in context to HomeActivity
        viewModel = (activity as HomeActivity).viewModel

        setUpRecyclerView()

        //access all existing tasks from db and observe them
        viewModel.getAllTodos().observe(viewLifecycleOwner, Observer {
            todoList = it as ArrayList<TodoModelClass>
            todoAdapter.differ.submitList(it)
        })

        //custom swipe helper that displays an underlay button on swipe, which deletes the task
        object: SwipeHelper(activity, rvToDO, true){
            override fun instantiateUnderlayButton(
                viewHolder: RecyclerView.ViewHolder?,
                underlayButtons: MutableList<UnderlayButton>?
            ) {
                underlayButtons?.add(UnderlayButton(
                    "Delete",
                    activity?.let {
                        AppCompatResources.getDrawable(
                            it.applicationContext,
                            R.drawable.ic_delete
                        )
                    },
                    Color.parseColor("#FFF44336"), Color.parseColor("#ffffff"),
                    UnderlayButtonClickListener { pos: Int ->
                        // get the specific task from adapter
                        val todo = todoAdapter.differ.currentList[pos]
                        // delete from database and observer will automatically update it
                        viewModel.deleteTodo(todo)
                        todoAdapter.notifyItemChanged(pos)
                    }
                ))
            }

        }
    }

    private fun setUpRecyclerView(){
        todoAdapter = TodoAdapter(this)
        rvToDO.also {
            it.adapter = todoAdapter
            it.layoutManager = LinearLayoutManager(activity)
        }
    }

}