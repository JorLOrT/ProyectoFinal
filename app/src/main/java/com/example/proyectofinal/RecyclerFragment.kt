package com.example.proyectofinal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinal.adapter.ItemsAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class RecyclerFragment : Fragment(R.layout.fragment_recycler) {

    private lateinit var itemAdapter: ItemsAdapter
    private var itemMutableList:MutableList<Item> = ItemsProvider.itemsList.toMutableList()
    private val layoutManager = LinearLayoutManager(this.context)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initReclerView(view)
        val inputFiltro = view.findViewById<EditText>(R.id.input_filtro_items)
        //Vigila los cambios en el edit text
        inputFiltro.addTextChangedListener {itemFiltro ->
            val itemsFiltrados = itemMutableList.filter { item -> item.nombre.lowercase().contains(itemFiltro.toString().lowercase())}
            // Actualiza los items en el adapter
            itemAdapter.mostrarListaFiltrada(itemsFiltrados.toMutableList())
        }
    }

    private fun initReclerView(view: View) {
        val itemRecycler = view.findViewById<RecyclerView>(R.id.recycler_widget)
        itemAdapter = ItemsAdapter(
            items = itemMutableList,
            onClickListener = { item:Item -> onItemSelected(item) },
            onClickDelete = { posicion -> onDeleteItem(posicion) }
        )
        itemRecycler.layoutManager = layoutManager
        itemRecycler.adapter = itemAdapter

        val btnAdd = view.findViewById<FloatingActionButton>(R.id.add_button)
        btnAdd.setOnClickListener {
            crearItem()
        }
    }

    private fun crearItem() {
        val item = Item("coso", "coso otro")
        itemMutableList.add(0,item)
        itemAdapter.notifyItemInserted(0)
        layoutManager.scrollToPosition(0)
    }

    private fun onDeleteItem(posicion: Int) {
        itemMutableList.removeAt(posicion)
        itemAdapter.notifyItemRemoved(posicion)
        itemAdapter.notifyItemRangeChanged(posicion, itemMutableList.size)
    }

    private fun onItemSelected(item: Item){
        Toast.makeText(this.context, "Seleccionaste ${item.nombre}", Toast.LENGTH_SHORT).show()
    }

}