package com.abhinav.shoppingapp.domain.useCase

import com.abhinav.shoppingapp.common.ResultState
import com.abhinav.shoppingapp.domain.models.CategoryDataModels
import com.abhinav.shoppingapp.domain.models.ProductsDataModels
import com.abhinav.shoppingapp.domain.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCheckOutUseCase @Inject constructor(private val repo: Repo) {
    fun getCheckOutUseCase(productId : String): Flow<ResultState<ProductsDataModels>>{
        return repo.getCheckout(productId)
    }
}