//package com.example.gachicar
//
//import android.content.Intent
//import android.os.Bundle
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import com.example.gachicar.databinding.ActivityEditgroupnameBinding
//import com.example.gachicar.retrofit.EditGroup_Service
//import com.example.gachicar.retrofit.RetrofitConnection
//import com.example.gachicar.retrofit.UpdateGroupDesc
//import com.example.gachicar.retrofit.UpdateGroupName
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//class GroupNameEditActivity : AppCompatActivity(){
//    private lateinit var binding: ActivityEditgroupnameBinding
//
//    override fun onCreate(savedInstanceState: Bundle?){
//        setTheme(R.style.Theme_GachiCar)
//        super.onCreate(savedInstanceState)
//        binding = ActivityEditgroupnameBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        intent.getStringExtra("groupName")?.let {
//            binding.tvGroupName.setText(it)
//        }
//        intent.getStringExtra("groupDesc")?.let {
//            binding.tvOneLineDesc.setText(it)
//        }
//        intent.getStringExtra("carNickname")?.let {
//            binding.tvCarNickname.text = it
//        }
//        intent.getStringExtra("groupLeaderName")?.let {
//            binding.tvGroupLeaderName.text = it
//        }
//
//        binding.editGroupName.setOnClickListener {
//            patchGroupData()
//        }
//    }
//
//    private fun patchGroupData() {
//        val updatedGroupName = binding.editGroupName.text.toString()
//        val groupNameUpdate = UpdateGroupName(data = updatedGroupName, code = 0, message = "")
//
//        val retrofitAPI = RetrofitConnection.getInstance().create(EditGroup_Service::class.java)
//
//        // 그룹 이름 업데이트 API 호출
//        retrofitAPI.patchGroupName(groupNameUpdate).enqueue(object : Callback<UpdateGroupName> {
//            override fun onResponse(call: Call<UpdateGroupName>, response: Response<UpdateGroupName>) {
//                if (response.isSuccessful) {
//                    // 성공적으로 업데이트됐을 때
//                    Toast.makeText(applicationContext, "그룹 이름이 업데이트 되었습니다.", Toast.LENGTH_SHORT).show()
//
//                    // MainActivity로 이동하며 ProfileFragment로 바로 이동할 것임을 알림
//                    val intent = Intent(this@GroupNameEditActivity, MainActivity::class.java)
//                    intent.putExtra("navigateToProfile", true)
//                    startActivity(intent)
//                    finish() // 현재 액티비티 종료
//                } else {
//                    // 업데이트 실패
//                    Toast.makeText(applicationContext, "그룹 이름 업데이트 실패", Toast.LENGTH_SHORT).show()
//                }
//            }
//
//            override fun onFailure(call: Call<UpdateGroupName>, t: Throwable) {
//                // API 호출 실패
//                Toast.makeText(applicationContext, "API 호출 실패: ${t.message}", Toast.LENGTH_SHORT).show()
//            }
//        })
//    }
//
//
//
//
//
//    private fun showError(message: String) {
//        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
//    }
//}