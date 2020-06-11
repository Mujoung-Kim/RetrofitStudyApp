package company.domain.retrofitstudyapp.api

import company.domain.retrofitstudyapp.models.DefaultResponse
import company.domain.retrofitstudyapp.models.LoginResponse
import company.domain.retrofitstudyapp.models.UserResponse

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {

    @FormUrlEncoded
    @POST("createuser")
    fun createUser(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("name") name: String,
        @Field("school") school: String
    ): Call<DefaultResponse>

    @FormUrlEncoded
    @POST("userlogin")
    fun userLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @GET("allusers")
    fun getUsers(): Call<UserResponse>

}