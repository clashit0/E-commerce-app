package com.abhinav.shoppingapp.domain.useCase

import com.abhinav.shoppingapp.common.ResultState
import com.abhinav.shoppingapp.domain.models.CategoryDataModels
import com.abhinav.shoppingapp.domain.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllCategoryUseCase @Inject constructor(private val repo: Repo) {
    fun getAllCategories(): Flow<ResultState<List<CategoryDataModels>>>{
        return repo.getAllCategories()
    }
}