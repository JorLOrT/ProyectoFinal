package com.example.proyectofinal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinal.adapter.ItemsAdapter

class RecyclerFragment : Fragment(R.layout.fragment_recycler) {

    private lateinit var itemAdapter: ItemsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initReclerView(view)
    }

    private fun initReclerView(view: View) {
        val itemRecycler = view.findViewById<RecyclerView>(R.id.recycler_widget)
        itemAdapter = ItemsAdapter(ItemsProvider.itemsList)
        itemRecycler.layoutManager = LinearLayoutManager(this.context)
        itemRecycler.adapter = itemAdapter
    }



}