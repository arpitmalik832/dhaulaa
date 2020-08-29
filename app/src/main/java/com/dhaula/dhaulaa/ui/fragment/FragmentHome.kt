package com.dhaula.dhaulaa.ui.fragment

import android.text.TextUtils
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.dhaula.dhaulaa.R
import com.dhaula.dhaulaa.adapter.AdapterCategory
import com.dhaula.dhaulaa.base.BaseFragment
import com.dhaula.dhaulaa.databinding.FragmentHomeBinding
import com.dhaula.dhaulaa.model.MCategory
import com.dhaula.dhaulaa.navigator.OnClick
import com.dhaula.dhaulaa.ui.activity.ActivityHome
import com.dhaula.dhaulaa.utils.Menu
import com.dhaula.dhaulaa.utils.UIUtils
import com.dhaula.dhaulaa.viewModel.VMHome


class FragmentHome : BaseFragment<FragmentHomeBinding>(), OnClick<MCategory> {

    private var binding: FragmentHomeBinding? = null
    private var viewModel: VMHome? = null
    private var adapterCategoryTop: AdapterCategory? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initViews() {
        binding = getViewDataBinding()
        viewModel = activity?.let { ViewModelProvider(it).get(VMHome::class.java) }
        binding?.viewModel = viewModel
        viewModel?.setNavigator(this)
        binding?.lifecycleOwner = this

        adapterCategoryTop = AdapterCategory(context, this, ArrayList())
        binding?.rvHomeCat?.adapter = adapterCategoryTop
        binding?.rvHomeCat?.layoutManager = GridLayoutManager(context, 2)
    }

    override fun initListeners() {
        viewModel?.categoryList?.observe(this, Observer {
            adapterCategoryTop?.setDataList(it)
        })

        binding?.ivDrawerMenu?.setOnClickListener {
            if (activity != null) {
                (activity as ActivityHome).openDrawer()
            }
        }

        viewModel?.banner?.observe(this, Observer {
            if(!TextUtils.isEmpty(it)){
                UIUtils.loadImage(context,it , binding?.ivTop, 0)
            }
        })
    }

    override fun bindDataWithViews() {
        viewModel?.getCategories()
    }

    override fun onAction(code: Int, param: Any?) {
    }

    override fun onClick(bean: MCategory, pos: Int, view: View) {
        viewModel?.categoryDetail?.value = bean
        replaceFragment(Menu.getMenuNavigation(bean.id?:0), R.id.fl_home, true)
    }

    override fun onResume() {
        super.onResume()
    }


}