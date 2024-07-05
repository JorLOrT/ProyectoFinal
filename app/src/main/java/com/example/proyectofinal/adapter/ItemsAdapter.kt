package com.example.proyectofinal.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinal.Item
import com.example.proyectofinal.R

class ItemsAdapter(var items: MutableList<Item>, private val onClickListener: (Item) ->Unit, private val onClickDelete: (Int) ->Unit ) :RecyclerView.Adapter<ItemsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_objeto, parent, false)
        return ItemsViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ItemsViewHolder, position: Int) {
        val item = items[position]
        holder.render(item, onClickListener, onClickDelete)
    }

    fun mostrarListaFiltrada(items: MutableList<Item>){
        this.items = items
        notifyDataSetChanged()
    }
}