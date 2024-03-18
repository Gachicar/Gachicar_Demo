package com.example.gachicar.retrofit

data class CreateGroup(
    val code: Int,
    val `data`: Any,
    val message: String
){
    data class GroupNameData(
        val groupName: String,
        val groupDesc: String
    )

}