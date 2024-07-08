package com.soulinc.stattrack.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trackAbles")
data class TrackableEntity(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    @ColumnInfo("name") val name: String,
    @ColumnInfo("progress") val progress: Float,
    @ColumnInfo("description") val description: String,
    @ColumnInfo("trackAbleType") val trackAbleType: String
)
