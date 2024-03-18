package com.example.gachicar.retrofit
import com.example.gachicar.retrofit.DriveReport
import com.example.gachicar.retrofit.MostUser
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface statistic_r_Service {
    @GET("/api/car/fuel")
    // fun getFuelData(@Query("fuelType") fuelType: String, @Query("oilStatus") oilStatus: Int): Call<statistic_r_Response>
    fun getFuelData(): Call<statistic_r_Response>

    @GET("/api/report")
    fun getDriveReport(): Call<DriveReport>

    @GET("/api/report/most")
    fun getMostUser(): Call<MostUser>

}