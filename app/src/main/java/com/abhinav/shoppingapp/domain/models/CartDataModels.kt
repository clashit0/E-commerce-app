package com.abhinav.shoppingapp.domain.models

data class CartDataModels(
    val productId:String = "",
    val name:String ="",
    var image:String ="",
    var price :String ="",
    var quantity:String ="",
    var cartId:String ="",
    var size:String ="",
    var description:String ="",
    var category:String ="",
)
