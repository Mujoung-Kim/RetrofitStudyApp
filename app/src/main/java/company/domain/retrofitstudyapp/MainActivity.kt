package company.domain.retrofitstudyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

import retrofit2.Call
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonSignUp.setOnClickListener {
            userSingUp()

        }
        textViewLogin.setOnClickListener {
            //
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

        val call: Call<ResponseBody>? = RetrofitClient
            .instance
            ?.api
            ?.createUser(email, password, name, school)

        call?.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                var string: String? = null

                try {
                    if (response.code() == 201) string = response.body().toString()
                    else string = response.errorBody().toString()

                } catch (error: IOException) {
                    error.printStackTrace()

                }

                if (string != null) {
                    try {
                        val jsonObject: JSONObject = JSONObject(string)

                        Toast.makeText(
                            this@MainActivity,
                            jsonObject.getString("message"),
                            Toast.LENGTH_LONG
                        ).show()

                    } catch (error: JSONException) {
                        error.printStackTrace()

                    }
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_LONG).show()

            }
        })

    }
}
