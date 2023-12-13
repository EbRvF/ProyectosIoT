package com.example.proyectoiot

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectoiot.databinding.ActivityAgregarDispositivoBinding
import com.example.proyectoiot.models.Devices
import com.example.proyectoiot.vistas.DevicesFragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AgregarDispositivoActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var binding: ActivityAgregarDispositivoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflar el diseño de la actividad utilizando el enlace generado por ViewBinding
        binding = ActivityAgregarDispositivoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtener la referencia a la base de datos Firebase
        database = FirebaseDatabase.getInstance().getReference("devices")

        // Configurar el clic del botón Guardar
        binding.btnGuardar.setOnClickListener {
            // Obtener el nombre del dispositivo desde el campo de texto
            val nombre = binding.etNombreDispositivo.text.toString()

            // Generar una clave única para el dispositivo en la base de datos
            val id = database.child("devices").push().key

            // Verificar si el nombre está vacío y mostrar un error si es necesario
            if (nombre.isEmpty()) {
                binding.etNombreDispositivo.error = "Por favor ingresar nombre"
                return@setOnClickListener
            }

            // Crear un objeto Devices con el ID y el nombre
            val device = Devices(id, nombre)

            // Guardar el dispositivo en la base de datos Firebase
            database.child(id!!).setValue(device)
                .addOnSuccessListener {
                    // Limpiar el campo de texto y mostrar un mensaje de éxito
                    binding.etNombreDispositivo.setText("")
                    Snackbar.make(binding.root, "Dispositivo Agregado", Snackbar.LENGTH_SHORT)
                        .show()
                }
        }

        // Configurar el clic del botón Ver
        binding.btnVer.setOnClickListener {
            // Crear un intent para abrir la actividad DevicesFragment
            val intent = Intent(this, DevicesFragment::class.java)
            startActivity(intent)
        }
    }
}
