package cl.ciisa.taskapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import cl.ciisa.taskapp.controllers.TaskController
import cl.ciisa.taskapp.ui.TaskAdapter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val lvTasks = findViewById<ListView>(R.id.activity_main_lv_tasks)

        val allTasks = TaskController(this).getAll()
        val adapter = TaskAdapter(this, allTasks)

        lvTasks.adapter = adapter
        lvTasks.setOnItemClickListener { adapterView, view, i, l ->
            run {
                val task = allTasks[i]
                val intent = Intent(view.context, TaskItemActivity::class.java)
                intent.putExtra("task", task)
                view.context.startActivity(intent)
            }
        }
    }
}