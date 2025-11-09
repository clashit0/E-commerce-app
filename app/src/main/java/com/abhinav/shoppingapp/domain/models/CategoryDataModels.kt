package com.abhinav.shoppingapp.domain.models

data class CategoryDataModels(
    var name:String = "",
    var date:Long = System.currentTimeMillis(),
    var createBy:String = "",
    var categoryImage:String = "",
)
