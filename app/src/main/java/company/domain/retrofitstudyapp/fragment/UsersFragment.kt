package company.domain.retrofitstudyapp.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager

import company.domain.retrofitstudyapp.R
import company.domain.retrofitstudyapp.adapters.UserAdapter
import company.domain.retrofitstudyapp.api.RetrofitClient
import company.domain.retrofitstudyapp.models.UserResponse

import kotlinx.android.synthetic.main.fragment_users.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsersFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_users, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.setHasFixedSize(true)

        val call: Call<UserResponse>? = RetrofitClient.instance?.api?.getUsers()

        call?.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                val userList = response.body()?.user

                Log.d("Log", userList.toString())
                val adapter = UserAdapter(activity, userList!!)

                recyclerView.adapter = adapter

            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {

            }
        })

    }
}