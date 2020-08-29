package com.dhaula.dhaulaa.resource.service

import com.dhaula.dhaulaa.model.MOrder
import com.dhaula.dhaulaa.resource.request.Request
import com.dhaula.dhaulaa.resource.response.Response
import com.dhaula.dhaulaa.web.ApiService
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

class ServiceOrders : ApiService<ServiceOrders.Api, Request<HashMap<String, Any>>, Response<ArrayList<MOrder>>>() {

    override val aPI: Class<Api>?
        get() = Api::class.java

    interface Api{
        @POST(RouteConstants.URL_FETCH_ORDERS)
        fun api(@Body request: HashMap<String,Any>): Call<Response<ArrayList<MOrder>>?>?
    }

    override fun onExecute(
            api: Api,
            request: Request<HashMap<String, Any>>
    ): Call<Response<ArrayList<MOrder>>?>? {
        return api.api(request.request)
    }

}