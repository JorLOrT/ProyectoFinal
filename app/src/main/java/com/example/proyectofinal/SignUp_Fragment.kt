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

class SignUp_Fragment : Fragment(R.layout.fragment_sign_up_) {

    lateinit var btn_signup: Button
    lateinit var input_email: EditText
    lateinit var input_password: EditText
    lateinit var input_confirm_password: EditText
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_signup = view.findViewById(R.id.crear_cuenta)
        input_email = view.findViewById(R.id.input_email_signup)
        input_password = view.findViewById(R.id.input_password_signup1)
        input_confirm_password = view.findViewById(R.id.input_password_signup2)
        val btn_login = view.findViewById<TextView>(R.id.texto_iniciar_sesion)
        btn_login.setOnClickListener {
            irLogin()
        }

        registrarUsuario()
    }

    fun registrarUsuario(){

        btn_signup.setOnClickListener {
            if(input_email.text.isNotEmpty() && input_password.text.isNotEmpty() && input_confirm_password.text.toString() == input_password.text.toString()){
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(input_email.text.toString(), input_password.text.toString()).addOnCompleteListener {
                    if(it.isSuccessful){
                        cambiarVista(it.result?.user?.email ?: "", ProviderType.BASIC)
                    }else{
                        mostrarAlerta()
                    }
                }
            }else{
                mostrarAlerta()
            }
        }
    }

    fun mostrarAlerta(){
        val msj = AlertDialog.Builder(this.context)
        msj.setTitle("Error")
        msj.setMessage("Error al registrarse")
        msj.setPositiveButton("Aceptar", null)
        msj.show()
    }

    private fun irLogin(){
        view?.findNavController()?.navigate(R.id.action_signUp_Fragment_to_login_Fragment)
    }

    private fun cambiarVista(email: String, provider: ProviderType){
        val resultadoClic = bundleOf("email" to email, "provider" to provider.name)
        view?.findNavController()?.navigate(R.id.action_signUp_Fragment_to_perfilFragment,resultadoClic)
    }
}