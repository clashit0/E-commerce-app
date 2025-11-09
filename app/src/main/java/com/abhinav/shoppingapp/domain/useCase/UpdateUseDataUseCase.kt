package com.abhinav.shoppingapp.domain.useCase

import com.abhinav.shoppingapp.common.ResultState
import com.abhinav.shoppingapp.domain.models.CategoryDataModels
import com.abhinav.shoppingapp.domain.models.UserData
import com.abhinav.shoppingapp.domain.models.UserDataParent
import com.abhinav.shoppingapp.domain.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateUseDataUseCase @Inject constructor(private val repo: Repo) {
    fun updateUseData(userDataParent: UserDataParent): Flow<ResultState<String>> {
        return repo.updateUserData(userDataParent)
    }
}