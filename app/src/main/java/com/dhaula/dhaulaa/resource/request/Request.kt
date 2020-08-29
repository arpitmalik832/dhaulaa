package com.dhaula.dhaulaa.resource.request

import com.dhaula.dhaulaa.web.ApiRequest

class Request<U>(temp: U) : ApiRequest() {
    var request:U = temp
}