package com.dhaula.dhaulaa.ui.fragment

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dhaula.dhaulaa.R
import com.dhaula.dhaulaa.base.BaseFragment
import com.dhaula.dhaulaa.databinding.FragmentLoginBinding
import com.dhaula.dhaulaa.utils.Status
import com.dhaula.dhaulaa.utils.UIUtils
import com.dhaula.dhaulaa.viewModel.VMAuth

class FragmentLogin : BaseFragment<FragmentLoginBinding>() {

    private var binding : FragmentLoginBinding? =null
    private var viewModel : VMAuth? =null

    override fun getLayoutId(): Int {
        return R.layout.fragment_login
    }

    override fun initViews() {
        binding = getViewDataBinding()
        viewModel = activity?.let { ViewModelProvider(it).get(VMAuth::class.java) }
        binding?.viewModel = viewModel
        viewModel?.setNavigator(this)
        binding?.lifecycleOwner = this
    }

    override fun initListeners() {
        viewModel?.loginProgressText?.observe(this, Observer {
            UIUtils.handleLoader(it,binding?.pbNetwork)
        })
    }

    override fun bindDataWithViews() {
    }

    override fun onAction(code: Int, param: Any?) {
        if(code==Status.SUCCESS){
            replaceFragment(FragmentVerify(),R.id.fl_auth,true)
        }
    }
}