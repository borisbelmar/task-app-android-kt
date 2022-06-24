package cl.ciisa.taskapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import cl.ciisa.taskapp.models.UserEntity

@Dao
interface UserDAO {
    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    fun findByEmail(email: String): UserEntity?

    @Insert
    fun insert(user: UserEntity)
}