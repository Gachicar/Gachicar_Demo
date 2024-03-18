package com.example.gachicar.retrofit

import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.Path

interface loginClient {
    @DELETE("/api/group/{groupId}")
    fun deleteGroup(@Path("groupId") groupId: Int): Call<DeleteGroup>
}
