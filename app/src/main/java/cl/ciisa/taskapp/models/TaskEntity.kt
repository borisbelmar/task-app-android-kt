package cl.ciisa.taskapp.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskEntity (
    @PrimaryKey(autoGenerate = true) val id: Long?,
    val title: String,
    val description: String?,
    val done: Boolean = false,
    @ColumnInfo(name = "user_id") val userId: Long
)