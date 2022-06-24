package cl.ciisa.taskapp.lib

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import cl.ciisa.taskapp.dao.TaskDAO
import cl.ciisa.taskapp.dao.UserDAO
import cl.ciisa.taskapp.models.TaskEntity
import cl.ciisa.taskapp.models.UserEntity
import cl.ciisa.taskapp.utils.Converters

@Database(entities = [UserEntity::class, TaskEntity::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDAO
    abstract fun taskDao(): TaskDAO

    companion object {
        const val DATABASE_NAME = "task-app"
    }
}