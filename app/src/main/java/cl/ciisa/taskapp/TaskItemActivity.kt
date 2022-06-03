package cl.ciisa.taskapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import cl.ciisa.taskapp.models.Task

class TaskItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_item)

        val task = intent.getSerializableExtra("task") as Task

        val tvTitle = findViewById<TextView>(R.id.activity_task_item_tv_title)

        tvTitle.text = task.title

    }
}