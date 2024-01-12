package com.example.jetshopping.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.jetshopping.data.room.dao.ItemDao
import com.example.jetshopping.data.room.dao.ListDao
import com.example.jetshopping.data.room.dao.StoreDao
import com.example.jetshopping.data.room.models.Item
import com.example.jetshopping.data.room.models.ShoppingList
import com.example.jetshopping.data.room.models.Store

@TypeConverters(value = [DateConverter::class])
@Database(
    entities = [ShoppingList::class,Item::class,Store::class],
    version = 1,
    exportSchema = false
)
abstract class ShoppingListDatabase:RoomDatabase() {
    abstract fun listDao():ListDao
    abstract fun itemDao():ItemDao
    abstract fun storeDao():StoreDao

    companion object{
        @Volatile
        private var INSTANCE:ShoppingListDatabase? = null
        private var LOCK = Any()

        operator fun invoke(context: Context) = INSTANCE ?: synchronized(LOCK){
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }
        fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            ShoppingListDatabase::class.java,
            "shopping_list_database"
        ).build()

    }

}