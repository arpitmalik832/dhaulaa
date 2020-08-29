package com.dhaula.dhaulaa.model

import android.text.Spanned
import com.dhaula.dhaulaa.utils.Action.getHtml
import com.google.gson.annotations.SerializedName

data class MProduct (
    var id:Int?=null,
    var name:String?=null,
    var qty:String?=null,
    var price:Int?=null,
    @SerializedName("offer_price")
    var offerPrice:Int?=null,
    var discount:Int?=null,
    var image:String?=null,
    var description:String?=null,
    var in_stock:Int?=null
){
    fun getDesc():Spanned?{
        return getHtml(description)
    }

}