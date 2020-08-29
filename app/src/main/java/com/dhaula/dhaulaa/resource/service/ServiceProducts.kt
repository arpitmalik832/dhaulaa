package com.dhaula.dhaulaa.resource.service

import com.dhaula.dhaulaa.model.MProduct
import com.dhaula.dhaulaa.resource.request.Request
import com.dhaula.dhaulaa.resource.response.Response
import com.dhaula.dhaulaa.web.ApiService
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

class ServiceProducts : ApiService<ServiceProducts.Api, Request<HashMap<String, Any>>, Response<ArrayList<MProduct>>>() {

    override val aPI: Class<Api>?
        get() = Api::class.java

    interface Api{
        @POST(RouteConstants.URL_PRODUCT_LIST)
        fun api(@Body request: HashMap<String,Any>): Call<Response<ArrayList<MProduct>>?>?
    }

    override fun onExecute(
        api: Api,
        request: Request<HashMap<String, Any>>
    ): Call<Response<ArrayList<MProduct>>?>? {
        return api.api(request.request)
    }

}