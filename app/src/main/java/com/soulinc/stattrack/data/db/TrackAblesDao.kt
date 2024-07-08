package com.soulinc.stattrack.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.soulinc.stattrack.data.entity.TrackableEntity

@Dao
interface TrackAblesDao {

    @Query("SELECT * FROM trackAbles")
    suspend fun getAllTrackAbles(): List<TrackableEntity>

    @Insert
    suspend fun addTrackAble(data: TrackableEntity)

    @Query("DELETE FROM trackAbles WHERE id = (:id)")
    suspend fun deleteTrackAble(id: Int)
}