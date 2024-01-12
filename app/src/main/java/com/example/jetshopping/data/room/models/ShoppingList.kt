package com.example.jetshopping.data.room.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_list")
data class ShoppingList(
    @ColumnInfo(name = "list_id")
    @PrimaryKey
    val id: Int,
    val name: String,
)
