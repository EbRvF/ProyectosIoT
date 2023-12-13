package com.example.proyectoiot

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectoiot.databinding.ActivityEditDeviceBinding
import com.example.proyectoiot.models.Devices
import com.example.proyectoiot.vistas.DevicesFragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.FirebaseDatabase

class EditDeviceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditDeviceBinding
    private var deviceToUpdate: Devices? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflar el diseño de la actividad utilizando el enlace de datos generado
        binding = ActivityEditDeviceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtener el objeto Devices enviado desde la actividad anterior
        deviceToUpdate = intent.getParcelableExtra("device")

        // Mostrar los detalles del dispositivo en la interfaz de usuario
        deviceToUpdate?.let { showDeviceDetails(it) }

        // Configurar el botón de guardar para actualizar el dispositivo
        binding.btnGuardar.setOnClickListener {
            // Obtener los nuevos valores ingresados por el usuario
            val newName = binding.etNombreDispositivo.text.toString()

            // Actualizar los valores del dispositivo con los nuevos valores
            deviceToUpdate?.apply {
                nombre = newName
            }

            // Actualizar los datos en la base de datos Firebase
            deviceToUpdate?.id?.let { id ->
                val databaseReference = FirebaseDatabase.getInstance().getReference("devices")
                databaseReference.child(id).setValue(deviceToUpdate)
                    .addOnSuccessListener {
                        // Mostrar mensaje de éxito en una Snackbar
                        Snackbar.make(binding.root, "Dispositivo Editado con éxito", Snackbar.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        // Mostrar mensaje de error en una Snackbar en caso de falla
                        Snackbar.make(binding.root, "Error 404, no se pudo editar el dispositivo", Snackbar.LENGTH_SHORT).show()
                    }
            }
        }

        // Configurar el botón para ver la lista de dispositivos
        binding.btnVer.setOnClickListener {
            // Crear un Intent para abrir la actividad de la lista de dispositivos
            val intent = Intent(this, DevicesFragment::class.java)
            startActivity(intent)
        }
    }

    // Método para mostrar los detalles del dispositivo en la interfaz de usuario
    private fun showDeviceDetails(device: Devices) {
        // Mostrar el nombre del dispositivo en el campo de texto correspondiente
        binding.etNombreDispositivo.setText(device.nombre)
    }
}
