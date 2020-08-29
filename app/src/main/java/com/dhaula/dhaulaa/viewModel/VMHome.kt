package com.dhaula.dhaulaa.viewModel

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.dhaula.dhaulaa.R
import com.dhaula.dhaulaa.model.*
import com.dhaula.dhaulaa.resource.request.Request
import com.dhaula.dhaulaa.resource.response.Response
import com.dhaula.dhaulaa.resource.service.*
import com.dhaula.dhaulaa.ui.fragment.FragmentMyOrder
import com.dhaula.dhaulaa.ui.fragment.FragmentPayment
import com.dhaula.dhaulaa.utils.Action
import com.dhaula.dhaulaa.utils.RequestUtil
import com.dhaula.dhaulaa.utils.Status
import com.dhaula.dhaulaa.utils.UIUtils
import com.dhaula.dhaulaa.web.ApiCallback
import com.dhaula.vegitable.base.BaseViewModel
import com.dhaula.vegitable.navigator.NavigatorBase
import com.paytag.web.ApiException
import retrofit2.Call

class VMHome : BaseViewModel<NavigatorBase>() {
    var categoryList = MutableLiveData<ArrayList<MCategory>>()
    var categoryDetail = MutableLiveData<MCategory>()
    var banner = MutableLiveData<String>()
    var basketList = MutableLiveData<ArrayList<MBasketItem>>()
    var newsList = MutableLiveData<ArrayList<MNews>>()
    var productList = MutableLiveData<ArrayList<MProduct>>()
    var addressList = MutableLiveData<ArrayList<MAddress>>()
    var productDetail = MutableLiveData<MProduct>()
    var newsDetail = MutableLiveData<MNews>()
    var updateUser = MutableLiveData<MUser>()
    var addAddress = MutableLiveData<MUser>()
    var orderList = MutableLiveData<ArrayList<MOrder>>()
    var packagesList = MutableLiveData<ArrayList<MProduct>>()
    var orderItemsList = MutableLiveData<ArrayList<MBasketItem>>()
    var orderDetail = MutableLiveData<Response.MOrderDetail>()


    fun getNews() {
        navigator?.showLoading()
        ServiceNews().execute(Request(RequestUtil.getNewsReq()),
            object : ApiCallback<Response<ArrayList<MNews>>?>() {
                override fun onSuccess(
                    call: Call<Response<ArrayList<MNews>>?>?,
                    response: Response<ArrayList<MNews>>?
                ) {
                    if (response?.statusCode == Status.SUCCESS) {
                        newsList.value = response.data
                    }
                }

                override fun onComplete() {
                    newsList.value = ArrayList()
                    navigator?.hideLoading()
                }

                override fun onFailure(e: ApiException?) {

                }

            })
    }


    fun getCategories() {
        navigator?.showLoading()
        ServiceCategories().execute(Request(RequestUtil.getNewsReq()),
            object : ApiCallback<Response<ArrayList<MCategory>>?>() {
                override fun onSuccess(
                    call: Call<Response<ArrayList<MCategory>>?>?,
                    response: Response<ArrayList<MCategory>>?
                ) {
                    if (response?.statusCode == Status.SUCCESS) {
                        banner.value = response.banner ?: ""
                        categoryList.value = response.data
                    }
                }

                override fun onComplete() {
                    categoryList.value = ArrayList()
                    navigator?.hideLoading()
                }

                override fun onFailure(e: ApiException?) {
                }

            })
    }

    fun addToBasket(userId:String,productId:String,price:String,quantity:String) {
        ServiceAddToBasket().execute(Request(RequestUtil.addToBasket(userId,productId,price,quantity)),
        object : ApiCallback<Response<MSuccess>?>() {
            override fun onSuccess(call: Call<Response<MSuccess>?>?, response: Response<MSuccess>?) {
                if(response?.statusCode == Status.SUCCESS) {
                    banner.value = response.banner?:""
                    navigator?.showMessage("Added To Basket",0)
                }
            }

            override fun onComplete() {
            }

            override fun onFailure(e: ApiException?) {
            }

        })
    }

    fun removeFromBasket(userId:String,cartId:String) {
        navigator?.showLoading()
        ServiceRemoveFromBasket().execute(Request(RequestUtil.removeFromBasket(userId,cartId)),
        object: ApiCallback<Response<MSuccess>?>() {
            override fun onSuccess(call: Call<Response<MSuccess>?>?, response: Response<MSuccess>?) {
                if(response?.statusCode == Status.SUCCESS) {
                    banner.value = response.banner?:""
                }
            }

            override fun onComplete() {
                navigator?.hideLoading()
            }

            override fun onFailure(e: ApiException?) {
            }

        })
    }

    fun addOrder(userId:String,grandTotal:String,orderType:String,shippingAddress:String,fragment:FragmentPayment) {
        navigator?.showLoading()
        ServiceAddOrder().execute(
                Request(RequestUtil.addOrder(userId,grandTotal,orderType,shippingAddress)),
                object : ApiCallback<Response<MSuccess>?>() {
                    override fun onSuccess(call: Call<Response<MSuccess>?>?, response: Response<MSuccess>?) {
                        if(response?.statusCode == Status.SUCCESS) {
                            banner.value = response.banner?:""
                            navigator?.showMessage("Order Placed", 0)
                        }
                    }

                    override fun onComplete() {
                        navigator!!.hideLoading()
                        fragment.replaceFragment(FragmentMyOrder(), R.id.fl_home, false)
                    }

                    override fun onFailure(e: ApiException?) {
                    }

                }
        )
    }

    fun fetchOrders() {
        navigator?.showLoading()
        ServiceOrders().execute(
                Request(RequestUtil.fetchOrders()),
                object : ApiCallback<Response<ArrayList<MOrder>>?>() {
                    override fun onSuccess(call: Call<Response<ArrayList<MOrder>>?>?, response: Response<ArrayList<MOrder>>?) {
                        if(response?.statusCode == Status.SUCCESS) {
                            banner.value = response.banner?:""
                            orderList.value = response.data
                        }
                    }

                    override fun onComplete() {
                        navigator!!.hideLoading()
                        navigator!!.hideLoading()
                        navigator!!.hideLoading()
                    }

                    override fun onFailure(e: ApiException?) {
                    }

                }
        )
    }

    fun fetchOrderDetail(orderId:String) {
        navigator?.showLoading()
        ServiceFetchOrder().execute(
                Request(RequestUtil.fetchOrderDetails(orderId)),
                object : ApiCallback<Response<ArrayList<MBasketItem>>?>() {
                    override fun onSuccess(call: Call<Response<ArrayList<MBasketItem>>?>?, response: Response<ArrayList<MBasketItem>>?) {
                        if(response?.statusCode == Status.SUCCESS) {
                            banner.value = response.banner?:""
                            orderItemsList.value = response.data
                            orderDetail.value = response.detail
                        }
                    }

                    override fun onComplete() {
                        navigator?.hideLoading()
                    }

                    override fun onFailure(e: ApiException?) {
                    }

                }
        )
    }

    fun getBasket(userId:String) {
        navigator?.showLoading()
        ServiceBasket().execute(Request(RequestUtil.getBasketReq(userId)),
            object: ApiCallback<Response<ArrayList<MBasketItem>>?>() {
                override fun onSuccess(call: Call<Response<ArrayList<MBasketItem>>?>?, response: Response<ArrayList<MBasketItem>>?) {
                    if(response?.statusCode == Status.SUCCESS) {
                        banner.value = response.banner ?: ""
                        basketList.value = response.data
                    }
                }

                override fun onComplete() {
                    basketList.value = ArrayList()
                    navigator?.hideLoading()
                }

                override fun onFailure(e: ApiException?) {
                }

            })
    }

    fun getProducts() {
        navigator?.showLoading()
        val id = categoryDetail.value?.id?: 0
        ServiceProducts().execute(Request(RequestUtil.getCategoryReq(id.toString())),
            object : ApiCallback<Response<ArrayList<MProduct>>?>() {
                override fun onSuccess(
                    call: Call<Response<ArrayList<MProduct>>?>?,
                    response: Response<ArrayList<MProduct>>?
                ) {
                    if (response?.statusCode == Status.SUCCESS) {
                        productList.value = response.data
                    }
                }

                override fun onComplete() {
                    productList.value = ArrayList()
                    navigator?.hideLoading()
                }

                override fun onFailure(e: ApiException?) {

                }

            })
    }

    fun getAddress() {
        navigator?.showLoading()
        ServiceAddress().execute(Request(RequestUtil.getAddress()),
            object : ApiCallback<Response<ArrayList<MAddress>>?>() {
                override fun onSuccess(
                    call: Call<Response<ArrayList<MAddress>>?>?,
                    response: Response<ArrayList<MAddress>>?
                ) {
                    if (response?.statusCode == Status.SUCCESS) {
                        addressList.value = response.data
                    }
                }

                override fun onComplete() {
                    addressList.value = ArrayList()
                    navigator?.hideLoading()
                }

                override fun onFailure(e: ApiException?) {
                }

            })
    }

    fun updateProfile() {
        navigator?.showLoading()
        ServiceUpdateProfile().execute(Request(
            RequestUtil.getUpdatedUser(
                updateUser.value ?: MUser()
            )
        ),
            object : ApiCallback<Response<String>?>() {
                override fun onSuccess(
                    call: Call<Response<String>?>?,
                    response: Response<String>?
                ) {
                    if (response?.statusCode == Status.SUCCESS) {

                    }
                    navigator?.showMessage(response?.message, 0)
                }

                override fun onComplete() {
                    updateUser.value = MUser()
                    navigator?.hideLoading()
                }

                override fun onFailure(e: ApiException?) {
                    navigator?.showMessage(e?.localizedMessage, 0)
                }

            })
    }


    //Add Address Fields
    var name = MutableLiveData<String>()
    var city = MutableLiveData<String>()
    var maddress = MutableLiveData<String>()
    var pinCode = MutableLiveData<String>()
    var mobile = MutableLiveData<String>()

    fun saveAddress() {
        if(!validateAddress()){
            return
        }
        navigator?.showLoading()
        ServiceAddAddress().execute(Request(RequestUtil.getAddAddress(addAddress.value ?: MUser())),
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

    private fun validateAddress(): Boolean {
        addAddress.value = MUser()
        if (TextUtils.isEmpty(name.value)) {
            navigator?.showMessage("Enter an name", 0)
            return false
        }
        if (TextUtils.isEmpty(mobile.value)) {
            navigator?.showMessage("Enter a mobile number", 0)
            return false
        }
        if (TextUtils.isEmpty(maddress.value)) {
            navigator?.showMessage("Enter an address", 0)
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
        addAddress.value = MUser(
            Action.getUserId(),
            maddress.value,
            city.value,
            "",
            mobile.value,
            name.value,
            false,
            pinCode.value
        )
        return true
    }




    var pName = MutableLiveData<String>()
    var pReference = MutableLiveData<String>()
    var pAddress = MutableLiveData<String>()
    var pMobile = MutableLiveData<String>()

    private fun validatePartner(): Boolean {
        if (TextUtils.isEmpty(pName.value)) {
            navigator?.showMessage("Enter an valid name", 0)
            return false
        }
        if (!UIUtils.isValidMobile(pMobile.value)) {
            navigator?.showMessage("Enter an valid mobile Number", 0)
            return false
        }
        if (TextUtils.isEmpty(pAddress.value)) {
            navigator?.showMessage("Enter an valid Address", 0)
            return false
        }
        if (!TextUtils.isEmpty(pReference.value) && !Action.isEmailValid(pReference.value)) {
            navigator?.showMessage("Enter an valid Email", 0)
            return false
        }
        return true

    }


    fun becomePartner(){
        if(!validatePartner()) return
        navigator?.showLoading()
        ServiceAddAddress().execute(Request(RequestUtil.getPartner(
            pName.value,
            pMobile.value,
            pReference.value,
            pAddress.value
        )),
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

    fun addRating(map: HashMap<String,Any>) {

        ServiceAddRating().execute(Request(map),
            object : ApiCallback<Response<Any>?>() {
                override fun onSuccess(
                    call: Call<Response<Any>?>?,
                    response: Response<Any>?
                ) {
                    navigator?.showMessage(response?.message, 0)
                }

                override fun onComplete() {
                }

                override fun onFailure(e: ApiException?) {
                    navigator?.showMessage(e?.localizedMessage, 0)
                }

            })
    }

    fun cancelOrder(bean: MOrder) {
        navigator?.showLoading()
        val map = HashMap<String,Any>()
        map["user_id"] = Action.getUserId()
        map["order_id"] = bean.order_id?:"0"
        map["id"] = bean.id?:"0"
        ServiceCancelOrder().execute(Request(map),
            object : ApiCallback<Response<Any>?>() {
                override fun onSuccess(
                    call: Call<Response<Any>?>?,
                    response: Response<Any>?
                ) {
                    if (response?.statusCode == Status.SUCCESS) {
                        navigator?.onAction(200, null)
                    }
                    fetchOrders()
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


}