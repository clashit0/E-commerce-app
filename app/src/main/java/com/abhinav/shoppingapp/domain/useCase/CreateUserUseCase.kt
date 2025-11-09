package com.abhinav.shoppingapp.domain.useCase

import com.abhinav.shoppingapp.common.ResultState
import com.abhinav.shoppingapp.domain.models.UserData
import com.abhinav.shoppingapp.domain.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateUserUseCase @Inject constructor(private val repo: Repo)  {
    fun createUser(userData: UserData): Flow<ResultState<String>>{
        return repo.registerUserWithEmailAndPassword(userData)
    }
}