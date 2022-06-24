package cl.ciisa.taskapp.dao

import androidx.room.*
import cl.ciisa.taskapp.models.TaskEntity

@Dao
interface TaskDAO {
    @Query("SELECT * FROM tasks WHERE user_id = :userId")
    fun findAll(userId: Long): List<TaskEntity>

    @Query("SELECT * FROM tasks WHERE id = :id")
    fun findById(id: Long): TaskEntity?

    @Insert
    fun insert(task: TaskEntity)

    @Update
    fun update(task: TaskEntity)

    @Query("DELETE FROM tasks WHERE id = :id")
    fun delete(id: Long)
}