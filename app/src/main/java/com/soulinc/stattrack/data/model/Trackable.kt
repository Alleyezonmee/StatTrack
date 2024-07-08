package com.soulinc.stattrack.data.model

sealed class Trackable(
    open val name: String,
    open val progress: Progress,
    open val description: String
) {
    /**
     * types of saving, amount in particular saving,
     */
    data class SavingTrackable(
        override val name: String,
        override val progress: Progress.MoneyBasedProgress,
        override val description: String,
        val savings: List<SavingsItem>,
    ) : Trackable(name, progress, description)
}

data class SavingsItem(
    val name: String,
    val amount: Int
)
