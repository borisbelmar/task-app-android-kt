package cl.ciisa.taskapp.controllers

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.room.Room
import cl.ciisa.taskapp.MainActivity
import cl.ciisa.taskapp.lib.AppDatabase
import cl.ciisa.taskapp.models.Task
import cl.ciisa.taskapp.models.TaskEntity

class TaskController constructor(ctx: Context, userId: Long = 0) {
    private val ctx = ctx
    private val sharedPref = ctx.getSharedPreferences("TASK_APP", Context.MODE_PRIVATE)
    private val userId = userId
    private val dao = Room.databaseBuilder(
        ctx,
        AppDatabase::class.java, "database-name"
    )
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()
        .taskDao()

    fun getAll (): List<Task> {
        val taskEntities = dao.findAll(userId)

        val tasks = ArrayList<Task>()

        taskEntities.forEach { task -> tasks.add(Task(
            id = task.id,
            title = task.title,
            description = task.description,
            done = task.done
        )) }

        return tasks
    }

    fun getById(id: Long): Task? {
        val entity = dao.findById(id) ?: return null

        return Task(
            id = entity?.id,
            title = entity.title,
            description = entity.description,
            done = entity.done
        )
    }

    fun create (task: Task) {
        val entity = TaskEntity(
            id = null,
            title = task.title,
            description = task.description,
            done = task.done,
            userId = sharedPref.getLong("user_id", -1)
        )
        dao.insert(entity)

        val intent = Intent(ctx, MainActivity::class.java)
        ctx.startActivity(intent)
        (this.ctx as Activity).finish()
    }
}