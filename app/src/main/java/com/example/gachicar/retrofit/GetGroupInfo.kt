package com.example.gachicar.retrofit

data class GetGroupInfo(
    val code: Int,
    val `data`: Data,
    val message: String
) {
    data class Data(
        val car: Car,
        val desc: String,
        val groupId: Int,
        val groupManager: GroupManager,
        val name: String
    ) {
        data class Car(
            val id: Int,
            val name: String
        )

        data class GroupManager(
            val id: Int,
            val name: String
        )
    }
}