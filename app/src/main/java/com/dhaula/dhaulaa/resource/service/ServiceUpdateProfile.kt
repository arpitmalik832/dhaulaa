package com.dhaula.dhaulaa.resource.service

import com.dhaula.dhaulaa.model.MNews
import com.dhaula.dhaulaa.resource.request.Request
import com.dhaula.dhaulaa.resource.response.Response
import com.dhaula.dhaulaa.web.ApiService
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

class ServiceUpdateProfile : ApiService<ServiceUpdateProfile.Api, Request<HashMap<String, Any>>, Response<String>>() {

    override val aPI: Class<Api>?
        get() = Api::class.java

    interface Api{
        @POST(RouteConstants.URL_UPDATE_PROFILE)
        fun api(@Body request: HashMap<String,Any>): Call<Response<String>?>?
    }

    override fun onExecute(
        api: Api,
        request: Request<HashMap<String, Any>>
    ): Call<Response<String>?>? {
        return api.api(request.request)
    }

}