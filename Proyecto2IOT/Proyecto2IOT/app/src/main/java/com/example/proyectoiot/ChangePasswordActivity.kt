package com.example.proyectoiot

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectoiot.databinding.ActivityAgregarDispositivoBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var etNewPass: TextInputEditText
    private lateinit var etReNewPass: TextInputEditText
    private lateinit var binding: ActivityAgregarDispositivoBinding

    // Instancia de FirebaseAuth para manejar la autenticación
    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        // Inicialización de las vistas
        etNewPass = findViewById(R.id.etNewPass)
        etReNewPass = findViewById(R.id.etReNewPass)

        // Botón para cambiar la contraseña
        val btnChangePassword = findViewById<Button>(R.id.btnChangePassword)
        btnChangePassword.setOnClickListener {
            changePassword()
        }
    }

    // Función para cambiar la contraseña
    private fun changePassword() {
        // Obtención de las contraseñas desde los campos de texto
        val newPassword = etNewPass.text.toString()
        val reEnteredPassword = etReNewPass.text.toString()

        // Verificación de campos vacíos
        if (newPassword.isEmpty() || reEnteredPassword.isEmpty()) {
            Snackbar.make(binding.root, "Completa los 2 campos", Snackbar.LENGTH_SHORT).show()
            return
        }

        // Verificación de coincidencia de contraseñas
        if (newPassword != reEnteredPassword) {
            Snackbar.make(binding.root, "Las contraseñas no coinciden", Snackbar.LENGTH_SHORT).show()
            return
        }

        // Obtención del usuario actual
        val user = firebaseAuth.currentUser

        // Actualización de la contraseña del usuario
        user?.updatePassword(newPassword)
            ?.addOnCompleteListener { task ->
                // Manejo del resultado de la actualización
                if (task.isSuccessful) {
                    Snackbar.make(binding.root, "Contraseña cambiada con éxito", Snackbar.LENGTH_SHORT).show()
                } else {
                    Snackbar.make(binding.root, "No se ha podido cambiar la contraseña", Snackbar.LENGTH_SHORT).show()
                }
            }
    }
}

