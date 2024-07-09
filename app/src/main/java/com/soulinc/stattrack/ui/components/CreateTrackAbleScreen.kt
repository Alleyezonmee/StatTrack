package com.soulinc.stattrack.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CreateTrackAbleScreen() {
    Column {
        Text(text = "Created Trackable")
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = "Trackable description")
    }
}