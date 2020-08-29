package com.dhaula.dhaulaa.ui.fragment

import androidx.lifecycle.ViewModelProvider
import com.dhaula.dhaulaa.R
import com.dhaula.dhaulaa.base.BaseFragment
import com.dhaula.dhaulaa.databinding.FragmentAddAddressBinding
import com.dhaula.dhaulaa.viewModel.VMHome

class FragmentAddAddress : BaseFragment<FragmentAddAddressBinding>() {

    private var binding: FragmentAddAddressBinding? = null
    private var viewModel: VMHome? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_add_address
    }

    override fun initViews() {
        binding = getViewDataBinding()
        viewModel = activity?.let { ViewModelProvider(it).get(VMHome::class.java) }
        binding?.viewModel = viewModel
        viewModel?.setNavigator(this)
        binding?.lifecycleOwner = this
        updateToolbarTitle("Add Address")
    }

    override fun initListeners() {
//        binding?.btRegister?.setOnClickListener {
//            viewModel?.saveAddress()
//        }
    }

    override fun bindDataWithViews() {
    }

    override fun onAction(code: Int, param: Any?) {
    }

}