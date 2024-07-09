package com.soulinc.stattrack.data.repo

import com.soulinc.stattrack.data.model.Trackable

interface TrackAblesRepo {

    suspend fun getAllTrackAbles() : List<Trackable>

    suspend fun addTrackAble(data: Trackable)

    suspend fun removeTrackAble(data: Trackable)

    suspend fun updateTrackAble(data: Trackable)
}