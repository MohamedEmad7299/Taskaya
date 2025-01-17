package com.ug.taskaya.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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
    fun provideRepository(firebaseAuth: FirebaseAuth, firebaseFirestore : FirebaseFirestore): Repository {
        return Repository(firebaseAuth , firebaseFirestore)
    }
}