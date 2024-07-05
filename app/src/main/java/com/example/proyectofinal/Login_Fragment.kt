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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Login_Fragment : Fragment(R.layout.fragment_login_) {

    lateinit var input_email: EditText
    lateinit var input_password: EditText
    lateinit var btn_login: Button
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        input_email = view.findViewById<EditText>(R.id.input_email)
        input_password = view.findViewById<EditText>(R.id.input_password)
        btn_login = view.findViewById<Button>(R.id.logear_cuenta)
        val btn_signup = view.findViewById<TextView>(R.id.texto_registrarse)
        btn_signup.setOnClickListener {
            irSignUp()
        }
        logearUsuario()
    }

    fun logearUsuario(){

        btn_login.setOnClickListener {
            if(input_email.text.isNotEmpty() && input_password.text.isNotEmpty()){
                FirebaseAuth.getInstance().signInWithEmailAndPassword(input_email.text.toString(), input_password.text.toString()).addOnCompleteListener {
                    if(it.isSuccessful){
                        cambiarVista(it.result?.user?.email ?: "", ProviderType.BASIC)
                    }else{
                        mostrarAlerta()
                    }
                }
            }else{
                alertaCampos()
            }

        }
    }

    fun mostrarAlerta(){
        val msj = AlertDialog.Builder(this.context)
        msj.setTitle("Error")
        msj.setMessage("Usuario o Password incorrectos")
        msj.setPositiveButton("Aceptar", null)
        msj.show()
    }

    fun alertaCampos(){
        val msj = AlertDialog.Builder(this.context)
        msj.setTitle("Error")
        msj.setMessage("Debe llenar todos los campos")
        msj.setPositiveButton("Aceptar", null)
        msj.show()
    }

    private fun irSignUp(){
        view?.findNavController()?.navigate(R.id.action_login_Fragment_to_signUp_Fragment)
    }

    private fun cambiarVista(email: String, provider: ProviderType){
        val resultadoClic = bundleOf("email" to email, "provider" to provider.name)
        view?.findNavController()?.navigate(R.id.action_login_Fragment_to_perfilFragment)
    }

}