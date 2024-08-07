package com.soulinc.stattrack.data.model

import com.soulinc.stattrack.data.entity.TrackableEntity

sealed class Trackable(
    open val id: Int? = null,
    open val name: String,
    open val progress: Progress,
    open val description: String
) {
    /**
     * types of saving, amount in particular saving,
     */
    data class SavingTrackable(
        override val id: Int? = null,
        override val name: String,
        override val progress: Progress.MoneyBasedProgress,
        override val description: String,
        val savings: List<SavingsItem>,
    ) : Trackable(id, name, progress, description)
}

data class SavingsItem(
    val name: String,
    val amount: Int
)

fun TrackableEntity.toLocal(): Trackable {
    return Trackable.SavingTrackable(
        id = id,
        name = name,
        description = description,
        savings = emptyList(),
        progress = Progress.MoneyBasedProgress(progress.toString(), 0, 0)
    )
}

data class StatTrackState(
    val trackAbles: List<Trackable>,
    val selectedTrackAble: Trackable? = null,
    val visibilityState: VisibilityState = VisibilityState.ListView
) {
    companion object {
        fun defaultState() = StatTrackState(trackAbles = emptyList())
    }
}

sealed interface VisibilityState {
    data object SingleSection : VisibilityState
    data object ListView : VisibilityState
}
