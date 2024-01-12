package com.example.jetshopping.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.jetshopping.data.room.models.ItemsWithStoreAndList
import com.example.jetshopping.data.room.models.ShoppingList
import kotlinx.coroutines.flow.Flow

@Dao
interface ListDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertShoppingList(shoppingList: ShoppingList) : Long

    @Query(
        """
        SELECT * FROM items AS I INNER JOIN shopping_list AS S
        ON I.listId = S.list_id INNER JOIN stores AS ST
        ON I.storeIdFk = ST.store_id
    """
    )
    fun getItemsWithStoreAndList(): Flow<List<ItemsWithStoreAndList>>

    @Query(
        """
        SELECT * FROM items AS I INNER JOIN shopping_list AS S
        ON I.listId = S.list_id INNER JOIN stores AS ST
        ON I.storeIdFk = ST.store_id WHERE S.list_id =:listId
    """
    )
    fun getItemsWithStoreAndListFilteredById(listId: Int)
            : Flow<List<ItemsWithStoreAndList>>

    @Query(
        """
        SELECT * FROM items AS I INNER JOIN shopping_list AS S
        ON I.listId = S.list_id INNER JOIN stores AS ST
        ON I.storeIdFk = ST.store_id WHERE I.item_id =:itemId
    """
    )
    fun getItemWithStoreAndListFilteredById(itemId: Int)
            : Flow<ItemsWithStoreAndList>

}