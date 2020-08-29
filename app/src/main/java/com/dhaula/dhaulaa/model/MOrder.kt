package com.dhaula.dhaulaa.model

data class MOrder (
        var id:String?=null,
        var grand_price:String?=null,
        var order_date:String?=null,
        val order_id:String?=null,
        var status:String?="0",
        var order_type:String?=null,
        var shipping_address:String?=null
)