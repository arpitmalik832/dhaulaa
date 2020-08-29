package com.dhaula.dhaulaa.web

import android.os.Parcel
import com.google.gson.annotations.SerializedName

/**
 * [ApiResponse]
 */
open class ApiResponse {
    @SerializedName("msg")
    val message: String? = null

    @SerializedName("statusCode")
    val statusCode = 0
    val actionCode = 0
    val reason:String?=null

    val isValid: Boolean
        get() = true

    protected fun writeMap(
        out: Parcel,
        map: Map<String?, String?>?
    ) {
        if (map == null) {
            return
        }
        out.writeInt(map.size)
        for ((key, value) in map) {
            out.writeString(key)
            out.writeString(value)
        }
    }

    protected fun readMap(
        `in`: Parcel,
        map: MutableMap<String?, String?>
    ) {
        val size = `in`.readInt()
        for (i in 0 until size) {
            val key = `in`.readString()
            val value = `in`.readString()
            map[key] = value
        }
    }

}