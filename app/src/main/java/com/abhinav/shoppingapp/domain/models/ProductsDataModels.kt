package com.abhinav.shoppingapp.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class ProductsDataModels(
    var name: String = "",
    var description: String = "",
    var price: String = "",
    var finalPrice: String = "",
    var category: String = "",
    var image: String = "",
    var date:Long = System.currentTimeMillis(),
    var createBy: String = "",
    var availableUnits: Int =0,
    var productId:String =""

    )
