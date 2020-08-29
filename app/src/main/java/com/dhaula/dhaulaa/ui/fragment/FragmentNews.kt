package com.dhaula.dhaulaa.ui.fragment

import android.content.Intent
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhaula.dhaulaa.R
import com.dhaula.dhaulaa.adapter.AdapterNews
import com.dhaula.dhaulaa.base.BaseFragment
import com.dhaula.dhaulaa.databinding.FragmentNewsBinding
import com.dhaula.dhaulaa.model.MNews
import com.dhaula.dhaulaa.navigator.OnClick
import com.dhaula.dhaulaa.utils.Action.visible
import com.dhaula.dhaulaa.viewModel.VMHome

class FragmentNews : BaseFragment<FragmentNewsBinding>(), OnClick<MNews> {

    private var binding: FragmentNewsBinding? = null
    private var viewModel: VMHome? = null
    private var basketListAdapter: AdapterNews? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_news
    }

    override fun initViews() {
        binding = getViewDataBinding()
        viewModel = activity?.let { ViewModelProvider(it).get(VMHome::class.java) }
        binding?.viewModel = viewModel
        viewModel?.setNavigator(this)
        binding?.lifecycleOwner = this
        basketListAdapter = AdapterNews(context, this, ArrayList())
        binding?.rvNews?.layoutManager = LinearLayoutManager(context)
        binding?.rvNews?.adapter = basketListAdapter
        visible(binding?.rvNews,false)
        visible(binding?.llEmpty,true)
    }

    override fun initListeners() {
        viewModel?.newsList?.observe(this, Observer {
            if((it?.size?:0) > 0 ){
                visible(binding?.llEmpty,false)
                visible(binding?.rvNews,true)
            }else{
                visible(binding?.llEmpty,true)
                visible(binding?.rvNews,false)
            }
            visible(binding?.pbLoader,false)
            basketListAdapter?.setDataList(it)
        })
    }

    override fun showLoading() {
        visible(binding?.pbLoader,true)
        visible(binding?.noData,false)
    }

    override fun hideLoading() {
        visible(binding?.pbLoader,false)
        visible(binding?.noData,true)
    }

    override fun bindDataWithViews() {
        viewModel?.getNews()
    }

    override fun onClick(bean: MNews, pos: Int, view: View) {
        viewModel?.newsDetail?.value = bean
        replaceFragment(FragmentNewsDetail(), R.id.fl_home, true)
    }

    override fun onResume() {
        super.onResume()
        updateToolbarTitle("News and Articles")
    }

}