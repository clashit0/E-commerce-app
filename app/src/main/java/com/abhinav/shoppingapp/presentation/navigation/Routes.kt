package com.abhinav.shoppingapp.presentation.navigation

import kotlinx.serialization.Serializable

sealed class SubNavigation{
    @Serializable
    object LoginSignUpScreen : SubNavigation(){
        const val route = "login_signup_screen"
    }

    @Serializable
    object MainHomeScreen : SubNavigation(){
        const val route = "main_home_screen"
    }
}

sealed class Routes {
    @Serializable
    object LoginScreen {
        const val route = "login_screen"  // 🏷️ Add this!
    }

    @Serializable
    object SignUpScreen {
        const val route = "signup_screen"  // 🏷️ Add this!
    }

    @Serializable
    object HomeScreen {
        const val route = "home_screen"  // 🏷️ Add this!
    }

    @Serializable
    object ProfileScreen {
        const val route = "profile_screen"  // 🏷️ Add this!
    }

    @Serializable
    object WishlistScreen {
        const val route = "wishlist_screen"  // 🏷️ Add this!
    }

    @Serializable
    object CartScreen {
        const val route = "cart_screen"  // 🏷️ Add this!
    }

    @Serializable
    data class CheckoutScreen(val productId: String) {
        companion object {
            const val route = "checkout_screen"  // 🏷️ Add this!
        }
    }

    @Serializable
    object PayScreen {
        const val route = "pay_screen"  // 🏷️ Add this!
    }

    @Serializable
    object SeeAllProductScreen {
        const val route = "see_all_products_screen"  // 🏷️ Add this!
    }

    @Serializable
    data class EachProductDetailsScreen(val productId: String) {
        companion object {
            const val route = "product_details_screen"  // 🏷️ Add this!
        }
    }

    @Serializable
    object AllCategoriesScreen {
        const val route = "all_categories_screen"  // 🏷️ Add this!
    }

    @Serializable
    data class EachCategoryItemScreens(val categoryName: String) {
        companion object {
            const val route = "category_items_screen"  // 🏷️ Add this!
        }
    }
}