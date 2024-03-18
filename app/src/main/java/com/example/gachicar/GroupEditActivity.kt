package com.example.gachicar

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gachicar.databinding.ActivityEditgroupBinding
import com.example.gachicar.retrofit.EditGroup_Service
import com.example.gachicar.retrofit.RetrofitConnection
import com.example.gachicar.retrofit.UpdateGroupDesc
import com.example.gachicar.retrofit.UpdateGroupName
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GroupEditActivity : AppCompatActivity(){
    private lateinit var binding: ActivityEditgroupBinding

    override fun onCreate(savedInstanceState: Bundle?){
        setTheme(R.style.Theme_GachiCar)
        super.onCreate(savedInstanceState)
        binding = ActivityEditgroupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.getStringExtra("groupName")?.let {
            binding.etGroupName.setText(it)
        }
        intent.getStringExtra("groupDesc")?.let {
            binding.etOneLineDesc.setText(it)
        }
        intent.getStringExtra("carNickname")?.let {
            binding.tvCarNickname.text = it
        }
        intent.getStringExtra("groupLeaderName")?.let {
            binding.tvGroupLeaderName.text = it
        }

        binding.finishEditGroup.setOnClickListener {
            patchGroupData()

        }
    }

    private fun patchGroupData() {
        val updatedGroupName = binding.etGroupName.text.toString()
        val updatedGroupDesc = binding.etOneLineDesc.text.toString()

        val groupNameUpdate = UpdateGroupName(data = updatedGroupName, code = 0, message = "")
        val groupDescUpdate = UpdateGroupDesc(data = updatedGroupDesc, code = 0, message = "")

        val retrofitAPI = RetrofitConnection.getInstance().create(EditGroup_Service::class.java)

        // 그룹 이름 업데이트
        retrofitAPI.patchGroupName(groupNameUpdate).enqueue(object : Callback<UpdateGroupName> {
            override fun onResponse(call: Call<UpdateGroupName>, response: Response<UpdateGroupName>) {
                if (response.isSuccessful) {
                    Toast.makeText(applicationContext, "그룹 이름 업데이트 성공", Toast.LENGTH_SHORT).show()
                    finish() // 현재 액티비티 종료
                } else {
                    showError("그룹 이름 업데이트 실패")
                }
            }

            override fun onFailure(call: Call<UpdateGroupName>, t: Throwable) {
                showError("API 호출 실패: ${t.message}")
            }
        })

        // 그룹 설명 업데이트
        retrofitAPI.patchGroupDesc(groupDescUpdate).enqueue(object : Callback<UpdateGroupDesc> {
            override fun onResponse(call: Call<UpdateGroupDesc>, response: Response<UpdateGroupDesc>) {
                if (response.isSuccessful) {
                    Toast.makeText(applicationContext, "그룹 설명 업데이트 성공", Toast.LENGTH_SHORT).show()
                    finish() // 여기도 성공적으로 업데이트 후 액티비티 종료
                } else {
                    showError("그룹 설명 업데이트 실패")
                }
            }

            override fun onFailure(call: Call<UpdateGroupDesc>, t: Throwable) {
                showError("API 호출 실패: ${t.message}")
            }
        })
    }



    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

}