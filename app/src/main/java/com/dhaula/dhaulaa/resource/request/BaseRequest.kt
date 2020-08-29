package com.dhaula.dhaulaa.resource.request

import com.dhaula.dhaulaa.web.ApiRequest

class BaseRequest : ApiRequest() {
    var firstParams: String? = null
    var firstIntParams: Int? = 0

    var secondParams: String? = null
    var secondIntParams: Int? = 0

    var thirdParams: String? = null
    var thirdIntParams: Int? = 0

}