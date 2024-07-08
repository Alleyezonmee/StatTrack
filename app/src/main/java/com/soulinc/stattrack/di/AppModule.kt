package com.soulinc.stattrack.di

import android.content.Context
import com.soulinc.stattrack.data.db.StatTrackDb
import com.soulinc.stattrack.data.db.TrackAblesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideStatTrackDb(@ApplicationContext context: Context): StatTrackDb {
        return StatTrackDb.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideTrackAblesDao(db: StatTrackDb) = db.getTrackAblesDao()
}