package org.ochamo.breakingbad.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.ochamo.breakingbad.data.local.entity.BreakingBadFavoriteItem
import org.ochamo.breakingbad.data.repository.BreakingBadRepository
import org.ochamo.breakingbad.ui.model.BreakingBadCharacterMapper
import org.ochamo.breakingbad.ui.model.BreakingBadCharacterModel
import org.ochamo.breakingbad.util.Event
import javax.inject.Inject

@HiltViewModel
class BreakingBadListViewModel @Inject constructor(
    val breakingBadRepository: BreakingBadRepository
) : ViewModel() {
    private val _dataLoading = MutableLiveData<Boolean>(false)
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _listOfCharacters = MutableLiveData<MutableSet<BreakingBadCharacterModel>>(
        mutableSetOf()
    )
    val listOfCharacters: LiveData<MutableSet<BreakingBadCharacterModel>> = _listOfCharacters

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _selectedItem = MutableLiveData<Event<BreakingBadCharacterModel>>()

    val selectedItem: LiveData<Event<BreakingBadCharacterModel>> = _selectedItem

    fun updateLoadingStatus(status: Boolean) {
        _dataLoading.value = status
    }

    fun onClick(selectedModel: BreakingBadCharacterModel) {
        _selectedItem.value = Event(selectedModel)
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
                    val items: MutableSet<BreakingBadCharacterModel> = _listOfCharacters.value!!
                    items.addAll(mapper.map(result.success, hashMapOf<Int, BreakingBadFavoriteItem>()))
                    _listOfCharacters.value = items
                }
            } else {
                _error.value = result.error!!.message
            }
        }
    }

    fun reorderItems(items : MutableSet<BreakingBadCharacterModel>) {
        viewModelScope.launch(Dispatchers.Default) {
            val reordered =
            _listOfCharacters.value!!.sortedBy {
                it.isFavorite.get()
            }.toMutableSet()
            _listOfCharacters.value = reordered
        }
    }

}