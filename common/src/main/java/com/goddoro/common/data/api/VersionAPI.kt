package com.goddoro.common.data.api

import android.os.Parcelable
import com.goddoro.common.data.api.response.ApiResponse
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import retrofit2.http.GET

interface VersionAPI {
    @GET("v1/version/min")
    suspend fun getMinimumVersion() : ApiResponse<MinimumVersion>
}


@Parcelize
data class MinimumVersion(

    @SerializedName("minVersion")
    val version : Int
) : Parcelable