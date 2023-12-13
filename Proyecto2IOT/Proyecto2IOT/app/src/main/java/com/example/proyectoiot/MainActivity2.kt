package com.example.proyectoiot

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectoiot.databinding.ActivityMain2Binding
import com.example.proyectoiot.vistas.AccountFragment
import com.example.proyectoiot.vistas.ControllerFragment
import com.example.proyectoiot.vistas.DevicesFragment

class MainActivity2 : AppCompatActivity() {

    // Declaración de la propiedad binding para manejar la vinculación de vistas
    private lateinit var binding: ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflar el diseño usando la clase de enlace generada
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Verificar si la actividad se está creando por primera vez
        if (savedInstanceState == null) {
            // Iniciar la transacción del fragmento de cuenta al contenedor (frameLayout)
            supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, AccountFragment()).commit()
        }

        // Configuración del listener para los elementos de la barra de navegación
        binding.buttonNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.devices -> {
                    // Reemplazar el contenido del contenedor con el fragmento de dispositivos
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, DevicesFragment()).commit()
                    true
                }

                R.id.control -> {
                    // Reemplazar el contenido del contenedor con el fragmento de control
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, ControllerFragment()).commit()
                    true
                }

                R.id.account -> {
                    // Reemplazar el contenido del contenedor con el fragmento de cuenta
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, AccountFragment()).commit()
                    true
                }

                else -> false
            }
        }

        // Configuración del listener para los elementos de la barra de navegación (cuando se vuelve a seleccionar)
        binding.buttonNav.setOnItemReselectedListener {
            when (it.itemId) {
                R.id.devices -> Toast.makeText(this, "Ya estás en la vista dispositivos", Toast.LENGTH_SHORT).show()
                R.id.control -> Toast.makeText(this, "Ya estás en la vista control", Toast.LENGTH_SHORT).show()
                R.id.account -> Toast.makeText(this, "Ya estás en la vista cuenta", Toast.LENGTH_SHORT).show()
                else -> false
            }
        }
    }

    // Método para agregar un dispositivo (puede ser llamado desde la interfaz de usuario)
    fun addDevice(view: View) {
        // Implementa la lógica para agregar un dispositivo
    }
}
