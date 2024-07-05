package com.example.proyectofinal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView

class ContenedorInterno : Fragment(R.layout.fragment_contenedor_interno) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomNavigationView = view.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val navFragment = childFragmentManager.findFragmentById(R.id.contenedor_interno_fragment) as NavHostFragment

        NavigationUI.setupWithNavController(bottomNavigationView, navFragment.navController)
    }
}