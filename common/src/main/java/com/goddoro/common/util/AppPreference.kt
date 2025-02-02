package com.goddoro.common.util

import android.annotation.SuppressLint
import android.app.Application
import android.content.SharedPreferences
import android.content.pm.PackageManager
import androidx.preference.PreferenceManager
import com.goddoro.common.di.ServerType


/**
 * created By DORO 2020/10/02
 */

class AppPreference(context: Application) {

    private val preference: SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)

    enum class KEY(val key: String) {
        // FCM token
        KEY_FCM_TOKEN("KEY_FCM_TOKEN"),

        // 알림 수신 동의
        // 맨 처음 한번만 display한다.
        KEY_NEED_SHOW_PUSH_ALARM_CONFIRM("KEY_NEED_SHOW_PUSH_ALARM_CONFIRM"),

        // 최신 sw버전
        KEY_LATEST_SW_VERSION("KEY_LATEST_SW_VERSION"),

        // Device UUID
        KEY_DEVICE_UUID("KEY_DEVICE_UUID"),

        // 앱 접근권한확인
        KEY_SHOW_APP_ACCESS_RIGHT("KEY_SHOW_APP_ACCESS_RIGHT"),

        // 마지막으로 동의한 약관 버전
        KEY_ACCEPTED_TERMS_VERSION("KEY_ACCEPTED_TERMS_VERSION"),

        // Login server
        KEY_SERVER("KEY_LOGIN_SERVER"),

        KEY_UPLOADING_REQUEST_ID("KEY_UPLOADING_REQUEST_ID"),

        KEY_USER("KEY_USER"),

        KEY_POP_UP_DATE("KEY_POP_UP_DATE"),

        KEY_MINIMUM_VERSION("KEY_MINIMUM_VERSION")
    }

    @SuppressLint("ApplySharedPref")
    private operator fun set(key: KEY, value: Any?) {
        value?.let { value ->
            when (value) {
                is String -> preference.edit().putString(key.key, value).commit()
                is Int -> preference.edit().putInt(key.key, value).commit()
                is Boolean -> preference.edit().putBoolean(key.key, value).commit()
                is Float -> preference.edit().putFloat(key.key, value).commit()
                is Long -> preference.edit().putLong(key.key, value).commit()
                else -> throw UnsupportedOperationException("Not yet implemented")
            }
        } ?: kotlin.run {
            preference.edit().remove(key.key).commit()
        }
    }

    private inline operator fun <reified T : Any> get(key: KEY, defaultValue: T? = null): T? {
        return when (T::class) {
            String::class -> preference.getString(key.key, defaultValue as? String) as T?
            Int::class -> preference.getInt(key.key, defaultValue as? Int ?: -1) as T?
            Boolean::class -> preference.getBoolean(
                key.key,
                defaultValue as? Boolean ?: false
            ) as T?
            Float::class -> preference.getFloat(key.key, defaultValue as? Float ?: -1f) as T?
            Long::class -> preference.getLong(key.key, defaultValue as? Long ?: -1) as T?
            else -> throw UnsupportedOperationException("Not yet implemented")
        }
    }


    var curUser: String?
        get() = get(KEY.KEY_USER)
        @SuppressLint("ApplySharedPref")
        set(value) {
            if (value != null) {
                set(KEY.KEY_USER, value)
            } else {
                preference.edit().remove(KEY.KEY_USER.key).commit()
            }
        }


    var curFCMToken: String
        get() = get(KEY.KEY_FCM_TOKEN) ?: ""
        set(value) = set(KEY.KEY_FCM_TOKEN, value)

    var curDeviceUUID: String
        get() = get(KEY.KEY_DEVICE_UUID) ?: ""
        set(value) = set(KEY.KEY_DEVICE_UUID, value)

    var isNeedPushAlarmConfirm: Boolean
        get() = get(KEY.KEY_NEED_SHOW_PUSH_ALARM_CONFIRM) ?: false
        set(value) = set(KEY.KEY_NEED_SHOW_PUSH_ALARM_CONFIRM, value)

    var latestSWVersion: String
        get() = get(KEY.KEY_LATEST_SW_VERSION) ?: ""
        set(value) = set(KEY.KEY_LATEST_SW_VERSION, value)

    var isNeedShowAccessRight: Boolean
        get() = get(KEY.KEY_SHOW_APP_ACCESS_RIGHT, true) ?: true
        set(value) = set(KEY.KEY_SHOW_APP_ACCESS_RIGHT, value)

    var latestRetrievedLegalNoticeVersion: String
        get() = get(KEY.KEY_ACCEPTED_TERMS_VERSION) ?: ""
        set(value) = set(KEY.KEY_ACCEPTED_TERMS_VERSION, value)

    var curServer: Int
        get() = get(KEY.KEY_SERVER) ?: ServerType.defaultServerType.value
        set(value) = set(KEY.KEY_SERVER, value)

    var popUpDate: String
        get() = get(KEY.KEY_POP_UP_DATE) ?: "2020-01-01"
        set(value) = set(KEY.KEY_POP_UP_DATE, value)

    val info = context.packageManager.getPackageInfo(context.packageName, PackageManager.GET_ACTIVITIES)


}