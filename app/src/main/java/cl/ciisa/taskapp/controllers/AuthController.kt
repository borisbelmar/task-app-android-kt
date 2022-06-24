package cl.ciisa.taskapp.controllers

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.room.Room
import cl.ciisa.taskapp.LoginActivity
import cl.ciisa.taskapp.MainActivity
import cl.ciisa.taskapp.lib.AppDatabase
import cl.ciisa.taskapp.lib.BCrypt
import cl.ciisa.taskapp.models.User
import cl.ciisa.taskapp.models.UserEntity
import java.lang.Exception

class AuthController constructor(ctx: Context) {
    private val sharedPref = ctx.getSharedPreferences("TASK_APP", Context.MODE_PRIVATE)
    private val INCORRECT_CREDENTIALS = "Credenciales incorrectas"
    private val ctx = ctx
    private val dao = Room.databaseBuilder(
        ctx,
        AppDatabase::class.java, "database-name"
    )
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()
        .userDao()

    fun login(email: String, password: String) {
        val user = dao.findByEmail(email)

        if (user == null) {
            Toast.makeText(this.ctx, INCORRECT_CREDENTIALS, Toast.LENGTH_SHORT).show()
            return
        }
        if (BCrypt.checkpw(password, user.password)) {
            Toast.makeText(this.ctx, "Bienvenido ${user.firstname}", Toast.LENGTH_SHORT).show()
            val sharedEdit = sharedPref.edit()
            sharedEdit.putLong("user_id", user.id!!)
            sharedEdit.commit()

            val intent = Intent(this.ctx, MainActivity::class.java)
            this.ctx.startActivity(intent)
            (this.ctx as Activity).finish()
        } else {
            Toast.makeText(this.ctx, INCORRECT_CREDENTIALS, Toast.LENGTH_SHORT).show()
        }
    }

    fun register(user: User) {
        val hashedPassword = BCrypt.hashpw(user.password, BCrypt.gensalt())
        val userEntity = UserEntity(
            id = null,
            firstname = user.firstname,
            lastname = user.lastname,
            email = user.email,
            gender = user.gender,
            password = hashedPassword,
            birth = user.birth
        )

        try {
            dao.insert(userEntity)

            Toast.makeText(this.ctx, "Cuenta registrada", Toast.LENGTH_SHORT).show()
            val intent = Intent(this.ctx, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            this.ctx.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this.ctx, "Cuenta existente", Toast.LENGTH_SHORT).show()
        }

    }

    fun checkUserSession() {
        val id = sharedPref.getLong("user_id", -1)

        Handler().postDelayed({
            if (id == (-1).toLong()) {
                val intent = Intent(this.ctx, LoginActivity::class.java)
                this.ctx.startActivity(intent)
            } else {
                val intent = Intent(this.ctx, MainActivity::class.java)
                this.ctx.startActivity(intent)
            }
            (this.ctx as Activity).finish()
        }, 2000)
    }

    fun clearSession() {
        val editor = sharedPref.edit()
        editor.remove("user_id")
        editor.commit()
        val intent = Intent(this.ctx, LoginActivity::class.java)
        this.ctx.startActivity(intent)
        (this.ctx as Activity).finish()
    }
}
