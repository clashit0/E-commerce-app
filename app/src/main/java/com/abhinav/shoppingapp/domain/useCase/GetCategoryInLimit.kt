package com.abhinav.shoppingapp.domain.useCase


import com.abhinav.shoppingapp.common.ResultState
import com.abhinav.shoppingapp.domain.models.CategoryDataModels
import com.abhinav.shoppingapp.domain.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoryInLimit @Inject constructor(private val repo: Repo) {
    fun getCategoryInLimit(): Flow<ResultState<List<CategoryDataModels>>>{
        return repo.getCategoriesInLimited()
    }
}