package com.example.jetshopping.data.room.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "stores")
data class Store(
    @ColumnInfo(name = "store_id")
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val listIdFk: Int,
    val storeName: String,
)
