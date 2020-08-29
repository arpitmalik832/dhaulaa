package com.dhaula.vegitable.navigator

interface NavigatorBase {
    fun showLoading()
    fun hideLoading()
    fun showMessage(msg: String?, code: Int)
    fun onAction(code: Int, data: Any?)
}