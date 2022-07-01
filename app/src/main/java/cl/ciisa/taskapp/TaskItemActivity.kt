package cl.ciisa.taskapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import cl.ciisa.taskapp.controllers.AuthController
import cl.ciisa.taskapp.controllers.TaskController
import cl.ciisa.taskapp.models.Task

class TaskItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_item)

        val authController = AuthController(this)
        val taskController = TaskController(this, authController.getSessionUserId())

        val task = intent.getSerializableExtra("task") as Task

        val tvTitle = findViewById<TextView>(R.id.activity_task_item_tv_title)
        val tvDescription = findViewById<TextView>(R.id.activity_task_item_tv_description)
        val btnUpdate = findViewById<TextView>(R.id.activity_task_item_btn_update)
        val btnDelete = findViewById<TextView>(R.id.activity_task_item_btn_delete)

        tvTitle.text = task.title
        tvDescription.text = task.description

        btnUpdate.setOnClickListener {
            val intent = Intent(this, TaskFormActivity::class.java)
            intent.putExtra("task", task)
            startActivity(intent)
        }

        btnDelete.setOnClickListener {
            taskController.delete(task.id!!)
        }
    }
}