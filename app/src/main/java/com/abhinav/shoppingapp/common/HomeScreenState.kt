package com.abhinav.shoppingapp.common

import com.abhinav.shoppingapp.domain.models.BannerDataModels
import com.abhinav.shoppingapp.domain.models.CategoryDataModels
import com.abhinav.shoppingapp.domain.models.ProductsDataModels

data class HomeScreenState(
    val isLoading : Boolean = true,
    val errorMessages : String? = null,
    val categories : List<CategoryDataModels>? = null,
    val products : List<ProductsDataModels>? = null,
    val banners : List<BannerDataModels>? = null

)