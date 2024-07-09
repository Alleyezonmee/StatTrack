package com.soulinc.stattrack.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.animation.with
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soulinc.stattrack.R
import com.soulinc.stattrack.data.StatTrackViewModel
import com.soulinc.stattrack.data.model.Trackable
import com.soulinc.stattrack.data.model.VisibilityState

@Composable
fun StatTrackContainer(viewModel: StatTrackViewModel) {
    val uiState = viewModel.state.collectAsState().value
    AnimatedContent(targetState = uiState.visibilityState, label = "statTrackContainer", transitionSpec = {
        if (uiState.visibilityState is VisibilityState.SingleSection) {
            slideInHorizontally { it }.togetherWith(slideOutHorizontally { -it })
        } else {
            slideInHorizontally { -it }.togetherWith(slideOutHorizontally { it })
        }
    }) { vState ->
        when (vState) {
            is VisibilityState.ListView -> {
                TrackAblesFeed(uiState.trackAbles, viewModel)
            }

            is VisibilityState.SingleSection -> {
                uiState.selectedTrackAble?.let {
                    SingleTrackAble(data = it, viewModel)
                }
            }
        }
    }
}

@Composable

fun SingleTrackAble(data: Trackable, viewModel: StatTrackViewModel) {
    val title = remember { mutableStateOf(data.name) }
    val description = remember { mutableStateOf(data.description) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .padding(horizontal = 16.dp)
            .verticalScroll(state = rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_back_arrow),
                contentDescription = "back_icon",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .padding(8.dp)
                    .align(Alignment.CenterVertically)
                    .clickable {
                        val newTrackAble = (data as Trackable.SavingTrackable).copy(
                            name = title.value,
                            description = description.value
                        )
                        viewModel.onBackClick(newTrackAble)
                    }
            )
            TextField(
                value = title.value,
                onValueChange = {
                    title.value = it
                },
                modifier = Modifier.weight(1F)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_delete),
                contentDescription = "delete_icon",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .padding(8.dp)
                    .align(Alignment.CenterVertically)
                    .clickable {
                        viewModel.deleteTrackAble(data)
                    }
            )
        }
        TextField(
            value = description.value, onValueChange = {
                description.value = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        )
    }
}

@Composable
fun TrackAblesFeed(list: List<Trackable>, viewModel: StatTrackViewModel) {
    val verticalStateList = rememberLazyListState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            state = verticalStateList
        ) {
            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
            items(list.size) { index ->
                TrackAbleItemUi(data = list[index], viewModel::onItemClick)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
        Text(
            text = "+", modifier = Modifier
                .size(48.dp)
                .background(Color.Green)
                .align(Alignment.BottomEnd)
                .clickable { viewModel.createAndSaveTrackAbleToDb() },
            textAlign = TextAlign.Center,
            fontSize = 32.sp
        )
    }
}

@Composable
fun TrackAbleItemUi(data: Trackable, onItemClick: (Trackable) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp))
            .background(Color.Magenta)
            .clickable { onItemClick(data) }
            .padding(12.dp)
    ) {
        Text(
            text = data.name + " " + data.id,
            fontSize = 16.sp,
            fontWeight = FontWeight.W500,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = data.description,
            fontSize = 12.sp,
            color = Color.White,
            maxLines = 4,
            overflow = TextOverflow.Ellipsis
        )
    }
}