package com.example.proyectofinal

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val pantallaSplash = installSplashScreen()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Thread.sleep(1000)
        pantallaSplash.setKeepOnScreenCondition{false}

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val navFragment = supportFragmentManager.findFragmentById(R.id.contenedor_fragment) as NavHostFragment

        NavigationUI.setupWithNavController(bottomNavigationView, navFragment.navController)

        // Para ocultar la barra inferior de navegación en el fragment de perfil y en el de registro
        navFragment.findNavController().addOnDestinationChangedListener { _, destination, _ ->
            bottomNavigationView.visibility = if(destination.id == R.id.login_Fragment || destination.id == R.id.signUp_Fragment) {
                View.GONE
            } else {
                View.VISIBLE
            }
        }

    }

}