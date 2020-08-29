package com.dhaula.dhaulaa.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.dhaula.dhaulaa.R
import com.dhaula.dhaulaa.base.BaseActivity
import com.dhaula.dhaulaa.databinding.ActivitySplashBinding
import com.dhaula.dhaulaa.utils.AppConstant
import com.dhaula.dhaulaa.utils.PreferencesUtils

class ActivitySplash : BaseActivity<ActivitySplashBinding>() {

    private var action: Runnable? = null
    private var handler: Handler? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override val layoutId: Int
        get() = R.layout.activity_splash

    override fun initViews() {
    }

    override fun initListeners() {
    }

    override fun bindDataWithViews() {
        handler = Handler()
        handler?.postDelayed(Runnable {
            if (PreferencesUtils.getBoolean(AppConstant.IS_LOGIN) == true) {
                startActivity(
                    Intent(
                        this,
                        ActivityHome::class.java
                    )
                )
            } else {
                startActivity(
                    Intent(
                        this,
                        ActivityHome::class.java
                    )
                )
            }
        }.also { action = it }, 1000)    }
}