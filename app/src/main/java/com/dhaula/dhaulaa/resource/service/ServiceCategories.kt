package com.dhaula.dhaulaa.resource.service

import com.dhaula.dhaulaa.model.MCategory
import com.dhaula.dhaulaa.resource.request.Request
import com.dhaula.dhaulaa.resource.response.Response
import com.dhaula.dhaulaa.web.ApiService
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

class ServiceCategories  : ApiService<ServiceCategories.Api, Request<HashMap<String, Any>>, Response<ArrayList<MCategory>>>() {

    override val aPI: Class<Api>?
        get() = Api::class.java

    interface Api{
        @POST(RouteConstants.URL_CATEGORIES)
        fun api(@Body request: HashMap<String,Any>): Call<Response<ArrayList<MCategory>>?>?
    }

    override fun onExecute(
        api: Api,
        request: Request<HashMap<String, Any>>
    ): Call<Response<ArrayList<MCategory>>?>? {
        return api.api(request.request)
    }

}