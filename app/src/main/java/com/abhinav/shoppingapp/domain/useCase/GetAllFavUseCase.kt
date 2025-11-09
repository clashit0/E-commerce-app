package com.abhinav.shoppingapp.domain.useCase

import com.abhinav.shoppingapp.common.ResultState
import com.abhinav.shoppingapp.domain.models.ProductsDataModels
import com.abhinav.shoppingapp.domain.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllFavUseCase @Inject constructor(private val repo: Repo) {
    fun getAllFavUseCase(): Flow<ResultState<List<ProductsDataModels>>>{
        return repo.getAllFav()
    }
}