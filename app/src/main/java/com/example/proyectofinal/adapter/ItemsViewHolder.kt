package com.example.proyectofinal.adapter

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinal.Item
import com.example.proyectofinal.R

class ItemsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){



    private val viewNombre = itemView.findViewById<TextView>(R.id.txtNombre)
    val imagen = itemView.findViewById<ImageView>(R.id.image_item)
    val btnDelete = itemView.findViewById<Button>(R.id.btn_borrar)

    fun render(item: Item, onClickListener: (Item) -> Unit, onClickDelete: (Int) -> Unit){
        viewNombre.text = item.nombre
        itemView.setOnClickListener { onClickListener(item) }
        btnDelete.setOnClickListener { onClickDelete(adapterPosition) }

    }
}