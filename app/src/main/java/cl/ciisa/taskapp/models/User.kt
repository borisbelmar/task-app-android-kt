package cl.ciisa.taskapp.models

import java.util.*

data class User(
    val id: Long?,
    val firstname: String,
    val lastname: String,
    val email: String,
    val gender: String,
    val password: String,
    val birth: Date
)
