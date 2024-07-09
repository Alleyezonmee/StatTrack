package com.soulinc.stattrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.soulinc.stattrack.data.StatTrackViewModel
import com.soulinc.stattrack.ui.theme.StatTrackTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateTrackAbleActivity: ComponentActivity() {

    private val viewModel by viewModels<StatTrackViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StatTrackTheme {

            }
        }
    }
}