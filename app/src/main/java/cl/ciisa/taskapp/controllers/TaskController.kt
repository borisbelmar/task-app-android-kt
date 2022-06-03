package cl.ciisa.taskapp.controllers

import android.content.Context
import cl.ciisa.taskapp.models.Task

class TaskController constructor(ctx: Context) {
    private val ctx = ctx

    fun getAll (): List<Task> {
        val tasks = ArrayList<Task>()
        for (i in 1..30) {
            tasks.add(Task(
                id = i.toLong(),
                title = "Tarea $i",
                description = "Desc $i"
            ))
        }
        return tasks
    }
}