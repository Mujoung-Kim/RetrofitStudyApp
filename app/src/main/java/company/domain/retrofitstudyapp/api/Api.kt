package company.domain.retrofitstudyapp.api

import company.domain.retrofitstudyapp.models.DefaultResponse
import company.domain.retrofitstudyapp.models.LoginResponse
import company.domain.retrofitstudyapp.models.UserResponse

import retrofit2.Call
import retrofit2.http.*

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

    @FormUrlEncoded
    @PUT("updateuser/{id}")
    fun updateUser(
        @Path("id") id: Int,
        @Field("email") email: String,
        @Field("name") name: String,
        @Field("school") school: String
    ): Call<LoginResponse>

    @FormUrlEncoded
    @PUT("updatepassword")
    fun updatePassword(
        @Field("currentpassword") currentPassword: String,
        @Field("newpassword") newPassword: String,
        @Field("email") email: String
    ): Call<DefaultResponse>

    @DELETE("deleteuser/{id}")
    fun deleteUser(
        @Path("id") id: Int
    ): Call<DefaultResponse>

}