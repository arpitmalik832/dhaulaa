package com.dhaula.dhaulaa.resource.service

import RouteConstants
import com.dhaula.dhaulaa.model.MUser
import com.dhaula.dhaulaa.resource.request.Request
import com.dhaula.dhaulaa.resource.response.Response
import com.dhaula.dhaulaa.web.ApiService
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

class ServiceVerify :
    ApiService<ServiceVerify.Api, Request<HashMap<String, Any>>, Response<MUser>>() {

    override val aPI: Class<Api>?
        get() = Api::class.java

    interface Api {
        @POST(RouteConstants.URL_VERIFY_OTP)
        fun api(@Body request: HashMap<String, Any>): Call<Response<MUser>?>?
    }

    override fun onExecute(
        api: Api,
        request: Request<HashMap<String, Any>>
    ): Call<Response<MUser>?>? {
        return api.api(request.request)
    }

}