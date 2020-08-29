package com.dhaula.dhaulaa.model

import com.google.gson.annotations.SerializedName

data class MCategory (
    var id:Int?=null,
    @SerializedName("category")
    var name:String?=null,
    var qty:String?=null,
    var image:String?=null
)