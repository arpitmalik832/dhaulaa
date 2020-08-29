package com.dhaula.dhaulaa.utils

import com.dhaula.dhaulaa.model.MUser

object RequestUtil {
    fun generateOtp(mobileNumber:String?):HashMap<String,Any>{
        val request = HashMap<String,Any>()
        request["mobile"] = mobileNumber?:""
        return request
    }

    fun getNewsReq(): java.util.HashMap<String, Any> {
        val request = HashMap<String,Any>()
        request["id"] = 1
        return request
    }

    fun fetchOrders(): HashMap<String, Any> {
        val request = HashMap<String, Any>()
        request["user_id"] = Action.getUserId()
        return request
    }

    fun fetchOrderDetails(orderId:String): HashMap<String, Any> {
        val request = HashMap<String, Any>()
        request["order_id"] = orderId
        return request
    }

    fun addOrder(userId:String,grandTotal:String,orderType:String,shippingAddress:String): HashMap<String, Any> {
        val request = HashMap<String,Any>()
        request["user_id"] = userId
        request["grand_total"] = grandTotal
        request["order_type"] = orderType
        request["shipping_address"] = shippingAddress
        request["addressId"] = Action.getAddressId()
        return request
    }

    fun getCategoryReq(id:String):HashMap<String,Any>{
        val request = HashMap<String,Any>()
        request["category_id"] = id
        return request
    }

    fun verifyOtp(phoneNo:String,otp:String):HashMap<String,Any> {
        val request = HashMap<String,Any>()
        request["mobile"] = phoneNo
        request["otp"] = otp
        return request
    }

    fun addToBasket(userId:String,productId:String,price:String,quantity:String):HashMap<String,Any> {
        val request = HashMap<String,Any>()
        request["user_id"] = userId
        request["product_id"] = productId
        request["price"] = price
        request["quantity"] = quantity
        return request
    }

    fun removeFromBasket(userId:String,cartId:String):HashMap<String,Any> {
        val request = HashMap<String,Any>()
        request["user_id"] = userId
        request["cart_id"] = cartId
        return request
    }

    fun getBasketReq(userId:String):HashMap<String,Any> {
        val request = HashMap<String,Any>()
        request["user_id"] = userId
        return request
    }

    fun getAddress(): java.util.HashMap<String, Any> {
        val request = HashMap<String,Any>()
        request["user_id"] = Action.getUserId()
        return request
    }

    fun getUpdatedUser(data : MUser): java.util.HashMap<String, Any> {
        val request = HashMap<String,Any>()
        request["user_id"] = Action.getUserId()
        request["name"] = data.name?:""
        request["email"] = data.email?:""
        return request
    }

    fun getAddAddress(data: MUser): HashMap<String, Any> {
        //        {"user_id":"1","address":"delhi burari","name":"vikrant","number":"7844684684","city":"delhi","pincode":"110084"}
        val request = HashMap<String,Any>()
        request["user_id"] = Action.getUserId()
        request["name"] = data.name?:""
        request["address"] = data.address?:""
        request["number"] = data.mobile?:""
        request["city"] = data.city?:""
        request["pincode"] = data.pincode?:""
        return request
    }

    fun getAddUser(data: MUser): java.util.HashMap<String, Any> {
        val request = HashMap<String,Any>()
        request["user_id"] = 0
        request["name"] = data.name?:""
        request["address"] = data.address?:""
        request["number"] = data.mobile?:""
        request["city"] = data.city?:""
        request["pincode"] = data.pincode?:""
        request["email"] = data.email?:""
        return request
    }

    fun registerUser(
            name:String,
            address:String,
            mobile:String,
            city:String,
            pinCode:String,
            email:String
    ): java.util.HashMap<String, Any> {
        val request = HashMap<String,Any>()
        request["name"] = name
        request["address"] = address
        request["mobile"] = mobile
        request["city"] = city
        request["pincode"] = pinCode
        request["email"] = email
        return request
    }

    fun getPartner(
        pName: String?,
        pMobile: String?,
        pReference: String?,
        pAddress: String?
    ): java.util.HashMap<String, Any> {
        val request = HashMap<String,Any>()
        request["user_id"] = Action.getUserId()
        request["name"] = pName?:""
        request["address"] = pAddress?:""
        request["mobile"] = pMobile?:""
        request["referenceId"] = pReference?:""
        return request
    }

}