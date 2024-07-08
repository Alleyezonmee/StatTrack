package com.soulinc.stattrack.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soulinc.stattrack.data.model.Trackable
import com.soulinc.stattrack.data.repo.TrackAblesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatTrackViewModel @Inject constructor(
    private val trackAbleRepo: TrackAblesRepo
) : ViewModel() {
    private val _trackAbles = MutableSharedFlow<List<Trackable>>()
    val trackAbles: SharedFlow<List<Trackable>>
        get() = _trackAbles

    fun fetchTrackAbles() {
        viewModelScope.launch(Dispatchers.IO) {
            val listFromDb = async { trackAbleRepo.getAllTrackAbles() }.await()
            _trackAbles.emit(listFromDb)
        }
    }
}