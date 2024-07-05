package com.example.proyectofinal

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

class Login_Fragment : Fragment(R.layout.fragment_login_) {

    lateinit var input_email: EditText
    lateinit var input_password: EditText
    lateinit var btn_login: Button
    lateinit var btn_google: Button
    lateinit var btn_signup: TextView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        input_email = view.findViewById<EditText>(R.id.input_email)
        input_password = view.findViewById<EditText>(R.id.input_password)
        btn_login = view.findViewById<Button>(R.id.logear_cuenta)
        btn_google = view.findViewById<Button>(R.id.logear_google)
        btn_signup = view.findViewById<TextView>(R.id.texto_registrarse)

        // Logeo con usuario y contraseña
        logearUsuario()
        // Navegar a la vista de registro
        irSignUp()
    }

    fun logearUsuario(){
        btn_login.setOnClickListener {
            if(input_email.text.isEmpty() || input_password.text.isEmpty()){
                mostrarAlerta("Error", "Debe completar todos los campos")
                return@setOnClickListener
            }
            else{
                FirebaseAuth.getInstance().signInWithEmailAndPassword(input_email.text.toString(), input_password.text.toString()).addOnCompleteListener {
                    if(it.isSuccessful){
                        cambiarVista(it.result?.user?.email ?: "", ProviderType.BASIC)
                    }else{
                        mostrarAlerta("Error", "Error al logearse")
                    }
                }
            }
        }
    }

    fun logerarGoogle(){
        btn_google.setOnClickListener {
            val configuracion = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()
        }
    }

    fun mostrarAlerta(tipo: String, mensaje: String){
        val msj = AlertDialog.Builder(this.context)
        msj.setTitle(tipo)
        msj.setMessage(mensaje)
        msj.setPositiveButton("Aceptar", null)
        msj.show()
    }

    private fun irSignUp(){
        btn_signup.setOnClickListener {
            view?.findNavController()?.navigateUp()
            view?.findNavController()?.navigate(R.id.action_login_Fragment_to_signUp_Fragment)
        }
    }

    private fun cambiarVista(email: String, provider: ProviderType){
        val resultadoClic = bundleOf("email" to email, "provider" to provider.name)
        view?.findNavController()?.navigate(R.id.action_login_Fragment_to_contenedor_interno, resultadoClic)
    }

}