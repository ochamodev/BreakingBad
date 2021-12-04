package org.ochamo.breakingbad.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class BreakingBadListViewModel() : ViewModel() {
    private val _dataLoading = MutableLiveData<Boolean>(false)

    val dataLoading: LiveData<Boolean> = _dataLoading

    fun updateLoadingStatus() {

    }


}