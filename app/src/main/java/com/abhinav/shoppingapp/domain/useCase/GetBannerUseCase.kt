package com.abhinav.shoppingapp.domain.useCase

import com.abhinav.shoppingapp.common.ResultState
import com.abhinav.shoppingapp.domain.models.BannerDataModels
import com.abhinav.shoppingapp.domain.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBannerUseCase @Inject constructor(private val repo: Repo) {
    fun getBannerUseCase(): Flow<ResultState<List<BannerDataModels>>>{
        return repo.getBanner()
    }
}