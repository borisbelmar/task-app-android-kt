package cl.ciisa.taskapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import cl.ciisa.taskapp.controllers.AuthController
import cl.ciisa.taskapp.models.User
import cl.ciisa.taskapp.utils.TilValidator
import cl.ciisa.taskapp.utils.showDatePickerDialog
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val tvToLogin = findViewById<TextView>(R.id.activity_register_tv_to_login)

        val tilFirstname = findViewById<TextInputLayout>(R.id.activity_register_til_firstname)
        val tilLastname = findViewById<TextInputLayout>(R.id.activity_register_til_lastname)
        val tilEmail = findViewById<TextInputLayout>(R.id.activity_register_til_email)
        val tilPassword = findViewById<TextInputLayout>(R.id.activity_register_til_password)
        val tilBirth = findViewById<TextInputLayout>(R.id.activity_register_til_birth)
        val spnGender = findViewById<Spinner>(R.id.activity_register_spn_gender)
        val btnRegister = findViewById<Button>(R.id.activity_register_btn_register)

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.genders_array,
            android.R.layout.simple_spinner_item
        )

        tilBirth.editText?.setOnClickListener { _ ->
            showDatePickerDialog(this, tilBirth, Date())
        }

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spnGender.adapter = adapter

        btnRegister.setOnClickListener {
            val firstname = tilFirstname.editText?.text.toString()
            val lastname = tilLastname.editText?.text.toString()
            val email = tilEmail.editText?.text.toString()
            val password = tilPassword.editText?.text.toString()
            val birth = tilBirth.editText?.text.toString()
            val gender = spnGender.selectedItem.toString()

            val firstnameValid = TilValidator(tilFirstname)
                .required()
                .isValid()

            val lastnameValid = TilValidator(tilLastname)
                .required()
                .isValid()


            val emailValid = TilValidator(tilEmail)
                .required()
                .email()
                .isValid()

            val passwordValid = TilValidator(tilPassword)
                .required()
                .isValid()

            val birthValid = TilValidator(tilBirth)
                .required()
                .dateBefore(Date())
                .isValid()

            if (emailValid && passwordValid && firstnameValid && lastnameValid && birthValid) {
                val user = User(
                    id = null,
                    firstname = firstname,
                    lastname = lastname,
                    email = email,
                    gender = gender,
                    password = password,
                    birth = SimpleDateFormat("yyyy-MM-dd").parse(birth)
                )
                AuthController(this).register(user)
            } else {
                Toast.makeText(this, "Campos inválidos", Toast.LENGTH_SHORT).show()
            }
        }

        tvToLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
    }
}