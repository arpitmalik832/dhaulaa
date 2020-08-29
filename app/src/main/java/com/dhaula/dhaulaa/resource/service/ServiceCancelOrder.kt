package com.dhaula.dhaulaa.resource.service

import com.dhaula.dhaulaa.resource.request.Request
import com.dhaula.dhaulaa.resource.response.Response
import com.dhaula.dhaulaa.web.ApiService
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST




class ServiceCancelOrder : ApiService<ServiceCancelOrder.Api, Request<HashMap<String, Any>>, Response<Any>>() {

    override val aPI: Class<Api>?
        get() = Api::class.java

    interface Api{
        @POST(RouteConstants.URL_CANCEL_ORDER)
        fun api(@Body request: HashMap<String,Any>): Call<Response<Any>?>?
    }

    override fun onExecute(
        api: Api,
        request: Request<HashMap<String, Any>>
    ): Call<Response<Any>?>? {
        return api.api(request.request)
    }

}