package com.example.proyectoiot

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectoiot.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inicializar el enlace de diseño utilizando el enlace de datos
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializar la instancia de FirebaseAuth
        auth = Firebase.auth

        // Configurar el listener del botón de inicio de sesión
        binding.btnLogin.setOnClickListener {
            validarCredenciales()
        }
    }

    // Método para validar las credenciales del usuario antes de iniciar sesión
    private fun validarCredenciales() {
        // Obtener el correo y la contraseña desde los campos de entrada de texto
        val correo = binding.etEmail.text.toString()
        val contrasena = binding.etPassword.text.toString()

        // Validar que el correo no esté vacío
        if (correo.isEmpty()) {
            binding.etEmail.error = "Ingrese Correo"
            return
        }
        // Validar que la contraseña no esté vacía
        if (contrasena.isEmpty()) {
            binding.etPassword.error = "Ingrese una contraseña"
            return
        }

        // Si las credenciales son válidas, intentar iniciar sesión
        signIn(correo, contrasena)
    }

    // Método para realizar el inicio de sesión utilizando Firebase Auth
    private fun signIn(email: String, contrasena: String) {
        try {
            auth.signInWithEmailAndPassword(email, contrasena)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Mostrar mensaje de inicio de sesión exitoso
                        Toast.makeText(this, "Inicio de sesión correcto", Toast.LENGTH_LONG).show()
                        // Redirigir a la actividad MainActivity2 después de un inicio de sesión exitoso
                        val intent = Intent(this, MainActivity2::class.java)
                        startActivity(intent)
                    } else {
                        // Mostrar mensaje de error si el inicio de sesión falla
                        Toast.makeText(this, "ERROR", Toast.LENGTH_LONG).show()
                        // Si el inicio de sesión falla, mostrar una alerta
                        mostrarAlerta()
                    }
                }
        } catch (e: Exception) {
            // Manejar la excepción general aquí
            Toast.makeText(this, "Error al intentar iniciar sesión", Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    // Método para mostrar una alerta en caso de inicio de sesión fallido
    private fun mostrarAlerta() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Usuario y/o contraseña incorrecta")
        builder.setPositiveButton("OK", null)
        val dialog = builder.create()
        dialog.show()
    }
}

