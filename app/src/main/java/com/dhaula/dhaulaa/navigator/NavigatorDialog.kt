package com.dhaula.dhaulaa.navigator

interface NavigatorDialog<T> {
    fun positiveClick(data:T,code:Int)
    fun cancelClick(code: Int)
}