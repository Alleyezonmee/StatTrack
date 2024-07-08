package com.soulinc.stattrack.di

import com.soulinc.stattrack.data.db.TrackAblesDao
import com.soulinc.stattrack.data.repo.TrackAblesRepo
import com.soulinc.stattrack.data.repo.TrackAblesRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class RepoModule {

    @Provides
    @ViewModelScoped
    fun provideTrackAblesRepo(trackAblesDao: TrackAblesDao) : TrackAblesRepo {
        return TrackAblesRepoImpl(trackAblesDao)
    }
}