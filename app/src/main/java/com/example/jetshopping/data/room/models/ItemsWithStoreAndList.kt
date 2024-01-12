package com.example.jetshopping.data.room.models

import androidx.room.Embedded
import androidx.room.Entity

@Entity
data class ItemsWithStoreAndList(
    @Embedded val item: Item,
    @Embedded val shoppingList: ShoppingList,
    @Embedded val store: Store
)
