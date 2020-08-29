package com.dhaula.dhaulaa.resource.service

import RouteConstants
import com.dhaula.dhaulaa.resource.request.Request
import com.dhaula.dhaulaa.resource.response.BaseRes
import com.dhaula.dhaulaa.resource.response.Response
import com.dhaula.dhaulaa.web.ApiService
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

class ServicePartner :
    ApiService<ServicePartner.Api, Request<HashMap<String, Any>>, Response<BaseRes>>() {

    override val aPI: Class<Api>?
        get() = Api::class.java

    interface Api {
        @POST(RouteConstants.URL_BECOME_PARTNER)
        fun api(@Body request: HashMap<String, Any>): Call<Response<BaseRes>?>?
    }

    override fun onExecute(
        api: Api,
        request: Request<HashMap<String, Any>>
    ): Call<Response<BaseRes>?>? {
        return api.api(request.request)
    }

}