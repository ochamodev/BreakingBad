package org.ochamo.breakingbad.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.ochamo.breakingbad.data.local.LocalDbRepository
import org.ochamo.breakingbad.data.local.LocalDbRepositoryImpl
import org.ochamo.breakingbad.data.local.entity.BreakingBadFavoriteItem
import org.ochamo.breakingbad.data.repository.BreakingBadRepository
import org.ochamo.breakingbad.ui.model.BreakingBadCharacterMapper
import org.ochamo.breakingbad.ui.model.BreakingBadCharacterModel
import org.ochamo.breakingbad.util.Event
import javax.inject.Inject

@HiltViewModel
class BreakingBadListViewModel @Inject constructor(
    val breakingBadRepository: BreakingBadRepository,
    val localDbRepositoryImpl: LocalDbRepository
) : ViewModel() {
    private val _dataLoading = MutableLiveData<Event<Boolean>>(Event(false))
    val dataLoading : LiveData<Event<Boolean>> = _dataLoading

    private val _listOfCharacters = MutableLiveData<MutableSet<BreakingBadCharacterModel>>(
        mutableSetOf()
    )
    val listOfCharacters: LiveData<MutableSet<BreakingBadCharacterModel>> = _listOfCharacters

    private val _listOfFavorites = MutableLiveData<HashMap<Int, BreakingBadFavoriteItem>>()
    val listOfFavorites: LiveData<HashMap<Int, BreakingBadFavoriteItem>> = _listOfFavorites

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _selectedItem = MutableLiveData<Event<BreakingBadCharacterModel>>()

    val selectedItem: LiveData<Event<BreakingBadCharacterModel>> = _selectedItem

    fun updateLoadingStatus(status: Boolean) {
        _dataLoading.postValue(Event(status))
    }

    fun onClick(selectedModel: BreakingBadCharacterModel) {
        _selectedItem.value = Event(selectedModel)
    }


    fun updateItem(item: BreakingBadCharacterModel) {
        var element = listOfCharacters.value!!.find { it.id == item.id }
        element!!.isFavorite = item.isFavorite
    }

    fun getFavorites() {
        viewModelScope.launch {
            val result = localDbRepositoryImpl.getFavoriteItems()
            if (result.success != null) {
                _listOfFavorites.value = result.success!!
            } else {
                _error.value = result.error!!.message
            }
        }
    }

    fun getCharacters() {
        val paginationLimit = 20
        if (listOfCharacters.value!!.isEmpty()) {
            getBreakingBadCharacters(paginationLimit, 0)
        } else {
            val lastId = listOfCharacters.value!!.size
            getBreakingBadCharacters(paginationLimit, lastId)
        }
    }

    fun getBreakingBadCharacters(limit: Int, offset: Int) {
        viewModelScope.launch {
            updateLoadingStatus(true)

            val result = breakingBadRepository
                .getTasks(
                    limit,
                    offset
                )
            val mapper = BreakingBadCharacterMapper()
            if (result.success != null) {
                if (result.success.isNotEmpty()) {

                    val items: MutableSet<BreakingBadCharacterModel> = listOfCharacters.value!!
                    items.addAll(mapper.map(result.success, listOfFavorites.value!!))
                    reorderItems()
                } else {
                    updateLoadingStatus(false)
                }
            } else {
                _error.value = result.error!!.message
            }
        }
    }

    fun reorderItems() {
        viewModelScope.launch {
            val result = ArrayList(listOfCharacters.value!!)
                .sortedWith(compareBy<BreakingBadCharacterModel>( {!it.isFavorite.get()})).toMutableSet()
            _listOfCharacters.value = result
        }
    }

}