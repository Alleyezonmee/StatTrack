package com.soulinc.stattrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.soulinc.stattrack.data.StatTrackViewModel
import com.soulinc.stattrack.ui.theme.StatTrackTheme

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<StatTrackViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StatTrackTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    val state = viewModel.trackAbles.collectAsState(initial = emptyList())
                }
            }
        }
    }
}