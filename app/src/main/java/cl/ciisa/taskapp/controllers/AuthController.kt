package cl.ciisa.taskapp.controllers

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import cl.ciisa.taskapp.LoginActivity
import cl.ciisa.taskapp.MainActivity

class AuthController constructor(ctx: Context) {
    private val ctx = ctx

    fun login(email: String, password: String) {
        if (email == "pepe@gmail.com" && password == "123456") {
            Toast.makeText(this.ctx, "Bienvenido Fulanito", Toast.LENGTH_SHORT).show()
            val intent = Intent(this.ctx, MainActivity::class.java)
            this.ctx.startActivity(intent)
            (this.ctx as Activity).finish()
        } else {
            Toast.makeText(this.ctx, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
        }
    }

    fun register(firstname: String, lastname: String, email: String, password: String, gender: String, birth: String) {
        Toast.makeText(this.ctx, "Cuenta registrada", Toast.LENGTH_SHORT).show()
        val intent = Intent(this.ctx, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        this.ctx.startActivity(intent)
    }
}