package company.domain.retrofitstudyapp.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast

import company.domain.retrofitstudyapp.models.DefaultResponse
import company.domain.retrofitstudyapp.R
import company.domain.retrofitstudyapp.api.RetrofitClient
import company.domain.retrofitstudyapp.storage.SharedPrefManager

import kotlinx.android.synthetic.main.activity_main.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonSignUp.setOnClickListener {
            userSingUp()

        }
        textViewLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))

        }
    }

    override fun onStart() {
        super.onStart()

        if (SharedPrefManager.getInstance(this)?.isLoggedIn!!) {
            val intent = Intent(this, ProfileActivity::class.java)

            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)

        }
    }

    private fun userSingUp() {
        val email: String = editTextEmail.text.toString()
        val password: String = editTextPassword.text.toString()
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

        if (password.isEmpty()) {
            editTextPassword.error = "Password required."
            editTextPassword.requestFocus()
            return

        }

        if (password.length < 6) {
            editTextPassword.error = "Password should be atleast 6 charactor long."
            editTextPassword.requestFocus()
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

        val call: Call<DefaultResponse>? = RetrofitClient
            .instance?.api?.createUser(email, password, name, school)

        call?.enqueue(object : Callback<DefaultResponse> {
            override fun onResponse(
                call: Call<DefaultResponse>,
                response: Response<DefaultResponse>
            ) {
                if (response.code() == 201) {
                    val dr: DefaultResponse? = response.body()

                    Toast.makeText(this@MainActivity, dr?.msg, Toast.LENGTH_LONG).show()

                } else if (response.code() == 422) {
                    Toast.makeText(this@MainActivity, "User already exist", Toast.LENGTH_LONG)
                        .show()

                }
            }

            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                TODO("Not yet implemented")

            }
        })

//        call?.enqueue(object : Callback<ResponseBody> {
//            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                var string: String? = null
//
//                try {
//                    if (response.code() == 201) string = response.body().toString()
//                    else string = response.errorBody().toString()
//
//                } catch (error: IOException) {
//                    error.printStackTrace()
//
//                }
//
//                if (string != null) {
//                    try {
//                        val jsonObject: JSONObject = JSONObject(string)
//
//                        Toast.makeText(
//                            this@MainActivity,
//                            jsonObject.getString("message"),
//                            Toast.LENGTH_LONG
//                        ).show()
//
//                    } catch (error: JSONException) {
//                        error.printStackTrace()
//
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_LONG).show()
//
//            }
//        })
//
//    }
    }
}
