package com.abhinav.shoppingapp.domain.useCase

import com.abhinav.shoppingapp.common.ResultState
import com.abhinav.shoppingapp.domain.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoveFromFavUseCase @Inject constructor(private val repo: Repo) {
    fun removeFromFavUseCase(productId : String): Flow<ResultState<String>>{
        return repo.removeFromFav(productId)
    }
}