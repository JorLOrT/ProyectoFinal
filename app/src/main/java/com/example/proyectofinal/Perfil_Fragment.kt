package com.example.proyectofinal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth

class PerfilFragment : Fragment(R.layout.fragment_perfil_) {


    lateinit var btnSalir: Button
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val nombre = view.findViewById<TextView>(R.id.nombreUsuario)
        val competencias = view.findViewById<TextView>(R.id.competencias)
        btnSalir = view.findViewById(R.id.cerrarSesion)

        arguments?.let {bundle->
            nombre.text = bundle.getString("email").toString()
            competencias.text = bundle.getString("provider").toString()
        }

        btnSalir.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            findNavController().navigate(R.id.action_perfilFragment_to_login_Fragment)
        }
    }
}