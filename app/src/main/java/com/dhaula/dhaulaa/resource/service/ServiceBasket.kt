package com.dhaula.dhaulaa.resource.service

import com.dhaula.dhaulaa.model.MBasketItem
import com.dhaula.dhaulaa.resource.request.Request
import com.dhaula.dhaulaa.resource.response.Response
import com.dhaula.dhaulaa.web.ApiService
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

class ServiceBasket  : ApiService<ServiceBasket.Api,Request<HashMap<String, Any>>,Response<ArrayList<MBasketItem>>>() {

    override val aPI: Class<Api>?
        get() = Api::class.java

    interface Api{
        @POST(RouteConstants.URL_BASKET_LIST)
        fun api(@Body request: HashMap<String, Any>): Call<Response<ArrayList<MBasketItem>>?>?
    }

    override fun onExecute(api: Api, request: Request<HashMap<String, Any>>): Call<Response<ArrayList<MBasketItem>>?>? {
        return api.api(request.request)
    }

}