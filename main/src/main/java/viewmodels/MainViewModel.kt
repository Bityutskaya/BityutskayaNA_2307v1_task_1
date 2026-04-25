package ci.nsu.mobile.main.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ci.nsu.mobile.main.data.ShoppingListRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: ShoppingListRepository) : ViewModel() {

    private val _shoppingLists = MutableLiveData<Map<String, List<String>>>()
    val shoppingLists: LiveData<Map<String, List<String>>> = _shoppingLists

    init {
        loadLists()
    }

    private fun loadLists() {
        viewModelScope.launch {
            repository.getAllLists().collect { lists ->
                _shoppingLists.value = lists.associate { it.date to it.items }
            }
        }
    }

    suspend fun getListForDate(date: String): List<String> {
        return repository.getListForDate(date)
    }

    fun saveList(date: String, items: List<String>) {
        viewModelScope.launch {
            repository.saveList(date, items)
        }
    }

    fun deleteList(date: String) {
        viewModelScope.launch {
            repository.deleteList(date)
        }
    }
}