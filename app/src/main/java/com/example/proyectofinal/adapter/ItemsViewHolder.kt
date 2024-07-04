package com.example.proyectofinal.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinal.Item
import com.example.proyectofinal.R

class ItemsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    private val viewNombre = itemView.findViewById<TextView>(R.id.txtNombre)
    val imagen = itemView.findViewById<ImageView>(R.id.image_item)

    fun render(item:Item, onClickListener: (Item) ->Unit){
        viewNombre.text = item.nombre
        itemView.setOnClickListener { onClickListener(item) }
        val eleccion = item.imagen
        when(eleccion){
            "avatar_1" -> imagen.setImageResource(R.drawable.profile_pic)
            "avatar_2" -> imagen.setImageResource(R.drawable.profile_pic)
            "avatar_3" -> imagen.setImageResource(R.drawable.profile_pic)
        }
    }
}