package com.example.proyectofinal

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

class ItemsProvider {
    private val db = FirebaseFirestore.getInstance()

    fun getItems(): List<Item> {
        val itemsList = mutableListOf<Item>()

        db.collection("items")
            .get()
            .addOnSuccessListener { snapshot ->
                for (document in snapshot.documents) {
                    val item = document.toObject(Item::class.java)
                    if (item != null) {
                        itemsList.add(item)
                    }
                }
            }
            .addOnFailureListener { exception ->
            }

        return itemsList.toList()
    }
}