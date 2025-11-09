package com.abhinav.shoppingapp.domain.models

data class UserAddress(
    var firstName:String = "",
    var lastName:String = "",
    var address:String = "",
    var city:String = "",
    var state:String = "",
    var pincode:String = "",
    var country:String = "",
    var phoneNumber:String = "",
)
