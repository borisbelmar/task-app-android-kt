package cl.ciisa.taskapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import cl.ciisa.taskapp.controllers.AuthController
import cl.ciisa.taskapp.utils.TilValidator
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val tvToRegister = findViewById<TextView>(R.id.activity_login_tv_to_register)
        val btnLogin = findViewById<Button>(R.id.activity_login_btn_login)
        val tilEmail = findViewById<TextInputLayout>(R.id.activity_login_til_email)
        val tilPassword = findViewById<TextInputLayout>(R.id.activity_login_til_password)

        btnLogin.setOnClickListener {
            val email = tilEmail.editText?.text.toString()
            val password = tilPassword.editText?.text.toString()

            val emailValid = TilValidator(tilEmail)
                .required()
                .email()
                .isValid()

            val passwordValid = TilValidator(tilPassword)
                .required()
                .isValid()

            if (emailValid && passwordValid) {
                AuthController(this).login(email, password)
            } else {
                Toast.makeText(this, "Campos inválidos", Toast.LENGTH_SHORT).show()
            }
        }

        tvToRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}