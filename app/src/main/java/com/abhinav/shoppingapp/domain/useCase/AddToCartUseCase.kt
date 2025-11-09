package com.abhinav.shoppingapp.domain.useCase

import com.abhinav.shoppingapp.common.ResultState
import com.abhinav.shoppingapp.domain.models.CartDataModels
import com.abhinav.shoppingapp.domain.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddToCartUseCase @Inject constructor(private val repo: Repo) {

    fun addToCart(cartDataModels: CartDataModels): Flow<ResultState<String>>{
        return repo.addToCart(cartDataModels)
    }
}