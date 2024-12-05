package com.ug.taskaya.di

import com.google.firebase.auth.FirebaseAuth
import com.ug.taskaya.data.repositories.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(auth: FirebaseAuth): Repository {
        return Repository(auth)
    }
}