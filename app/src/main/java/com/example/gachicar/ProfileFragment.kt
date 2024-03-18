package com.example.gachicar
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.gachicar.databinding.FragmentProfileBinding
import com.example.gachicar.retrofit.GetGroupInfo
import com.example.gachicar.retrofit.GetGroupInfo_Service
import com.example.gachicar.retrofit.RetrofitConnection
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment() {

    override fun onResume() {
        super.onResume()
        getGroupData() // 프래그먼트가 다시 화면에 나타날 때마다 데이터를 새로 가져옵니다.
    }

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getGroupData()
        val retrofitAPI = RetrofitConnection.getInstance().create(GetGroupInfo_Service::class.java)
        binding.editGroupDetail.setOnClickListener {
            val intent = Intent(activity, GroupEditActivity::class.java)
            intent.putExtra("groupName", binding.tvGroupName.text.toString())
            intent.putExtra("groupDesc", binding.tvOneLineDesc.text.toString())
            intent.putExtra("carNickname", binding.tvCarNickname.text.toString())
            intent.putExtra("groupLeaderName", binding.tvGroupLeaderName.text.toString())
            startActivity(intent)
        }

    }

    private fun getGroupData() {
        val retrofitAPI = RetrofitConnection.getInstance().create(GetGroupInfo_Service::class.java)

        // API 호출
        retrofitAPI.getGroupInfo().enqueue(object : Callback<GetGroupInfo> {
            override fun onResponse(call: Call<GetGroupInfo>, response: Response<GetGroupInfo>) {
                if (response.isSuccessful && response.body() != null) {
                    // 성공적으로 데이터를 받아온 경우 UI 업데이트
                    Log.d("ProfileFragment", "Server Response: ${response.body()}")
                    val groupInfo = response.body()!!
                    updateUIWithGroupData(groupInfo.data)
                } else {
                    // 데이터를 받아오는데 실패한 경우
                    showError("그룹 정보를 가져오는데 실패했습니다.")
                }
            }

            override fun onFailure(call: Call<GetGroupInfo>, t: Throwable) {
                // API 호출 자체가 실패한 경우
                showError("API 호출 중 오류가 발생했습니다.")
            }
        })
    }


    private fun updateUIWithGroupData(data: GetGroupInfo.Data) {
        // UI 업데이트 로직
        binding.tvGroupName.text = data.name
        binding.tvOneLineDesc.text = data.desc
        binding.tvGroupLeaderName.text = data.groupManager.name
        binding.tvCarNickname.text = data.car.name

        // car 객체 null 체크
        data.car?.let {
            binding.tvCarNickname.text = it.name
        } ?: showError("차량 정보를 가져올 수 없습니다.")

    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}