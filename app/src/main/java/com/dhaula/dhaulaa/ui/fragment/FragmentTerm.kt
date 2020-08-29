package com.dhaula.dhaulaa.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.dhaula.dhaulaa.R
import com.dhaula.dhaulaa.adapter.AdapterPackages
import com.dhaula.dhaulaa.base.BaseFragment
import com.dhaula.dhaulaa.databinding.FragmentPartnerBinding
import com.dhaula.dhaulaa.databinding.FragmentTermBinding
import com.dhaula.dhaulaa.viewModel.VMHome

class FragmentTerm : BaseFragment<FragmentTermBinding>() {

    private var binding: FragmentTermBinding? = null
    private var viewModel: VMHome? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_term
    }

    override fun initViews() {
        binding = getViewDataBinding()
        viewModel = activity?.let { ViewModelProvider(it).get(VMHome::class.java) }
        binding?.viewModel = viewModel
        viewModel?.setNavigator(this)
        binding?.lifecycleOwner = this

        updateToolbarTitle("Terms and Condition")
    }

    override fun initListeners() {

    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun bindDataWithViews() {

    }

    override fun onAction(code: Int, param: Any?) {
    }

}