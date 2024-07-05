package com.example.proyectofinal

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
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

    }
}