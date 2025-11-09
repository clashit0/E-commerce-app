package com.abhinav.shoppingapp.data.di

import com.abhinav.shoppingapp.data.repo.RepoImpl
import com.abhinav.shoppingapp.domain.repo.Repo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    // Firebase Auth
    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    // Firebase Firestore
    @Singleton
    @Provides
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }


    // ✅ ADD THIS: Repository binding (THIS FIXES THE ERROR!)
    @Singleton
    @Provides
    fun provideRepository(
        firebaseAuth: FirebaseAuth,
        firebaseFirestore: FirebaseFirestore,
    ): Repo {
        return RepoImpl(firebaseAuth, firebaseFirestore)
    }
}