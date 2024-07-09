package com.soulinc.stattrack.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soulinc.stattrack.data.model.Progress
import com.soulinc.stattrack.data.model.StatTrackState
import com.soulinc.stattrack.data.model.Trackable
import com.soulinc.stattrack.data.model.VisibilityState
import com.soulinc.stattrack.data.repo.TrackAblesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatTrackViewModel @Inject constructor(
    private val trackAbleRepo: TrackAblesRepo
) : ViewModel() {

    private val _state = MutableStateFlow(StatTrackState.defaultState())
    val state: StateFlow<StatTrackState>
        get() = _state

    private val _trackAbles = MutableStateFlow<List<Trackable>>(emptyList())
    val trackAbles: StateFlow<List<Trackable>>
        get() = _trackAbles

    fun fetchTrackAbles() {
        viewModelScope.launch(Dispatchers.IO) {
            val listFromDb = async { trackAbleRepo.getAllTrackAbles() }.await()
            _state.emit(
                state.value.copy(
                    trackAbles = listFromDb,
                    visibilityState = VisibilityState.ListView,
                    selectedTrackAble = null
                )
            )
        }
    }

    fun populateDummyResponse() {
        val list = arrayListOf<Trackable>()
        repeat(20) {
            val item = Trackable.SavingTrackable(
                id = it,
                name = "Trackable Entity $it",
                progress = Progress.MoneyBasedProgress("0.5", 1000, 2000),
                description = "Entering data is an important task in many apps. On devices with no physical keyboard (the vast majority in Android land) a so-called soft(ware) keyboard handles user input. Now, you may be wondering why we need to talk about these virtual peripherals at all. Shouldn't the operating system take care? I mean, in terms of user interface, the app expresses its desire to allow user input by showing and configuring an editable text field. What else needs to be done? This article takes a closer look at how Jetpack Compose apps interact with the keyboard.",
                savings = emptyList()
            )
            list.add(item)
        }
        println("statTrackLogs: dummy size: ${list.size}")
        viewModelScope.launch {
            _trackAbles.value = list
        }
    }

    fun createAndSaveTrackAbleToDb() {
        viewModelScope.launch(Dispatchers.IO) {
            val item = Trackable.SavingTrackable(
                name = "Trackable Entity Saved",
                progress = Progress.MoneyBasedProgress("0.5", 1000, 2000),
                description = "Entering data is an important task in many apps. On devices with no physical keyboard (the vast majority in Android land) a so-called soft(ware) keyboard handles user input. Now, you may be wondering why we need to talk about these virtual peripherals at all. Shouldn't the operating system take care? I mean, in terms of user interface, the app expresses its desire to allow user input by showing and configuring an editable text field. What else needs to be done? This article takes a closer look at how Jetpack Compose apps interact with the keyboard.",
                savings = emptyList()
            )
            trackAbleRepo.addTrackAble(item)
            fetchTrackAbles()
        }
    }

    fun deleteTrackAble(data: Trackable) {
        viewModelScope.launch(Dispatchers.IO) {
            trackAbleRepo.removeTrackAble(data)
            fetchTrackAbles()
        }
    }

    fun onItemClick(data: Trackable) {
        viewModelScope.launch {
            _state.emit(
                state.value.copy(
                    selectedTrackAble = data,
                    visibilityState = VisibilityState.SingleSection
                )
            )
        }
    }

    fun onBackClick(data: Trackable) {
        viewModelScope.launch {
            if (data != state.value.selectedTrackAble) {
                trackAbleRepo.updateTrackAble(data)
                fetchTrackAbles()
            } else {
                _state.emit(
                    state.value.copy(
                        selectedTrackAble = null,
                        visibilityState = VisibilityState.ListView
                    )
                )
            }
        }
    }
}