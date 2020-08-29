package com.dhaula.dhaulaa.ui.activity

import androidx.lifecycle.ViewModelProvider
import com.dhaula.dhaulaa.R
import com.dhaula.dhaulaa.base.BaseActivity
import com.dhaula.dhaulaa.databinding.ActivityAuthBinding
import com.dhaula.dhaulaa.ui.fragment.FragmentLogin
import com.dhaula.dhaulaa.ui.fragment.FragmentRegister
import com.dhaula.dhaulaa.viewModel.VMAuth

class ActivityAuth : BaseActivity<ActivityAuthBinding>() {

    private var binding : ActivityAuthBinding? =null
    private var viewModel : VMAuth? =null

    override val layoutId: Int
        get() = R.layout.activity_auth

    override fun initViews() {
        binding = getViewDataBinding()
        viewModel = ViewModelProvider(this).get(VMAuth::class.java)
        binding?.viewModel = viewModel
        viewModel?.setNavigator(this)
        binding?.lifecycleOwner = this
    }

    override fun initListeners() {

    }

    override fun bindDataWithViews() {
        if(intent.getStringExtra("fragment") == "login")
            replaceFragment(FragmentLogin(),R.id.fl_auth,false)
        if(intent.getStringExtra("fragment") == "register")
            replaceFragment(FragmentRegister(),R.id.fl_auth,false)
    }
}