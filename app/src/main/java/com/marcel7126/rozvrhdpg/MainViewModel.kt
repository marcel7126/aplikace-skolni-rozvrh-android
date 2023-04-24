package com.marcel7126.rozvrhdpg

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// AT ALMOST FINAL VERSION, VIEW MODEL IS NOT USED AT ALL
// I AM KEEPING IT FOR POSSIBLE FUTURE USE

class MainViewModel: ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            delay(0)
            _isLoading.value = false
        }
    }

}