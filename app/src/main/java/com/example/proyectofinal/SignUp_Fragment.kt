package com.example.proyectofinal

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignUp_Fragment : Fragment(R.layout.fragment_sign_up_) {

    lateinit var btn_signup: Button
    lateinit var input_email: EditText
    lateinit var input_password: EditText
    lateinit var input_confirm_password: EditText
    lateinit var btn_login: TextView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_signup = view.findViewById(R.id.crear_cuenta)
        input_email = view.findViewById(R.id.input_email_signup)
        input_password = view.findViewById(R.id.input_password_signup1)
        input_confirm_password = view.findViewById(R.id.input_password_signup2)
        btn_login = view.findViewById<TextView>(R.id.texto_iniciar_sesion)

        irLogin()
        registrarUsuario()
    }

    fun registrarUsuario(){
        btn_signup.setOnClickListener {
            if(input_email.text.isEmpty() || input_password.text.isEmpty() || input_confirm_password.text.isEmpty()){
                mostrarAlerta("Error", "Debe completar todos los campos")
                return@setOnClickListener
            }
            else if(input_password.text.toString() != input_confirm_password.text.toString() ){
                mostrarAlerta("Error", "Las passwords no coinciden")
                return@setOnClickListener
            }
            else{
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(input_email.text.toString(), input_password.text.toString()).addOnCompleteListener {
                    if(it.isSuccessful){
                        cambiarVista(it.result?.user?.email ?: "")
                    }else{
                        mostrarAlerta("Error", "Error al registrarse")
                    }
                }
            }
        }
    }

    fun mostrarAlerta(tipo: String, mensaje: String){
        val msj = AlertDialog.Builder(this.context)
        msj.setTitle(tipo)
        msj.setMessage(mensaje)
        msj.setPositiveButton("Aceptar", null)
        msj.show()
    }

    private fun irLogin(){
        btn_login.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_signUp_Fragment_to_login_Fragment)
        }
    }

    private fun cambiarVista(email: String){
        val db = FirebaseFirestore.getInstance()
        val usuario = hashMapOf("email" to email, "provider" to "Administrador")
        // Para poder controlar la primary key
        db.collection("users").document(email).set(usuario).
        addOnSuccessListener{
            Log.d("Insertado", "Insertado Correcto")
        }.addOnFailureListener{
            Log.d("Error", "Error al insertar")
        }
        view?.findNavController()?.navigate(R.id.action_signUp_Fragment_to_perfilFragment)
    }
}