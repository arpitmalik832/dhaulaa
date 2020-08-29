package com.dhaula.dhaulaa.resource.response

import com.dhaula.dhaulaa.web.ApiResponse

class Response<R>() : ApiResponse() {
    var data: R? = null
    var date: R? = null
    var banner: String? = null
    var detail: MOrderDetail? = null

    data class MOrderDetail (
        val order_id: String?=null,
        val shipping_address:String?=null,
        val order_date:String?=null,
        var order_type:String?=null,
        var status:String?=null,
        var grand_price:String?=null
    )

}