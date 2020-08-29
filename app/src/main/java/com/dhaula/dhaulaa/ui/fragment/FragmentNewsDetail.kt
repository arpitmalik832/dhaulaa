package com.dhaula.dhaulaa.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.dhaula.dhaulaa.R
import com.dhaula.dhaulaa.base.BaseFragment
import com.dhaula.dhaulaa.databinding.FragmentNewsBinding
import com.dhaula.dhaulaa.databinding.FragmentNewsDetailBinding
import com.dhaula.dhaulaa.utils.UIUtils
import com.dhaula.dhaulaa.viewModel.VMHome

class FragmentNewsDetail : BaseFragment<FragmentNewsDetailBinding>() {

    private var binding: FragmentNewsDetailBinding? = null
    private var viewModel: VMHome? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_news_detail
    }

    override fun initViews() {
        binding = getViewDataBinding()
        viewModel = activity?.let { ViewModelProvider(it).get(VMHome::class.java) }
        binding?.viewModel = viewModel
        viewModel?.setNavigator(this)
        binding?.lifecycleOwner = this
        updateToolbarTitle("Article")

    }

    override fun initListeners() {

    }

    override fun bindDataWithViews() {
        binding?.ivPicture?.let { UIUtils.loadImage(context,viewModel?.newsDetail?.value?.image, it,0) }
    }
}