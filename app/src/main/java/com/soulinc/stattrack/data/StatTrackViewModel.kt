package com.soulinc.stattrack.data

import androidx.lifecycle.ViewModel
import com.soulinc.stattrack.data.model.Trackable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

@HiltViewModel
class StatTrackViewModel @Inject constructor() : ViewModel() {
    private val _trackAbles = MutableSharedFlow<List<Trackable>>()
    val trackAbles: SharedFlow<List<Trackable>>
        get() = _trackAbles
}