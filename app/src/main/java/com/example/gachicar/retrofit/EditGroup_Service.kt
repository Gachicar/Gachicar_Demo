package com.example.gachicar.retrofit

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.PATCH

interface EditGroup_Service {
    @PATCH("/api/group/updateDesc")
    fun patchGroupDesc(@Body desc: UpdateGroupDesc): Call<UpdateGroupDesc>

    @PATCH("/api/group/updateName")
    fun patchGroupName(@Body name: UpdateGroupName): Call<UpdateGroupName>
}
