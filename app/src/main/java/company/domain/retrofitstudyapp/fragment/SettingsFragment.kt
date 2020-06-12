package company.domain.retrofitstudyapp.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment

import company.domain.retrofitstudyapp.R
import company.domain.retrofitstudyapp.activites.LoginActivity
import company.domain.retrofitstudyapp.activites.MainActivity
import company.domain.retrofitstudyapp.api.RetrofitClient
import company.domain.retrofitstudyapp.models.DefaultResponse
import company.domain.retrofitstudyapp.models.LoginResponse
import company.domain.retrofitstudyapp.models.User
import company.domain.retrofitstudyapp.storage.SharedPrefManager

import kotlinx.android.synthetic.main.fragment_settings.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SettingsFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextEmail
        editTextName
        editTextSchool
        editTextCurrentPassword
        editTextNewPassword

        buttonSave.setOnClickListener {
            updateProfile()

        }

        buttonChangePassword.setOnClickListener {
            updatePassword()

        }

        buttonLogout.setOnClickListener {
            logout()

        }

        buttonDelete.setOnClickListener {
            deleteUser()

        }
    }

    private fun updateProfile() {
        val email: String = editTextEmail.text.toString()
        val name: String = editTextName.text.toString()
        val school: String = editTextSchool.text.toString()

        if (email.isEmpty()) {
            editTextEmail.error = "Email is required."
            editTextEmail.requestFocus()
            return

        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.error = "Enter a valid email."
            editTextEmail.requestFocus()
            return

        }

        if (name.isEmpty()) {
            editTextName.error = "Name required."
            editTextName.requestFocus()
            return

        }

        if (school.isEmpty()) {
            editTextSchool.error = "School required."
            editTextSchool.requestFocus()
            return

        }

        val user: User = SharedPrefManager.getInstance(activity)?.user!!
        val call: Call<LoginResponse>? = RetrofitClient.instance?.api?.updateUser(
            user.id, email, name, school
        )

        call?.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                Toast.makeText(activity, response.body().toString(), Toast.LENGTH_LONG).show()

                if (response.body()?.isError!!)
                    SharedPrefManager.getInstance(activity)?.saveUser(response.body()!!.user)

            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun updatePassword() {
        val currentPassword: String = editTextCurrentPassword.text.toString()
        val newPassword: String = editTextNewPassword.text.toString()

        if (currentPassword.isEmpty()) {
            editTextCurrentPassword.error = "Password required"
            editTextCurrentPassword.requestFocus()
            return

        }

        if (newPassword.isEmpty()) {
            editTextNewPassword.error = "Enter new password"
            editTextNewPassword.requestFocus()
            return

        }

        val user: User = SharedPrefManager.getInstance(activity)?.user!!
        val call: Call<DefaultResponse>? =
            RetrofitClient.instance?.api?.updatePassword(currentPassword, newPassword, user.email)

        call?.enqueue(object : Callback<DefaultResponse> {
            override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                Toast.makeText(activity, response.body().toString(), Toast.LENGTH_LONG).show()

            }

            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {

            }
        })
    }

    private fun logout() {
        SharedPrefManager.getInstance(activity)!!.clear()

        val intent = Intent(activity, LoginActivity::class.java)

        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)

    }

    private fun deleteUser() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)

        builder.setTitle("Are you sure?")
        builder.setMessage("This action is irreversible...")
        builder.setPositiveButton("Yes") {
                dialog, which ->

            val user: User = SharedPrefManager.getInstance(activity)?.user!!
            val call: Call<DefaultResponse>? = RetrofitClient.instance?.api?.deleteUser(user.id)

            call?.enqueue(object : Callback<DefaultResponse> {
                override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                    if (response.body()?.isError!!) {
                        SharedPrefManager.getInstance(activity)!!.clear()

                        val intent = Intent(activity, MainActivity::class.java)

                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)

                    }
                    Toast.makeText(activity, response.body()!!.msg, Toast.LENGTH_LONG).show()

                }

                override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {

                }
            })
        }
        builder.setPositiveButton("No") {
                dialog, which -> TODO("Not yet implemented")

        }

        val ad: AlertDialog = builder.create()

        ad.show()

    }
}