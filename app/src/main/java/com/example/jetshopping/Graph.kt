package com.example.jetshopping

import android.content.Context
import com.example.jetshopping.data.room.ShoppingListDatabase
import com.example.jetshopping.ui.repository.Repository

object Graph {
    private lateinit var db: ShoppingListDatabase

    val repository by lazy {
        Repository(
            listDao = db.listDao(),
            storeDao = db.storeDao(),
            itemDao = db.itemDao()
        )
    }

    fun provide(context: Context){
        db = ShoppingListDatabase.buildDatabase(context)
    }
}