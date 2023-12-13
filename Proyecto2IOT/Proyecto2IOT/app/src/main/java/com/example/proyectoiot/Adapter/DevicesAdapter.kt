package com.example.proyectoiot.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoiot.R
import com.example.proyectoiot.models.Devices

// El adaptador recibe una lista de dispositivos (clase Devices)
class DevicesAdapter(private val dispositivos: ArrayList<Devices>) :
    RecyclerView.Adapter<DevicesAdapter.ViewHolder>() {

    // Interfaz para manejar clics en los elementos del RecyclerView
    interface OnItemClickListener {
        fun onDeleteClick(position: Int)
        fun onUpdateClick(position: Int)
    }

    // Variable para almacenar el listener de clic
    private var mListener: OnItemClickListener? = null

    // Método para establecer el listener
    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    // Clase interna que representa un elemento de la vista en el RecyclerView
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Elementos de la vista que se mostrarán en cada elemento del RecyclerView
        val nombre: TextView = itemView.findViewById(R.id.tvNombre)
        val deleteDevice: Button = itemView.findViewById(R.id.btnEliminaritem)
        val updateDevice: Button = itemView.findViewById(R.id.btnEditarItem)
    }

    // Método que se llama cuando se necesita crear una nueva vista
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Infla el diseño de la vista de cada elemento del RecyclerView
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.deviceitems, parent, false)
        // Retorna una instancia de ViewHolder que contiene la vista inflada
        return ViewHolder(view)
    }

    // Método que devuelve la cantidad de elementos en el conjunto de datos
    override fun getItemCount(): Int {
        return dispositivos.size
    }

    // Método que se llama para mostrar datos en una vista específica
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Obtiene el dispositivo en la posición dada
        val devices = dispositivos[position]
        // Establece el nombre del dispositivo en el TextView
        holder.nombre.text = devices.nombre

        // Configura el clic en el botón de eliminar
        holder.deleteDevice.setOnClickListener {
            mListener?.onDeleteClick(position)
        }

        // Configura el clic en el botón de editar
        holder.updateDevice.setOnClickListener {
            mListener?.onUpdateClick(position)
        }
    }
}

