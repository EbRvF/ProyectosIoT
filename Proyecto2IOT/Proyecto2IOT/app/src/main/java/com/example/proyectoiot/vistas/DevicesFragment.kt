package com.example.proyectoiot.vistas

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoiot.Adapter.DevicesAdapter
import com.example.proyectoiot.AgregarDispositivoActivity
import com.example.proyectoiot.EditDeviceActivity
import com.example.proyectoiot.databinding.FragmentDevicesBinding
import com.example.proyectoiot.models.Devices
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DevicesFragment : Fragment() {

    // Declaración de variables miembro
    private lateinit var database: DatabaseReference
    private lateinit var binding: FragmentDevicesBinding
    private lateinit var devicesList: ArrayList<Devices>
    private lateinit var devicesRecyclerView: RecyclerView
    private lateinit var devicesAdapter: DevicesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el diseño de la interfaz de usuario
        binding = FragmentDevicesBinding.inflate(inflater, container, false)
        val view = binding.root

        // Configurar el RecyclerView
        devicesRecyclerView = binding.rvDispositivosList
        devicesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        devicesRecyclerView.setHasFixedSize(true)

        // Inicializar la lista y el adaptador
        devicesList = arrayListOf()
        devicesAdapter = DevicesAdapter(devicesList)

        // Configurar el listener del adaptador para manejar clics en elementos del RecyclerView
        devicesAdapter.setOnItemClickListener(object : DevicesAdapter.OnItemClickListener {
            override fun onDeleteClick(position: Int) {
                // Manejar clic en el botón de eliminación
                val eraseDevice = devicesList[position]
                eraseDevice.id?.let { deleteDevices(it) }
            }

            override fun onUpdateClick(position: Int) {
                // Manejar clic en el botón de actualización
                val update = devicesList[position]
                val intent = Intent(requireContext(), EditDeviceActivity::class.java)
                intent.putExtra("device", update)
                startActivity(intent)
            }
        })

        // Configurar el adaptador en el RecyclerView
        devicesRecyclerView.adapter = devicesAdapter

        // Configurar el clic del botón "Agregar"
        binding.btnAgregar.setOnClickListener {
            val intent = Intent(requireContext(), AgregarDispositivoActivity::class.java)
            startActivity(intent)
        }

        // Obtener y mostrar la lista de dispositivos desde Firebase
        getDevices()

        return view
    }

    // Función para obtener la lista de dispositivos desde Firebase
    private fun getDevices() {
        // Obtener la referencia de la base de datos
        database = FirebaseDatabase.getInstance().getReference("devices")

        // Configurar el listener para manejar los cambios en los datos
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    devicesList.clear()

                    // Recorrer los elementos en la instantánea de datos y agregarlos a la lista
                    for (deviceSnapshot in snapshot.children) {
                        val device1 = deviceSnapshot.getValue(Devices::class.java)
                        devicesList.add(device1!!)
                    }

                    // Notificar al adaptador que los datos han cambiado
                    devicesAdapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Manejar el error si es necesario
                Toast.makeText(requireContext(), "Error al obtener dispositivos", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Función para eliminar un dispositivo de la base de datos
    private fun deleteDevices(id: String) {
        // Obtener la referencia de la base de datos
        database = FirebaseDatabase.getInstance().getReference("devices")

        // Eliminar el dispositivo con el ID especificado
        database.child(id).removeValue()
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Se ha eliminado el dispositivo correctamente", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener{
                Toast.makeText(requireContext(), "No se ha podido eliminar el dispositivo", Toast.LENGTH_SHORT).show()
            }
    }

    companion object {
        @JvmStatic
        fun newInstance() = DevicesFragment()
    }
}

