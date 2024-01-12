package com.example.jetshopping.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.jetshopping.data.room.models.Item
import com.example.jetshopping.data.room.models.Store
import kotlinx.coroutines.flow.Flow

@Dao
interface StoreDao {

    @Insert
    suspend fun insert(store: Store) : Long

    @Update
    suspend fun update(store: Store)

    @Delete
    suspend fun delete(item: Item)

    @Query("SELECT * FROM stores")
    fun getAllStores(): Flow<List<Store>>

    @Query("SELECT * FROM stores WHERE store_id =:storeId")
    fun getStore(storeId: Int): Flow<Store>

}