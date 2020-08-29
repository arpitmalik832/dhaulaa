package com.dhaula.dhaulaa.utils

import android.os.Build
import android.text.Html
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import com.dhaula.dhaulaa.model.MAddress
import com.dhaula.dhaulaa.model.MUser
import com.google.gson.Gson
import java.util.regex.Pattern


object Action {
    const val  FLAG_HTML = 25
    fun isLogin():Boolean{
        return PreferencesUtils.getBoolean(AppConstant.IS_LOGIN)?:false
    }

    fun visible(view: View?,boolean: Boolean){
        view?.let {
            it.visibility  = if(boolean) {View.VISIBLE}else{View.GONE}
        }
    }

    fun getUserId():String{
        return PreferencesUtils.getString(AppConstant.USER_ID)?:""
    }

    fun getHtml(value:String?): Spanned? {
        if(TextUtils.isEmpty(value)){ return SpannableString("")}
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(value, FLAG_HTML)
        }else{
            Html.fromHtml(value)
        }
    }

    fun saveUser(data:MUser?){
        data?.let {
            PreferencesUtils.putString(AppConstant.USER_DATA,Gson().toJson(data))
        }
    }

    fun getUser():MUser {
        val data = PreferencesUtils.getString(AppConstant.USER_DATA)
        try {
           return Gson().fromJson<MUser>(data, MUser::class.java)?:MUser()
        }catch (e:Exception){}
        return MUser()
    }

    fun saveAddress(address:MAddress?) {
        if(address==null) return
        PreferencesUtils.putString("Address",Gson().toJson(address))
    }

    fun getAddress():MAddress?{
        return Gson().fromJson<MAddress>(PreferencesUtils.getString("Address")?:"",MAddress::class.java)?:MAddress()
    }

    fun getAddressId():String{
        return getAddress()?.id?:"0"
    }

    fun getFancyAddress():String{
        val address = getAddress()
        return "${address?.address?:""}, ${address?.city?:""} -  ${address?.pincode?:""}"
    }

    fun isEmailValid(email: String?): Boolean {
        if(TextUtils.isEmpty(email)) return false
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }
}