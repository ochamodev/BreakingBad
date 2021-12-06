package org.ochamo.breakingbad.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.ochamo.breakingbad.data.local.LocalDbRepository
import org.ochamo.breakingbad.data.local.LocalDbRepositoryImpl
import org.ochamo.breakingbad.data.local.entity.BreakingBadFavoriteItem
import org.ochamo.breakingbad.ui.model.BreakingBadCharacterModel
import org.ochamo.breakingbad.util.Event
import javax.inject.Inject

@HiltViewModel
class SharedCharacterItemViewModel @Inject constructor(
    val localDbRepositoryImpl: LocalDbRepository
): ViewModel() {

    private val _breakingBadItem = MutableLiveData<BreakingBadCharacterModel>()

    val breakingBadItem: LiveData<BreakingBadCharacterModel> = _breakingBadItem

    private val _markAsFavorite = MutableLiveData<Event<BreakingBadCharacterModel>>()

    val markAsFavorite: LiveData<Event<BreakingBadCharacterModel>> = _markAsFavorite


    fun markOrUnMarkAsFavorite(selectedModel: BreakingBadCharacterModel) {
        //unmark
        if (selectedModel.isFavorite.get()) {
            viewModelScope.launch {
                localDbRepositoryImpl.deleteFavorite(BreakingBadFavoriteItem(selectedModel.id, selectedModel.isFavorite.get()))
            }

            selectedModel.isFavorite.set(false)
            //mark
        } else {
            viewModelScope.launch {
                localDbRepositoryImpl.saveFavorite(BreakingBadFavoriteItem(selectedModel.id, true))
            }
            selectedModel.isFavorite.set(true)

        }
        _markAsFavorite.value = Event(selectedModel)
    }


    fun setItem(selected: BreakingBadCharacterModel) {
        _breakingBadItem.postValue(selected)
    }


}