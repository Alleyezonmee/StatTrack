package com.soulinc.stattrack.data.repo

import com.soulinc.stattrack.data.db.TrackAblesDao
import com.soulinc.stattrack.data.entity.TrackableEntity
import com.soulinc.stattrack.data.model.Trackable
import com.soulinc.stattrack.data.model.toLocal
import javax.inject.Inject

class TrackAblesRepoImpl @Inject constructor(private val trackDbSource: TrackAblesDao) : TrackAblesRepo {
    override suspend fun getAllTrackAbles(): List<Trackable> {
        return trackDbSource.getAllTrackAbles().map { it.toLocal() }
    }

    override suspend fun addTrackAble(data: Trackable) {
        trackDbSource.addTrackAble(
            TrackableEntity(
                name = data.name,
                description = data.description,
                progress = data.progress.progress.toFloat(),
                trackAbleType = data.name
            )
        )
    }

    override suspend fun removeTrackAble(data: Trackable) {
        data.id?.let {
            trackDbSource.deleteTrackAble(it)
        }
    }

    override suspend fun updateTrackAble(data: Trackable) {
        trackDbSource.updateTrackAble(
            TrackableEntity(
                id = data.id,
                name = data.name,
                description = data.description,
                progress = data.progress.progress.toFloat(),
                trackAbleType = data.name
            )
        )
    }

}