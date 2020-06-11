package company.domain.retrofitstudyapp.models

import com.google.gson.annotations.SerializedName

class DefaultResponse(
    @field:SerializedName("error") val isError: Boolean,
    @field:SerializedName("message") val msg: String

)
