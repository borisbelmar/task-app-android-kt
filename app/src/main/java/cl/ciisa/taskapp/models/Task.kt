package cl.ciisa.taskapp.models

import java.io.Serializable

data class Task(
    val id: Long?,
    val title: String,
    val description: String?,
    val done: Boolean = false
) : Serializable
