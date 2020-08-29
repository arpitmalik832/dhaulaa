package com.dhaula.dhaulaa.ui.fragment

import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dhaula.dhaulaa.R
import com.dhaula.dhaulaa.adapter.AdapterPackages
import com.dhaula.dhaulaa.base.BaseFragment
import com.dhaula.dhaulaa.databinding.FragmentRegisterBinding
import com.dhaula.dhaulaa.ui.activity.ActivityHome
import com.dhaula.dhaulaa.utils.Status
import com.dhaula.dhaulaa.utils.UIUtils
import com.dhaula.dhaulaa.viewModel.VMAuth

class FragmentRegister : BaseFragment<FragmentRegisterBinding>() {

    private var binding: FragmentRegisterBinding? = null
    private var viewModel: VMAuth? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_register
    }

    override fun initViews() {
        binding = getViewDataBinding()
        viewModel = activity?.let { ViewModelProvider(it).get(VMAuth::class.java) }
        binding?.viewModel = viewModel
        viewModel?.setNavigator(this)
        binding?.lifecycleOwner = this

        updateToolbarTitle("Register")
    }

    override fun initListeners() {
        viewModel?.registerProgressText?.observe(this, Observer {
            UIUtils.handleLoader(it,binding?.pbNetwork)
        })
    }

    override fun bindDataWithViews() {
    }

    override fun onAction(code: Int, param: Any?) {
        if (code == Status.SUCCESS) {
            startActivity(Intent(activity, ActivityHome::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP))
        }
    }

    override fun onDetach() {
        activity?.viewModelStore?.clear()
        super.onDetach()
    }
}