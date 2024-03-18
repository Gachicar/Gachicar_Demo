package com.example.gachicar.retrofit

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface GroupService {
    @POST("/api/group")
    fun postGroupNameData(
        @Body groupNameData: CreateGroup.GroupNameData
    ): Call<CreateGroup>

    @DELETE("/api/group/{groupId}")
    fun deleteGroup(@Path("groupId") groupId: Int): Call<DeleteGroup>

    @GET("/api/group")
    fun getGroupInfo(): Call<GetGroupInfo>
}