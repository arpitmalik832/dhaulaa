package com.dhaula.dhaulaa.navigator

import android.view.View
import com.dhaula.dhaulaa.model.MMenu

interface NavigatorTab {
    fun onTabClick(bean:MMenu,view:View){}
}