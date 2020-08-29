package com.dhaula.dhaulaa.ui.fragment

import androidx.lifecycle.ViewModelProvider
import com.dhaula.dhaulaa.R
import com.dhaula.dhaulaa.adapter.AdapterPackages
import com.dhaula.dhaulaa.base.BaseFragment
import com.dhaula.dhaulaa.databinding.FragmentPartnerBinding
import com.dhaula.dhaulaa.viewModel.VMHome

class FragmentPartner : BaseFragment<FragmentPartnerBinding>() {

    private var binding: FragmentPartnerBinding? = null
    private var viewModel: VMHome? = null
    private var adapter: AdapterPackages? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_partner
    }

    override fun initViews() {
        binding = getViewDataBinding()
        viewModel = activity?.let { ViewModelProvider(it).get(VMHome::class.java) }
        binding?.viewModel = viewModel
        viewModel?.setNavigator(this)
        binding?.lifecycleOwner = this

        updateToolbarTitle("Become a Partner")
    }

    override fun initListeners() {

    }

    override fun bindDataWithViews() {

    }

    override fun onAction(code: Int, param: Any?) {
    }

}