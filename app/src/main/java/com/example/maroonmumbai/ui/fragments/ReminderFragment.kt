package com.example.maroonmumbai.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.maroonmumbai.R
import com.example.maroonmumbai.adapter.ReminderAdapter
import com.example.maroonmumbai.model.ReminderModelClass
import com.example.maroonmumbai.ui.activities.HomeActivity
import com.example.maroonmumbai.ui.viewmodels.HomeViewModel
import com.example.maroonmumbai.util.SwipeHelper
import kotlinx.android.synthetic.main.fragment_reminder.*
import kotlinx.android.synthetic.main.fragment_todo.*

class ReminderFragment: Fragment(R.layout.fragment_reminder) {

    lateinit var viewModel: HomeViewModel
    lateinit var adapter : ReminderAdapter
    var reminderList = ArrayList<ReminderModelClass>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = (activity as HomeActivity).viewModel

        setRecyclerView()

        viewModel.getAllReminders().observe(viewLifecycleOwner, Observer {
            reminderList = it as ArrayList<ReminderModelClass>
            adapter.differ.submitList(it)
        })

        //custom swipe helper that displays an underlay button on swipe, which deletes the reminder
        object: SwipeHelper(activity, rvReminder, true){
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
                        val reminder = adapter.differ.currentList[pos]
                        // delete from database and observer will automatically update it
                        viewModel.deleteReminder(reminder)
                        adapter.notifyItemChanged(pos)
                    }
                ))
            }

        }
    }

    private fun setRecyclerView() {
        adapter = ReminderAdapter(this)
        rvReminder.also {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(activity)
        }
    }
}