package com.example.proyectoiot.vistas

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.proyectoiot.ChangePasswordActivity
import com.example.proyectoiot.MainActivity
import com.example.proyectoiot.R
import com.google.firebase.auth.FirebaseAuth

class AccountFragment : Fragment() {

    // Instancia de Firebase Authentication
    private val firebaseAuth = FirebaseAuth.getInstance()

    private var param1: String? = null
    private var param2: String? = null

    // Llamado cuando se crea el fragmento
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Recupera los argumentos si los hay
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    // Llamado para crear la vista del fragmento
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infla el diseño para este fragmento
        val view = inflater.inflate(R.layout.fragment_account, container, false)

        // Encuentra el botón "Cambiar Contraseña" por su ID
        val btnChangePassword = view.findViewById<Button>(R.id.newPassword)
        // Establece un listener de clic para el botón
        btnChangePassword.setOnClickListener {
            // Crea un intento para iniciar ChangePasswordActivity
            val intent = Intent(activity, ChangePasswordActivity::class.java)
            // Inicia la actividad
            startActivity(intent)
        }

        // Encuentra el botón "Cerrar Sesión" por su ID
        val btnLogout = view.findViewById<Button>(R.id.logout)
        // Establece un listener de clic para el botón
        btnLogout.setOnClickListener {
            // Cierra la sesión en Firebase Authentication
            firebaseAuth.signOut()

            // Crea un intento para iniciar MainActivity
            val mainIntent = Intent(activity, MainActivity::class.java)
            // Inicia la actividad
            startActivity(mainIntent)
            // Finaliza la actividad actual (la actividad contenedora del fragmento)
            activity?.finish()
        }

        // Devuelve la vista preparada
        return view
    }

    // Objeto compañero para crear una nueva instancia del fragmento
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AccountFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}






