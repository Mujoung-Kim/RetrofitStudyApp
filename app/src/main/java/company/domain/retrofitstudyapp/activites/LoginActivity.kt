package company.domain.retrofitstudyapp.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast

import company.domain.retrofitstudyapp.R
import company.domain.retrofitstudyapp.api.RetrofitClient
import company.domain.retrofitstudyapp.models.LoginResponse
import company.domain.retrofitstudyapp.storage.SharedPrefManager

import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.editTextEmail
import kotlinx.android.synthetic.main.activity_login.editTextPassword

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        buttonLogin.setOnClickListener {
            userLogin()

        }
        textViewRegister.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))

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

    private fun userLogin() {
        val email: String = editTextEmail.text.toString()
        val password: String = editTextPassword.text.toString()

        if (email.isEmpty()) {
            editTextEmail.error = "Email is required."
            editTextEmail.requestFocus()
            return

        }

        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
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

        val call: Call<LoginResponse>? = RetrofitClient
            .instance?.api?.userLogin(email, password)

        call?.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                val loginResponse: LoginResponse = response.body()!!

                if (!loginResponse.isError) {
                    //  save user & open profile
                    SharedPrefManager.getInstance(this@LoginActivity)?.saveUser(loginResponse.user)

                    val intent = Intent(this@LoginActivity, ProfileActivity::class.java)

                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)

                } else {
                    Toast.makeText(this@LoginActivity, loginResponse.message, Toast.LENGTH_LONG).show()

                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}
