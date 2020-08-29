package com.dhaula.vegitable.base

import androidx.lifecycle.ViewModel
import java.lang.ref.WeakReference


open class BaseViewModel<Chandan> : ViewModel() {
    private var mNavigator: WeakReference<Chandan>? = null
    override fun onCleared() {
        super.onCleared()
    }

    val navigator: Chandan?
        get() = mNavigator!!.get()

    fun setNavigator(navigator: Chandan) {
        mNavigator = WeakReference(navigator)
    }
}
