package com.dhaula.dhaulaa.resource.service

import com.dhaula.dhaulaa.model.MAddress
import com.dhaula.dhaulaa.resource.request.Request
import com.dhaula.dhaulaa.resource.response.Response
import com.dhaula.dhaulaa.web.ApiService
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

class ServiceAddress : ApiService<ServiceAddress.Api, Request<HashMap<String, Any>>, Response<ArrayList<MAddress>>>() {

    override val aPI: Class<Api>?
        get() = Api::class.java

    interface Api{
        @POST(RouteConstants.URL_ADDRESS)
        fun api(@Body request: HashMap<String,Any>): Call<Response<ArrayList<MAddress>>?>?
    }

    override fun onExecute(
        api: Api,
        request: Request<HashMap<String, Any>>
    ): Call<Response<ArrayList<MAddress>>?>? {
        return api.api(request.request)
    }

}