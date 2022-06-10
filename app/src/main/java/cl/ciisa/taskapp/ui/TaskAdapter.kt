package cl.ciisa.taskapp.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import cl.ciisa.taskapp.R
import cl.ciisa.taskapp.models.Task

class TaskAdapter(private val ctx: Context, private val tasks: List<Task>) : BaseAdapter() {
    override fun getCount(): Int {
        return tasks.size
    }

    override fun getItem(i: Int): Task {
        return tasks[i]
    }

    override fun getItemId(i: Int): Long {
        return tasks[i].id!!
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup?): View {
        val inflater = LayoutInflater.from(ctx)

        val rowView = inflater.inflate(R.layout.item_task, null)

        val task = tasks[i]

        val tvTitle = rowView.findViewById<TextView>(R.id.item_task_tv_title)
        val tvId = rowView.findViewById<TextView>(R.id.item_task_tv_id)
        val tvDescription = rowView.findViewById<TextView>(R.id.item_task_tv_description)

        tvTitle.text = task.title
        tvId.text = task.id.toString()
        tvDescription.text = task.description

        return rowView
    }


}