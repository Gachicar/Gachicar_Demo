package com.example.gachicar
import android.content.Intent
import android.os.Bundle
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
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!


    override fun onResume() {
        super.onResume()
        getGroupData() // 프래그먼트가 다시 화면에 나타날 때마다 데이터를 새로 가져옵니다.
    }


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
        binding.editGroupName.setOnClickListener {
            val intent = Intent(activity, GroupNameEditActivity::class.java)
            intent.putExtra("groupName", binding.tvGroupName.text.toString())
            intent.putExtra("groupDesc", binding.tvOneLineDesc.text.toString())
            intent.putExtra("carNickname", binding.tvCarNickname.text.toString())
            intent.putExtra("groupLeaderName", binding.tvGroupLeaderName.text.toString())
            startActivity(intent)
        }

        binding.editGroupDesc.setOnClickListener {
            val intent = Intent(activity, GroupDescEditActivity::class.java)
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
        retrofitAPI.getGroupInfo()
            .enqueue(object : Callback<GetGroupInfo> {
                override fun onResponse(call: Call<GetGroupInfo>, response: Response<GetGroupInfo>) {
                    if (response.isSuccessful) {
                    // 성공적으로 데이터를 받아온 경우 UI 업데이트
                        response.body()?.let { updateUIWithGroupData(it) }
                } else {
                    // 데이터를 받아오는데 실패한 경우
                        // API 응답이 실패한 경우 에러 메시지를 출력합니다.
                        Toast.makeText(requireContext(), "그룹정보 데이터를 가져오는 데 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<GetGroupInfo>, t: Throwable) {
                // API 호출 자체가 실패한 경우
                Toast.makeText(requireContext(), "API 호출 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                t.printStackTrace()
            }
        })
    }


    private fun updateUIWithGroupData(showGroupInfo: GetGroupInfo) {
        // UI 업데이트 로직
        val groupName = showGroupInfo.data.name
        val description = showGroupInfo.data.desc
        val groupLeader = showGroupInfo.data.groupManager.name
        val carNickName = showGroupInfo.data.car.carName

        binding.tvGroupName.text = groupName
        binding.tvOneLineDesc.text = description
        binding.tvGroupLeaderName.text = groupLeader
        binding.tvCarNickname.text = carNickName

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}