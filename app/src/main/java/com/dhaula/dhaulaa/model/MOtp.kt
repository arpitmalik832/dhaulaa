package com.dhaula.dhaulaa.model


data class MOtp (
        var statusCode:Int?=null,
        var msg: String? = null,
        var reason: String? = null,
        var actionCode:Int?=null,
        var data: MUser? = null
)