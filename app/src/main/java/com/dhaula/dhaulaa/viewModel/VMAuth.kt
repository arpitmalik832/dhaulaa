package com.dhaula.dhaulaa.viewModel

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.dhaula.dhaulaa.model.MOtp
import com.dhaula.dhaulaa.model.MSuccess
import com.dhaula.dhaulaa.model.MUser
import com.dhaula.dhaulaa.resource.request.Request
import com.dhaula.dhaulaa.resource.response.Response
import com.dhaula.dhaulaa.resource.service.ServiceAddAddress
import com.dhaula.dhaulaa.resource.service.ServiceLogin
import com.dhaula.dhaulaa.resource.service.ServiceRegisterUser
import com.dhaula.dhaulaa.resource.service.ServiceVerify
import com.dhaula.dhaulaa.utils.*
import com.dhaula.dhaulaa.web.ApiCallback
import com.dhaula.vegitable.base.BaseViewModel
import com.dhaula.vegitable.navigator.NavigatorBase
import com.google.gson.Gson
import com.paytag.web.ApiException
import retrofit2.Call

class VMAuth : BaseViewModel<NavigatorBase>() {
    var phoneNumber = MutableLiveData<String>()
    var otp = MutableLiveData<String>()
    var serverData = MutableLiveData<MUser>()
    var loginProgressText = MutableLiveData<String>("Request Otp")
    var otpProgressText = MutableLiveData<String>("Login")
    var registerProgressText = MutableLiveData<String>("Register")

    fun sendOTP() {
        if (!UIUtils.isValidMobile(phoneNumber.value)) {
            navigator?.showMessage("Enter an valid Mobile Number", 0)
            return
        }
        loginProgressText.value = ""
        ServiceLogin().execute(
            Request(RequestUtil.generateOtp(phoneNumber.value?:"")),
            object : ApiCallback<Response<MUser>?>() {
                override fun onSuccess(call: Call<Response<MUser>?>?, response: Response<MUser>?) {
                    if (response?.statusCode == Status.SUCCESS) {
                        navigator?.onAction(Status.SUCCESS, response.data)
                    }
                    navigator?.showMessage(response?.message, 0)
                }

                override fun onComplete() {
                    loginProgressText.value = "Request Otp"
                }

                override fun onFailure(e: ApiException?) {
                    navigator?.showMessage(e?.localizedMessage, 0)
                }
            })
    }

    fun verifyOTP() {
        otpProgressText.value = ""
        ServiceVerify().execute(
            Request(RequestUtil.verifyOtp(phoneNumber.value!!,otp.value!!)),
            object : ApiCallback<Response<MUser>?>() {
                override fun onSuccess(call: Call<Response<MUser>?>?, response: Response<MUser>?) {
                    if(response?.statusCode == Status.SUCCESS) {

                        serverData.value = response.date

                        serverData.value = response.date?:MUser()
                        Action.saveUser(serverData.value?:MUser())
//                        PreferencesUtils.putString(AppConstant.USER_DATA, Gson().toJson(serverData.value))
                        PreferencesUtils.putString(AppConstant.USER_ID, serverData.value?.id?:"")
                        PreferencesUtils.putBoolean(AppConstant.IS_LOGIN, true)
                        navigator?.onAction(Status.SUCCESS, serverData.value)
                        navigator?.showMessage("Verified Successfully",0)
                    } else {
                        navigator?.showMessage("Enter an valid 6 digits OTP", 0)
                    }
                }

                override fun onComplete() {
                    otpProgressText.value = "Verify"
                }

                override fun onFailure(e: ApiException?) {
                    navigator?.showMessage(e?.localizedMessage,0)
                }
            }
        )
    }

    fun registerUser() {
        if(!validateUser()) return
        navigator?.showLoading()
        ServiceRegisterUser().execute(Request(RequestUtil.registerUser(
                name.value!!,
                address.value!!,
                mobile.value!!,
                city.value!!,
                pinCode.value!!,
                email.value!!

        )),
        object : ApiCallback<Response<MSuccess>?>() {
            override fun onSuccess(call: Call<Response<MSuccess>?>?, response: Response<MSuccess>?) {
                if(response?.statusCode == Status.SUCCESS) {
                    navigator?.onAction(Status.SUCCESS, serverData.value)
                    navigator?.showMessage("User Registered Successfully",0)
                } else {
                    navigator?.showMessage("Please Try Again",0)
                }
            }

            override fun onComplete() {
                navigator?.hideLoading()
            }

            override fun onFailure(e: ApiException?) {
                navigator?.showMessage(e?.localizedMessage,0)
            }

        })
    }
    //Add Address Fields
    var name = MutableLiveData<String>()
    var city = MutableLiveData<String>()
    var address = MutableLiveData<String>()
    var pinCode = MutableLiveData<String>()
    var mobile = MutableLiveData<String>()
    var email = MutableLiveData<String>()
    var user = MutableLiveData<MUser>()

    fun addUser() {
        if(!validateUser()) return
        navigator?.showLoading()
        ServiceAddAddress().execute(Request(RequestUtil.getAddUser(user.value ?: MUser())),
            object : ApiCallback<Response<String>?>() {
                override fun onSuccess(
                    call: Call<Response<String>?>?,
                    response: Response<String>?
                ) {
                    if (response?.statusCode == Status.SUCCESS) {
                        navigator?.onAction(200, null)
                    }
                    navigator?.showMessage(response?.message, 0)
                }

                override fun onComplete() {
                    navigator?.hideLoading()
                }

                override fun onFailure(e: ApiException?) {
                    navigator?.showMessage(e?.localizedMessage, 0)
                }

            })
    }

    private fun validateUser(): Boolean {
        user.value = MUser()
        if (TextUtils.isEmpty(name.value)) {
            navigator?.showMessage("Enter an name", 0)
            return false
        }
        if (TextUtils.isEmpty(address.value)) {
            navigator?.showMessage("Enter an address", 0)
            return false
        }
        if (TextUtils.isEmpty(mobile.value)) {
            navigator?.showMessage("Enter an Mobile Number", 0)
            return false
        }
        if (TextUtils.isEmpty(city.value)) {
            navigator?.showMessage("Enter an city", 0)
            return false
        }
        if (TextUtils.isEmpty(pinCode.value)) {
            navigator?.showMessage("Enter an pincode", 0)
            return false
        }
        user.value = MUser(
           "",
            address.value,
            city.value,
            email.value,
            mobile.value,
            name.value,
            false,
            pinCode.value
        )
        return true
    }


    fun resendOTP() {
        if (!UIUtils.isValidMobile(phoneNumber.value)) {
            navigator?.showMessage("Enter an valid Mobile Number", 0)
            return
        }
        otpProgressText.value = ""
        ServiceLogin().execute(
        Request(RequestUtil.generateOtp(phoneNumber.value!!)),
        object : ApiCallback<Response<MUser>?>() {
            override fun onSuccess(call: Call<Response<MUser>?>?, response: Response<MUser>?) {
                navigator?.showMessage(response?.message, 0)
            }

            override fun onComplete() {
                otpProgressText.value = "Login"
            }

            override fun onFailure(e: ApiException?) {
                navigator?.showMessage(e?.localizedMessage, 0)
            }
        })
    }


}