package com.example.gachicar

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gachicar.databinding.ActivityEditgroupdescBinding
import com.example.gachicar.retrofit.EditGroup_Service
import com.example.gachicar.retrofit.RetrofitConnection
import com.example.gachicar.retrofit.UpdateGroupDesc
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GroupDescEditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditgroupdescBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_GachiCar)
        super.onCreate(savedInstanceState)
        binding = ActivityEditgroupdescBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.getStringExtra("groupName")?.let {
            binding.tvGroupName.setText(it)
        }
        intent.getStringExtra("groupDesc")?.let {
            binding.tvOneLineDesc.setText(it)
        }
        intent.getStringExtra("carNickname")?.let {
            binding.tvCarNickname.text = it
        }
        intent.getStringExtra("groupLeaderName")?.let {
            binding.tvGroupLeaderName.text = it
        }

        binding.editGroupDesc.setOnClickListener {
            patchGroupData()
        }
    }

    private fun patchGroupData() {
        val updatedGroupDesc = binding.editGroupDesc.text.toString()
        val groupDescUpdate = UpdateGroupDesc(data = updatedGroupDesc, code = 0, message = "")

        val retrofitAPI = RetrofitConnection.getInstance().create(EditGroup_Service::class.java)

        // 그룹 설명 업데이트 API 호출
        retrofitAPI.patchGroupDesc(groupDescUpdate).enqueue(object : Callback<UpdateGroupDesc> {
            override fun onResponse(call: Call<UpdateGroupDesc>, response: Response<UpdateGroupDesc>) {
                if (response.isSuccessful) {
                    Toast.makeText(applicationContext, "그룹 설명이 업데이트 되었습니다.", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@GroupDescEditActivity, MainActivity::class.java).apply {
                        putExtra("navigateToProfile", true)
                    }
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(applicationContext, "그룹 설명 업데이트 실패", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UpdateGroupDesc>, t: Throwable) {
                Toast.makeText(applicationContext, "API 호출 실패: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
