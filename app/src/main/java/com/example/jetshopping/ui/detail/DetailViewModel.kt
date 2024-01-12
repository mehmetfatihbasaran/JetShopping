package com.example.jetshopping.ui.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.jetshopping.Category
import com.example.jetshopping.Graph
import com.example.jetshopping.Utils
import com.example.jetshopping.data.room.models.Item
import com.example.jetshopping.data.room.models.ShoppingList
import com.example.jetshopping.data.room.models.Store
import com.example.jetshopping.ui.repository.Repository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Date

class DetailViewModel constructor(
    private val itemId: Int,
    private val repository: Repository = Graph.repository
) : ViewModel() {

    var state by mutableStateOf(DetailState())
        private set

    init {
        addListItem()
        getStores()
        if (itemId != -1) {
            viewModelScope.launch {
                repository.getItemWithStoreAndList(itemId).collectLatest {
                    state = state.copy(
                        item = it.item.itemName,
                        qty = it.item.qty,
                        store = it.store.storeName,
                        date = it.item.date,
                        category = Utils.category.find {category ->
                            category.id == it.shoppingList.id
                        } ?: Category()
                    )
                }
            }
        }
    }

    init {
        state = if (itemId != -1){
            state.copy(isUpdatingItem = true)
        }else{
            state.copy(isUpdatingItem = false)
        }
    }

    val isFieldsNotEmpty: Boolean
        get() = state.item.isNotEmpty() && state.store.isNotEmpty() && state.qty.isNotEmpty()

    fun onItemChange(newValue: String) {
        state = state.copy(item = newValue)
    }

    fun onStoreChange(newValue: String) {
        state = state.copy(store = newValue)
    }

    fun onQtyChange(newValue: String) {
        state = state.copy(qty = newValue)
    }

    fun onDateChange(newValue: Date) {
        state = state.copy(date = newValue)
    }

    fun onCategoryChange(newValue: Category) {
        state = state.copy(category = newValue)
    }

    fun onScreenDialogDismissed(newValue: Boolean) {
        state = state.copy(isScreenDialogDismissed = newValue)
    }

    private fun addListItem() {
        viewModelScope.launch {
            Utils.category.forEach { category ->
                repository.insertList(
                    ShoppingList(
                        id = category.id,
                        name = category.title
                    )
                )
            }
        }
    }

    fun addShoppingListItem() {
        viewModelScope.launch {
            repository.insertItem(
                Item(
                    id = itemId,
                    itemName = state.item,
                    qty = state.qty,
                    listId = state.category.id,
                    storeIdFk = state.storeList.find {
                        it.storeName == state.store
                    }?.id ?: 0,
                    date = state.date,
                    isChecked = false
                )
            )
        }
    }

    fun updateShoppingItem(id: Int) {
        viewModelScope.launch {
            repository.updateItem(
                Item(
                    id = id,
                    itemName = state.item,
                    qty = state.qty,
                    listId = state.category.id,
                    storeIdFk = state.storeList.find {
                        it.storeName == state.store
                    }?.id ?: 0,
                    date = state.date,
                    isChecked = false
                )
            )
        }
    }

    fun addStore() {
        viewModelScope.launch {
            repository.insertStore(
                Store(
                    storeName = state.store,
                    listIdFk = state.category.id
                )
            )
        }
    }

    private fun getStores() {
        viewModelScope.launch {
            repository.store.collectLatest {
                state = state.copy(storeList = it)
            }
        }
    }

}

class DetailViewModelFactory(private val id: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailViewModel(id) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

data class DetailState(
    val storeList: List<Store> = emptyList(),
    val item: String = "",
    val store: String = "",
    val date: Date = Date(),
    val qty: String = "",
    val isScreenDialogDismissed: Boolean = true,
    val isUpdatingItem: Boolean = false,
    val category: Category = Category()
)