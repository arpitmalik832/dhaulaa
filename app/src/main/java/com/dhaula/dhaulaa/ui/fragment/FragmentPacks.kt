package com.dhaula.dhaulaa.ui.fragment

import android.content.Intent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhaula.dhaulaa.R
import com.dhaula.dhaulaa.adapter.AdapterPackages
import com.dhaula.dhaulaa.base.BaseFragment
import com.dhaula.dhaulaa.databinding.FragmentPacksBinding
import com.dhaula.dhaulaa.model.MProduct
import com.dhaula.dhaulaa.navigator.OnClick
import com.dhaula.dhaulaa.ui.activity.ActivityAuth
import com.dhaula.dhaulaa.utils.*
import com.dhaula.dhaulaa.utils.Action.isLogin
import com.dhaula.dhaulaa.viewModel.VMHome

class FragmentPacks : BaseFragment<FragmentPacksBinding>() , OnClick<MProduct>{

    private var binding : FragmentPacksBinding? =null
    private var viewModel : VMHome? = null
    private var adapter : AdapterPackages? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_packs
    }

    override fun initViews() {
        binding = getViewDataBinding()
        viewModel = activity?.let { ViewModelProvider(it).get(VMHome::class.java) }
        binding?.viewModel = viewModel
        viewModel?.setNavigator(this)
        binding?.lifecycleOwner = this

        adapter = AdapterPackages(context,this,ArrayList(),viewModel!!)
        binding?.rvPack?.layoutManager = LinearLayoutManager(context)
        binding?.rvPack?.adapter = adapter

        updateToolbarTitle(viewModel?.categoryDetail?.value?.name?:"Vegetables and Fruits")
    }

    override fun initListeners() {
        viewModel?.productList?.observe(this, Observer {
            if((it?.size?:0) > 0 ){
                Action.visible(binding?.llEmpty, false)
                Action.visible(binding?.rvPack, true)
            }else{
                Action.visible(binding?.llEmpty, true)
                Action.visible(binding?.rvPack, false)
            }
            Action.visible(binding?.pbLoader, false)
            adapter?.setDataList(it)
        })
    }


    override fun showLoading() {
        Action.visible(binding?.pbLoader, true)
        Action.visible(binding?.noData, false)
    }

    override fun hideLoading() {
        Action.visible(binding?.pbLoader, false)
        Action.visible(binding?.noData, true)
    }

    override fun bindDataWithViews() {
        viewModel?.getProducts()
    }

    override fun onClick(bean: MProduct, pos: Int, view: View) {
        if (pos == -1) {
            viewModel?.productDetail?.value = bean
            replaceFragment(FragmentProductDetail(), R.id.fl_home, true)
        } else {
            if (isLogin()) {
                PreferencesUtils.getString(AppConstant.USER_ID)?.let { it1 ->
                    viewModel?.addToBasket(
                        it1,
                        bean.id.toString(),
                        bean.price.toString(),
                        bean.qty.toString()
                    )
                }
            } else {
                PreferencesUtils.clearSharedPrefs()
                val intent = Intent(context, ActivityAuth::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.putExtra("fragment","login")
                startActivity(intent)
                showMessage("Login First",0)
            }
        }
    }

    override fun onAction(code: Int, param: Any?) {
    }

    override fun onDetach() {
        activity?.viewModelStore?.clear()
        super.onDetach()
    }
}