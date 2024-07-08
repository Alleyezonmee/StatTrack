package com.soulinc.stattrack.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.soulinc.stattrack.data.entity.TrackableEntity

@Database(entities = [TrackableEntity::class], exportSchema = true, version = 1)
abstract class StatTrackDb : RoomDatabase() {
    abstract fun getTrackAblesDao(): TrackAblesDao

    companion object {
        @Volatile
        private var INSTANCE: StatTrackDb? = null

        fun getDatabase(context: Context): StatTrackDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StatTrackDb::class.java,
                    "statTrack"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
