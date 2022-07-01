package cl.ciisa.taskapp.controllers

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.room.Room
import retrofit2.Callback;
import cl.ciisa.taskapp.LoginActivity
import cl.ciisa.taskapp.MainActivity
import cl.ciisa.taskapp.lib.AppDatabase
import cl.ciisa.taskapp.lib.BCrypt
import cl.ciisa.taskapp.lib.RetrofitClient
import cl.ciisa.taskapp.models.*
import cl.ciisa.taskapp.services.AuthService
import retrofit2.Call
import retrofit2.Response
import java.lang.Exception

class AuthController constructor(ctx: Context) {
    private val sharedPref = ctx.getSharedPreferences("TASK_APP", Context.MODE_PRIVATE)
    private val INCORRECT_CREDENTIALS = "Credenciales incorrectas"
    private val ctx = ctx
    private val retrofit = RetrofitClient.getRetrofitInstance()
    private val authService = retrofit.create(AuthService::class.java)

    private val dao = Room.databaseBuilder(
        ctx,
        AppDatabase::class.java, "database-name"
    )
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()
        .userDao()

    fun login(email: String, password: String) {
        val loginPayload = LoginPayloadDTO(email, password)
        val call = authService.login(loginPayload)

        call.enqueue(object : Callback<LoginResponseDTO> {
            override fun onFailure(call: Call<LoginResponseDTO>, t: Throwable) {
                Toast.makeText(ctx, "Error de conexión", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<LoginResponseDTO>,
                response: Response<LoginResponseDTO>
            ) {
                if (response.code() != 200) {
                    Toast.makeText(ctx, INCORRECT_CREDENTIALS, Toast.LENGTH_SHORT).show()
                } else {
                    val bodyResponse = response.body()
                    Toast.makeText(ctx, "Bienvenido ${bodyResponse?.user?.id}", Toast.LENGTH_SHORT).show()
                    val sharedEdit = sharedPref.edit()
                    sharedEdit.putLong("user_id", bodyResponse?.user?.id!!)
                    sharedEdit.putString("user_jwt", bodyResponse?.jwt!!)
                    sharedEdit.commit()

                    val intent = Intent(ctx, MainActivity::class.java)
                    ctx.startActivity(intent)
                    (ctx as Activity).finish()
                }
            }
        })
    }

    fun register(user: User) {
        val registerPayload = RegisterPayloadDTO(
            user.email,
            user.email,
            user.password
        )

        val call = authService.register(registerPayload)

        call.enqueue(object : Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(ctx, "Error de conexión", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<Void>,
                response: Response<Void>
            ) {
                if (response.code() != 200) {
                    Toast.makeText(ctx, "Cuenta existente", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(ctx, "Cuenta registrada", Toast.LENGTH_SHORT).show()
                    val intent = Intent(ctx, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    ctx.startActivity(intent)
                }
            }
        })
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

    fun getSessionUserId(): Long {
        return sharedPref.getLong("user_id", -1)
    }

    fun clearSession() {
        val editor = sharedPref.edit()
        editor.remove("user_id")
        editor.remove("user_jwt")
        editor.commit()
        val intent = Intent(this.ctx, LoginActivity::class.java)
        this.ctx.startActivity(intent)
        (this.ctx as Activity).finish()
    }
}
