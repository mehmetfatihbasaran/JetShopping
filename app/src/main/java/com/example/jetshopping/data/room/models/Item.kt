package com.example.jetshopping.data.room.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "items")
data class Item(
    @ColumnInfo(name = "item_id")
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val itemName: String,
    val qty: String,
    val listId: Int,
    val storeIdFk: Int,
    val date: Date,
    val isChecked: Boolean,
)
