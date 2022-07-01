package cl.ciisa.taskapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.TextView
import cl.ciisa.taskapp.controllers.AuthController
import cl.ciisa.taskapp.controllers.TaskController
import cl.ciisa.taskapp.models.Task
import com.google.android.material.textfield.TextInputLayout

class TaskFormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_form)

        val authController = AuthController(this)
        val taskController = TaskController(this, authController.getSessionUserId())

        val task = intent.getSerializableExtra("task") as Task?

        val tvTitle = findViewById<TextView>(R.id.activity_task_form_tv_title)
        val tilTitle = findViewById<TextInputLayout>(R.id.activity_task_form_til_title)
        val tilDescription = findViewById<TextInputLayout>(R.id.activity_task_form_til_description)
        val btnSubmit = findViewById<Button>(R.id.activity_task_form_btn_submit)
        val tvBack = findViewById<TextView>(R.id.activity_task_form_tv_back)

        if (task != null) {
            tvTitle.text = "Editando tarea ${task.id}"
            tilTitle.editText?.text = Editable.Factory.getInstance().newEditable(task.title)
            tilDescription.editText?.text = Editable.Factory.getInstance().newEditable(task.description)
        }

        btnSubmit.setOnClickListener {
            if (task == null) {
                val newTask = Task(
                    id = null,
                    title = tilTitle.editText?.text.toString(),
                    description = tilDescription.editText?.text.toString()
                )
                taskController.create(newTask)
            } else {
                val updatedTask = Task(
                    id = task.id,
                    title = tilTitle.editText?.text.toString(),
                    description = tilDescription.editText?.text.toString()
                )
                taskController.update(updatedTask)
            }
        }

        tvBack.setOnClickListener {
           super.onBackPressed()
        }
    }
}