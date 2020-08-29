package com.dhaula.dhaulaa.web

import com.dhaula.dhaulaa.utils.AppConstant


/**
 * [ApiRequest]
 */
abstract class ApiRequest {
    val isValid: Boolean
        get() = true

    val baseUrl: String
        get() = AppConstant.BASE_URL

}