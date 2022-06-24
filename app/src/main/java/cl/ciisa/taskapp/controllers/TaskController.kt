package cl.ciisa.taskapp.controllers

import android.content.Context
import androidx.room.Room
import cl.ciisa.taskapp.lib.AppDatabase
import cl.ciisa.taskapp.models.Task

class TaskController constructor(ctx: Context, userId: Long = 0) {
    private val ctx = ctx
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
}