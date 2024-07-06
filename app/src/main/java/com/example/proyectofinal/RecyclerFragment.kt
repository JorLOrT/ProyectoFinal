package com.example.proyectofinal

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinal.adapter.ItemsAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions

class RecyclerFragment : Fragment(R.layout.fragment_recycler) {

    private lateinit var itemAdapter: ItemsAdapter
    private val layoutManager = LinearLayoutManager(this.context)
    private var db = FirebaseFirestore.getInstance()
    private var itemMutableList:MutableList<Item> = mutableListOf()
    var indexNuevo = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(itemMutableList.isNotEmpty()){
            Log.d("Item Recycler", itemMutableList[0].nombre)
        }else{
            cargarDatos()
            Log.d("Item Recycler", "Lista vacia")
        }

        initReclerView(view)
        val inputFiltro = view.findViewById<EditText>(R.id.input_filtro_items)
        //Vigila los cambios en el edit text
        inputFiltro.addTextChangedListener {itemFiltro ->
            val itemsFiltrados = itemMutableList.filter { item -> item.nombre.lowercase().contains(itemFiltro.toString().lowercase())}
            // Actualiza los items en el adapter
            itemAdapter.mostrarListaFiltrada(itemsFiltrados.toMutableList())
        }
    }

    private fun cargarDatos(){
        db.collection("items")
            .get()
            .addOnSuccessListener { snapshot ->
                for (document in snapshot.documents) {
                    val item = document.toObject(Item::class.java)
                    if (item != null) {
                        itemMutableList.add(item)
                        Log.d("Success", "Item agregado: $item")
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w("Error", "Error obteniendo items", exception)
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

    // Uso de Scanner para obtener la información del QR
    val scannerL = registerForActivityResult<ScanOptions, ScanIntentResult>(ScanContract()) { result ->
        if (result.contents == null) {
            Toast.makeText(this.context, "Cancelado", Toast.LENGTH_SHORT).show()
        } else {
            val res = result.contents
            // añadiendo a base de datos
            val item = Item("${res} ${indexNuevo}", "imagen")
            db.collection("items").document(indexNuevo.toString()).set(item).
            addOnSuccessListener{
                Log.d("Insertado", "Insertado Correcto")
            }.addOnFailureListener{
                Log.d("Error", "Error al insertar")
            }
            itemMutableList.add(indexNuevo,item)
            itemAdapter.notifyItemInserted(indexNuevo)
            layoutManager.scrollToPosition(indexNuevo)
            indexNuevo++

            Toast.makeText(this.context, "Scanned: " + result.contents, Toast.LENGTH_SHORT)
                .show()
        }
    }

    // Método para crear un item
    private fun crearItem() {

        scannerL.launch(ScanOptions().setPrompt("Scan Qr").setDesiredBarcodeFormats(ScanOptions.QR_CODE))

    }

    // Metodo para eliminar un item
    private fun onDeleteItem(posicion: Int) {
        // añadiendo a base de datos
        db = FirebaseFirestore.getInstance()
        db.collection("items").document(posicion.toString()).delete()
        // añadiendo al recycler
        itemMutableList.removeAt(posicion)
        itemAdapter.notifyItemRemoved(posicion)
        itemAdapter.notifyItemRangeChanged(posicion, itemMutableList.size)
    }

    // Método para mostrar un Toast al seleccionar un item
    private fun onItemSelected(item: Item){
        Toast.makeText(this.context, "Seleccionaste ${item.nombre}", Toast.LENGTH_SHORT).show()
    }

}