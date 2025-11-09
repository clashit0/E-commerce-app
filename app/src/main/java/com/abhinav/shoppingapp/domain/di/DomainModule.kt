package com.abhinav.shoppingapp.domain.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

//    @Provides
//    fun provideRepo(firebaseAuth : FirebaseAuth):Repo{
//        return RepoImpl(firebaseAuth ,firebaseFirestore)
//    }
}