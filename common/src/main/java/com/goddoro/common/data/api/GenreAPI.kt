package com.goddoro.common.data.api

import android.os.Parcelable
import com.goddoro.common.data.api.response.ApiResponse
import com.goddoro.common.data.model.Event
import com.goddoro.common.data.model.Genre
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import retrofit2.http.FieldMap
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface GenreAPI {

    @GET("v1/genre")
    suspend fun listGenres(
        @QueryMap parameters : HashMap<String,Any>
    ) : ApiResponse<GenreListResponse>

}


@Parcelize
data class GenreListResponse(
    @SerializedName("genres")
    val genres : List<Genre>
) : Parcelable