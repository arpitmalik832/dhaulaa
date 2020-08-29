package com.dhaula.dhaulaa.navigator

import android.view.View

interface OnClick<T> {
    fun onClick(bean:T , pos:Int , view:View){}
}