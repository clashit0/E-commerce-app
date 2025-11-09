package com.abhinav.shoppingapp.presentation.viewModels

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhinav.shoppingapp.common.HomeScreenState
import com.abhinav.shoppingapp.common.ResultState
import com.abhinav.shoppingapp.domain.models.CartDataModels
import com.abhinav.shoppingapp.domain.models.CategoryDataModels
import com.abhinav.shoppingapp.domain.models.ProductsDataModels
import com.abhinav.shoppingapp.domain.models.UserData
import com.abhinav.shoppingapp.domain.models.UserDataParent
import com.abhinav.shoppingapp.domain.useCase.AddToCartUseCase
import com.abhinav.shoppingapp.domain.useCase.AddToFavUseCase
import com.abhinav.shoppingapp.domain.useCase.CreateUserUseCase
import com.abhinav.shoppingapp.domain.useCase.GetAllCategoryUseCase
import com.abhinav.shoppingapp.domain.useCase.GetAllFavUseCase
import com.abhinav.shoppingapp.domain.useCase.GetAllProductUseCase
import com.abhinav.shoppingapp.domain.useCase.GetAllSuggestedProductsUseCase
import com.abhinav.shoppingapp.domain.useCase.GetBannerUseCase
import com.abhinav.shoppingapp.domain.useCase.GetCartUseCase
import com.abhinav.shoppingapp.domain.useCase.GetCategoryInLimit
import com.abhinav.shoppingapp.domain.useCase.GetCheckOutUseCase
import com.abhinav.shoppingapp.domain.useCase.GetProductById
import com.abhinav.shoppingapp.domain.useCase.GetProductsInLimit
import com.abhinav.shoppingapp.domain.useCase.GetSpecificCategoryItems
import com.abhinav.shoppingapp.domain.useCase.GetUserUseCase
import com.abhinav.shoppingapp.domain.useCase.LoginUserUseCase
import com.abhinav.shoppingapp.domain.useCase.UpdateUseDataUseCase
import com.abhinav.shoppingapp.domain.useCase.UserProfileImageUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

class ShoppingAppViewModel @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val addToFavUseCase: AddToFavUseCase,
    private val getAllFavUseCase: GetAllFavUseCase,
    private val getCartUseCase: GetCartUseCase,
    private val getAllCategoriesUseCase: GetAllCategoryUseCase,
    private val getCheckOutUseCase: GetCheckOutUseCase,
    private val getSpecificCategoryItemUseCase: GetSpecificCategoryItems,
    private val getAllSuggestedProductsUseCase: GetAllSuggestedProductsUseCase,
    private val getProductsInLimitedUseCase: GetProductsInLimit,
    private val getCategoriesInLimitedUseCase: GetCategoryInLimit,
    private val getAllProductsUseCase: GetAllProductUseCase,
    private val getProductByIdUseCase: GetProductById,
    private val createUserUseCase: CreateUserUseCase,
    private val updateUserDataUseCase: UpdateUseDataUseCase,
    private val userProfileImageUseCase: UserProfileImageUseCase,
    private val getBannerUseCase: GetBannerUseCase,
    private val getProfileUseCase: GetUserUseCase,

    ) : ViewModel() {
    private val _signUpScreeState = MutableStateFlow(SignUpScreenState())
    val signUpScreenState = _signUpScreeState.asStateFlow()

    private val _logInScreenState = MutableStateFlow(LogInScreenState())
    val logInScreenState = _logInScreenState.asStateFlow()

    private val _profileScreenState = MutableStateFlow(ProfileScreenState())
    val profileScreenState = _profileScreenState.asStateFlow()

    private val _updateScreenState = MutableStateFlow(UpdateScreenState())
    val updateScreenState = _updateScreenState.asStateFlow()

    private val _updateUserProfileImageState = MutableStateFlow(UpdateUserProfileImageState())
    val updateUserProfileImageState = _updateUserProfileImageState.asStateFlow()

    private val _addToCartState = MutableStateFlow(AddToCartState())
    val addToCartState = _addToCartState.asStateFlow()

    private val _getProductByIdState = MutableStateFlow(GetProductByIdState())
    val getProductByIdState = _getProductByIdState.asStateFlow()

    private val _addToFavState = MutableStateFlow(AddToFavState())
    val addToFavState = _addToFavState.asStateFlow()

    private val _getAllFavState = MutableStateFlow(GetAllFavState())
    val getAllFavState = _getAllFavState.asStateFlow()

    private val _getAllProductState = MutableStateFlow(GetAllProductState())
    val getAllProductState = _getAllProductState.asStateFlow()

    private val _getCartState = MutableStateFlow(GetCartState())
    val getCartState = _getCartState.asStateFlow()

    private val _getAllCategoriesState = MutableStateFlow(GetAllCategoriesState())
    val getAllCategoriesState = _getAllCategoriesState.asStateFlow()

    private val _getCheckOutState = MutableStateFlow(GetCheckOutState())
    val getCheckOutState = _getCheckOutState.asStateFlow()

    private val _getSpecificCategoryItemState = MutableStateFlow(GetSpecificCategoryItemState())
    val getSpecificCategoryItemState = _getSpecificCategoryItemState.asStateFlow()

    private val _getAllSuggestedProductsState = MutableStateFlow(GetAllSuggestedProductsState())
    val getAllSuggestedProductsState = _getAllSuggestedProductsState.asStateFlow()

    fun getspecificCategoryItems(categoryName: String) {
        viewModelScope.launch {
            getSpecificCategoryItemUseCase.getSpecificCategoryItems(categoryName).collect {
                when (it) {
                    is ResultState.Error -> {
                        _getSpecificCategoryItemState.value =
                            _getSpecificCategoryItemState.value.copy(
                                isLoading = false,
                                errorMessage = it.message
                            )
                    }

                    is ResultState.Loading -> {
                        _getSpecificCategoryItemState.value =
                            _getSpecificCategoryItemState.value.copy(
                                isLoading = true
                            )
                    }

                    is ResultState.Success -> {
                        _getSpecificCategoryItemState.value =
                            _getSpecificCategoryItemState.value.copy(
                                isLoading = false,
                                userData = it.data
                            )
                    }
                }
            }
        }
    }

    fun getCheckOut(productId: String) {
        viewModelScope.launch {
            getCheckOutUseCase.getCheckOutUseCase(productId).collect {
                when (it) {
                    is ResultState.Error -> {
                        _getCheckOutState.value = _getCheckOutState.value.copy(
                            isLoading = false,
                            errorMessage = it.message
                        )
                    }

                    is ResultState.Loading -> {
                        _getCheckOutState.value = _getCheckOutState.value.copy(
                            isLoading = true
                        )
                    }

                    is ResultState.Success -> {
                        _getCheckOutState.value = _getCheckOutState.value.copy(
                            isLoading = false,
                            userData = it.data
                        )
                    }
                }
            }
        }
    }

    fun getAllCategories() {
        viewModelScope.launch {
            getAllCategoriesUseCase.getAllCategories().collect {
                when (it) {
                    is ResultState.Error -> {
                        _getAllCategoriesState.value = _getAllCategoriesState.value.copy(
                            errorMessage = it.message,
                            isLoading = false

                        )
                    }

                    is ResultState.Loading -> {
                        _getAllCategoriesState.value = _getAllCategoriesState.value.copy(
                            isLoading = true
                        )
                    }

                    is ResultState.Success -> {
                        _getAllCategoriesState.value = _getAllCategoriesState.value.copy(
                            isLoading = false,
                            userData = it.data
                        )
                    }

                }
            }

        }
    }

    fun getCart() {
        viewModelScope.launch {
            getCartUseCase.getCart().collect {
                when (it) {
                    is ResultState.Loading -> {
                        _getCartState.value = _getCartState.value.copy(
                            isLoading = true
                        )
                    }

                    is ResultState.Error -> {
                        _getCartState.value = _getCartState.value.copy(

                            errorMessage = it.message,
                            isLoading = false
                        )

                    }

                    is ResultState.Success -> {
                        _getCartState.value = _getCartState.value.copy(
                            isLoading = false,
                            userData = it.data
                        )
                    }
                }
            }
        }
    }

    fun getAllProducts() {
        viewModelScope.launch {
            getAllProductsUseCase.getAllProduct().collect {
                when (it) {
                    is ResultState.Error -> {
                        _getAllProductState.value = _getAllProductState.value.copy(
                            isLoading = false,
                            errorMessage = it.message

                        )
                    }

                    is ResultState.Loading -> {
                        _getAllProductState.value = _getAllProductState.value.copy(
                            isLoading = true
                        )
                    }

                    is ResultState.Success -> {
                        _getAllProductState.value = _getAllProductState.value.copy(
                            isLoading = false,
                            userData = it.data

                        )
                    }
                }
            }
        }
    }

    fun getAllFav() {
        viewModelScope.launch {
            getAllFavUseCase.getAllFavUseCase().collect {
                when (it) {
                    is ResultState.Error -> {
                        _getAllFavState.value = _getAllFavState.value.copy(
                            isLoading = false,
                            errorMessage = it.message
                        )
                    }

                    is ResultState.Loading -> {
                        _getAllFavState.value = _getAllFavState.value.copy(
                            isLoading = true
                        )
                    }

                    is ResultState.Success -> {
                        _getAllFavState.value = _getAllFavState.value.copy(
                            isLoading = false,
                            userData = it.data
                        )
                    }

                }

            }

        }
    }

    fun addToFav(productsDataModels: ProductsDataModels) {
        viewModelScope.launch {
            addToFavUseCase.addToFav(productsDataModels).collect {
                when (it) {
                    is ResultState.Error -> {
                        _addToFavState.value = _addToFavState.value.copy(
                            isLoading = false,
                            errorMessage = it.message
                        )
                    }

                    is ResultState.Loading -> {
                        _addToFavState.value = _addToFavState.value.copy(
                            isLoading = true
                        )
                    }

                    is ResultState.Success -> {
                        _addToFavState.value = _addToFavState.value.copy(
                            isLoading = false,
                            userData = it.data
                        )
                    }

                }
            }
        }
    }

    fun getProductById(productId: String) {
        viewModelScope.launch {
            getProductByIdUseCase.getProductById(productId).collect {
                when (it) {
                    is ResultState.Error -> {
                        _getProductByIdState.value = _getProductByIdState.value.copy(
                            isLoading = false,
                            errorMessage = it.message
                        )
                    }

                    is ResultState.Loading -> {
                        _getProductByIdState.value = _getProductByIdState.value.copy(
                            isLoading = true
                        )
                    }

                    is ResultState.Success -> {
                        _getProductByIdState.value = _getProductByIdState.value.copy(
                            isLoading = false,
                            userData = it.data
                        )
                    }

                }
            }

        }
    }

    fun addToCart(cartDataModels: CartDataModels) {
        viewModelScope.launch {
            addToCartUseCase.addToCart(cartDataModels).collect {
                when (it) {
                    is ResultState.Error -> {
                        _addToCartState.value = _addToCartState.value.copy(
                            isLoading = false,
                            errorMessage = it.message
                        )
                    }

                    is ResultState.Loading -> {
                        _addToCartState.value = _addToCartState.value.copy(
                            isLoading = true
                        )
                    }

                    is ResultState.Success -> {
                        _addToCartState.value = _addToCartState.value.copy(
                            isLoading = false,
                            userData = it.data
                        )
                    }

                }
            }
        }
    }

    private val _homeScreenSate = MutableStateFlow(HomeScreenState())
    val homeScreenState = _homeScreenSate.asStateFlow()

    init {
        loadingHomeScreenData()
    }

    fun loadingHomeScreenData() {
        viewModelScope.launch {
            combine(
                getCategoriesInLimitedUseCase.getCategoryInLimit(),
                getProductsInLimitedUseCase.getProductsInLimit(),
                getBannerUseCase.getBannerUseCase()

            ) { categoriesResult, productResult, bannerResults ->

                when {
                    categoriesResult is ResultState.Error -> {
                        HomeScreenState(isLoading = false, errorMessages = categoriesResult.message)
                    }

                    productResult is ResultState.Error -> {
                        HomeScreenState(isLoading = false, errorMessages = productResult.message)
                    }

                    bannerResults is ResultState.Error -> {
                        HomeScreenState(isLoading = false, errorMessages = bannerResults.message)
                    }

                    categoriesResult is ResultState.Success && productResult is ResultState.Success && bannerResults is ResultState.Success -> {
                        HomeScreenState(
                            isLoading = false,
                            categories = categoriesResult.data,
                            products = productResult.data,
                            banners = bannerResults.data
                        )
                    }

                    else -> {
                        HomeScreenState(
                            isLoading = true

                        )
                    }

                }

            }.collect { state ->
                _homeScreenSate.value = state
            }
        }
    }

    fun upLoadUserProfileImage(uri: Uri) {
        viewModelScope.launch {
            userProfileImageUseCase.userProfileImage(uri).collect {
                when (it) {
                    is ResultState.Error -> {
                        _updateUserProfileImageState.value =
                            _updateUserProfileImageState.value.copy(
                                isLoading = false,
                                errorMessage = it.message
                            )
                    }

                    is ResultState.Loading -> {
                        _updateUserProfileImageState.value =
                            _updateUserProfileImageState.value.copy(
                                isLoading = true
                            )

                    }

                    is ResultState.Success -> {
                        _updateUserProfileImageState.value =
                            _updateUserProfileImageState.value.copy(
                                isLoading = false,
                                userData = it.data
                            )
                    }

                }
            }

        }
    }

    fun updateUserData(userDataParent: UserDataParent) {
        viewModelScope.launch {
            updateUserDataUseCase.updateUseData(userDataParent = userDataParent).collect {
                when (it) {
                    is ResultState.Error -> {
                        _updateScreenState.value = _updateScreenState.value.copy(
                            isLoading = false,
                            errorMessage = it.message

                        )
                    }

                    is ResultState.Loading -> {
                        _updateScreenState.value = _updateScreenState.value.copy(
                            isLoading = true
                        )
                    }

                    is ResultState.Success -> {
                        _updateScreenState.value = _updateScreenState.value.copy(
                            isLoading = false,
                            userData = it.data
                        )
                    }
                }
            }
        }


    }

    fun createUser(userData: UserData) {
        viewModelScope.launch {
            createUserUseCase.createUser(userData).collect {
                when (it) {
                    is ResultState.Error -> {
                        _signUpScreeState.value = _signUpScreeState.value.copy(
                            isLoading = false,
                            errorMessage = it.message
                        )
                    }

                    is ResultState.Loading -> {
                        _signUpScreeState.value = _signUpScreeState.value.copy(
                            isLoading = true
                        )

                    }

                    is ResultState.Success -> {
                        _signUpScreeState.value = _signUpScreeState.value.copy(
                            isLoading = false,
                            userData = it.data
                        )
                    }
                }
            }

        }
    }

    fun loginUser(userData: UserData) {
        viewModelScope.launch {
            loginUserUseCase.loginUser(userData).collect {
                when (it) {
                    is ResultState.Error -> {
                        _logInScreenState.value = _logInScreenState.value.copy(
                            isLoading = false,
                            errorMessage = it.message
                        )
                    }

                    is ResultState.Loading -> {
                        _logInScreenState.value = _logInScreenState.value.copy(
                            isLoading = true
                        )
                    }

                    is ResultState.Success -> {
                        _logInScreenState.value = _logInScreenState.value.copy(
                            isLoading = false,
                            userData = it.data
                        )
                    }
                }
            }
        }
    }

    fun getUserId(uid: String) {
        viewModelScope.launch {
            getProfileUseCase.getUserById(uid).collect {
                when (it) {
                    is ResultState.Error -> {
                        _profileScreenState.value = _profileScreenState.value.copy(
                            isLoading = false,
                            errorMessage = it.message
                        )
                    }

                    is ResultState.Loading -> {
                        _profileScreenState.value = _profileScreenState.value.copy(
                            isLoading = true
                        )
                    }

                    is ResultState.Success -> {
                        _profileScreenState.value = _profileScreenState.value.copy(
                            isLoading = false,
                            userData = it.data
                        )
                    }
                }

            }
        }
    }

    fun getAllSuggestedProducts() {
        viewModelScope.launch {
            getAllSuggestedProductsUseCase.getAllSuggestedProducts().collect {
                when (it) {
                    is ResultState.Error -> {
                        _getAllSuggestedProductsState.value =
                            _getAllSuggestedProductsState.value.copy(
                                isLoading = false,
                                errorMessage = it.message
                            )
                    }

                    is ResultState.Loading -> {
                        _getAllSuggestedProductsState.value =
                            _getAllSuggestedProductsState.value.copy(
                                isLoading = true
                            )
                    }

                    is ResultState.Success -> {
                        _getAllSuggestedProductsState.value =
                            _getAllSuggestedProductsState.value.copy(
                                isLoading = false,
                                userData = it.data
                            )
                    }

                }
            }
        }

    }
}

data class ProfileScreenState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userData: UserDataParent? = null
)

data class SignUpScreenState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userData: String? = null
)

data class LogInScreenState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userData: String? = null
)

data class UpdateScreenState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userData: String? = null
)

data class UpdateUserProfileImageState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userData: String? = null
)

data class AddToCartState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userData: String? = null
)

data class GetProductByIdState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userData: ProductsDataModels? = null
)

data class AddToFavState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userData: String? = null
)

data class GetAllFavState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userData: List<ProductsDataModels?> = emptyList()
)

data class GetAllProductState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userData: List<ProductsDataModels?> = emptyList()
)

data class GetCartState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userData: List<CartDataModels?> = emptyList()
)

data class GetAllCategoriesState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userData: List<CategoryDataModels?> = emptyList()
)

data class GetCheckOutState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userData: ProductsDataModels? = null
)

data class GetSpecificCategoryItemState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userData: List<ProductsDataModels?> = emptyList()
)

data class GetAllSuggestedProductsState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userData: List<ProductsDataModels?> = emptyList()
)
