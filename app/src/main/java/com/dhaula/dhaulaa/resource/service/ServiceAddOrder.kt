package com.dhaula.dhaulaa.resource.service

import com.dhaula.dhaulaa.model.MAddress
import com.dhaula.dhaulaa.model.MSuccess
import com.dhaula.dhaulaa.resource.request.Request
import com.dhaula.dhaulaa.resource.response.Response
import com.dhaula.dhaulaa.web.ApiService
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

class ServiceAddOrder : ApiService<ServiceAddOrder.Api, Request<HashMap<String, Any>>, Response<MSuccess>>() {

    override val aPI: Class<Api>?
        get() = Api::class.java

    interface Api{
        @POST(RouteConstants.URL_ADD_ORDER)
        fun api(@Body request: HashMap<String,Any>): Call<Response<MSuccess>?>?
    }

    override fun onExecute(
            api: Api,
            request: Request<HashMap<String, Any>>
    ): Call<Response<MSuccess>?>? {
        return api.api(request.request)
    }

}