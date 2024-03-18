package com.example.gachicar.retrofit
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface CreateGroup_Service {
    @POST("/api/group")
    fun postGroupNameData(
        @Body groupNameData: CreateGroup.GroupNameData
    ): Call<CreateGroup>
}
