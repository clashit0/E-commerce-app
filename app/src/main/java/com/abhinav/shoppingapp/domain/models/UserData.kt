package com.abhinav.shoppingapp.domain.models

import androidx.compose.runtime.mutableStateMapOf

data class UserData(
    var firstName:String = "",
    var lastName:String = "",
    var email:String = "",
    var password:String = "",
    var phoneNumber:String = "",
    var address:String = "",
    var profileImage:String = "",
){
    fun toMap():Map<String , Any>{
        val map = mutableStateMapOf<String,Any>()
        map["firstName"] = firstName
        map["lastName"] = lastName
        map["email"] = email
        map["password"] = password
        map["phoneNumber"] = phoneNumber
        map["address"] = address
        map["profileImage"] = profileImage
        return map
    }
}

data class UserDataParent(
    val nodeId: String = "",
    val userData: UserData = UserData()
)
