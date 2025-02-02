package com.goddoro.common.data.api

import android.os.Parcelable
import com.goddoro.common.data.model.Event
import com.goddoro.common.data.model.Notification
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap
import com.goddoro.common.data.api.response.ApiResponse

/**
 * created By DORO 2/2/21
 */

interface NotificationAPI {


    @GET("v1/notification")
    suspend fun listNotifications(

    ) : ApiResponse<NotificationListResponse>

}

@Parcelize
data class NotificationListResponse(
    @SerializedName("notifications")
    val notifications : List<Notification>
) : Parcelable

//@Parcelize
//data class EventResponse(
//    @SerializedName("event")
//    val event : Event
//) : Parcelable