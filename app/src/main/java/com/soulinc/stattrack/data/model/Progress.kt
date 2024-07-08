package com.soulinc.stattrack.data.model

import androidx.annotation.StringDef

sealed class Progress(
    @ProgressType open val progress: String
) {
    data class PercentageBasedProgress(
        override val progress: String,
        val currentStatus: Float
    ) : Progress(progress)

    data class MoneyBasedProgress(
        override val progress: String,
        val currentAmount: Int,
        val goalAmount: Int
    ) : Progress(progress)
}

@Retention(AnnotationRetention.RUNTIME)
@StringDef(
    ProgressType.NOT_STARTED,
    ProgressType.STARTED,
    ProgressType.FINISHED,
    ProgressType.PAUSED
)
annotation class ProgressType {
    companion object {
        const val NOT_STARTED = "NOT_STARTED"
        const val STARTED = "STARTED"
        const val FINISHED = "FINISHED"
        const val PAUSED = "PAUSED"
    }
}
