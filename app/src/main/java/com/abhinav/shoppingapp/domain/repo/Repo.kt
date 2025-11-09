package com.abhinav.shoppingapp.domain.repo

import android.net.Uri
import com.abhinav.shoppingapp.common.ResultState
import com.abhinav.shoppingapp.domain.models.BannerDataModels
import com.abhinav.shoppingapp.domain.models.CartDataModels
import com.abhinav.shoppingapp.domain.models.CategoryDataModels
import com.abhinav.shoppingapp.domain.models.ProductsDataModels
import com.abhinav.shoppingapp.domain.models.UserData
import com.abhinav.shoppingapp.domain.models.UserDataParent
import kotlinx.coroutines.flow.Flow

interface Repo {
    fun registerUserWithEmailAndPassword( userData: UserData): Flow<ResultState<String>>
    fun loginUserWithEmailAndPassword(userData: UserData): Flow<ResultState<String>>
    fun getUserById(uid: String): Flow<ResultState<UserDataParent>>
    fun updateUserData(userDataParent: UserDataParent): Flow<ResultState<String>>
    fun userProfileImage(uri: Uri): Flow<ResultState<String>>
    fun getCategoriesInLimited(): Flow<ResultState<List<CategoryDataModels>>>
    fun getProductsInLimited(): Flow<ResultState<List<ProductsDataModels>>>
    fun getAllProducts(): Flow<ResultState<List<ProductsDataModels>>>
    fun getProductById(productId: String): Flow<ResultState<ProductsDataModels>>
    fun addToCart(cartDataModels: CartDataModels):Flow<ResultState<String>>
    fun addToFav(productsDataModels: ProductsDataModels): Flow<ResultState<String>>
    fun getAllFav(): Flow<ResultState<List<ProductsDataModels>>>
    fun getCart(): Flow<ResultState<List<CartDataModels>>>
    fun getAllCategories():Flow<ResultState<List<CategoryDataModels>>>
    fun getCheckout(productId: String):Flow<ResultState<ProductsDataModels>>
    fun getBanner():Flow<ResultState<List<BannerDataModels>>>
    fun getSpecificCategoryItems(categoryName: String):Flow<ResultState<List<ProductsDataModels>>>
    fun getAllSuggestedProducts():Flow<ResultState<List<ProductsDataModels>>>


}