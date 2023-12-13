package com.example.proyectoiot.models

// Se importa la interfaz Parcelable que se utiliza para pasar objetos entre actividades en Android.
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// Se declara una clase llamada Devices que implementa la interfaz Parcelable.
@Parcelize
data class Devices(
    var id: String? = null,
    var nombre: String? = null,
) : Parcelable // La clase implementa la interfaz Parcelable para poder pasar instancias de esta clase entre actividades.

