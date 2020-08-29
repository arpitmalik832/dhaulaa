package com.dhaula.dhaulaa.ui.fragment

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhaula.dhaulaa.R
import com.dhaula.dhaulaa.adapter.AdapterAddress
import com.dhaula.dhaulaa.adapter.AdapterNews
import com.dhaula.dhaulaa.base.BaseFragment
import com.dhaula.dhaulaa.databinding.FragmentNewsBinding
import com.dhaula.dhaulaa.databinding.FragmentSavedAddressBinding
import com.dhaula.dhaulaa.model.MAddress
import com.dhaula.dhaulaa.model.MCategory
import com.dhaula.dhaulaa.navigator.OnClick
import com.dhaula.dhaulaa.utils.Action
import com.dhaula.dhaulaa.utils.UIUtils
import com.dhaula.dhaulaa.viewModel.VMHome
import com.google.gson.Gson

class FragmentSavedAddress: BaseFragment<FragmentSavedAddressBinding>(), OnClick<MAddress> {

    private var binding: FragmentSavedAddressBinding? = null
    private var viewModel: VMHome? = null
    private var addressListAdapter: AdapterAddress? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_saved_address
    }

    override fun initViews() {
        binding = getViewDataBinding()
        viewModel = activity?.let { ViewModelProvider(it).get(VMHome::class.java) }
        binding?.viewModel = viewModel
        viewModel?.setNavigator(this)
        binding?.lifecycleOwner = this
        addressListAdapter = AdapterAddress(context,this,ArrayList())
        binding?.rvNews?.layoutManager = LinearLayoutManager(context)
        binding?.rvNews?.adapter = addressListAdapter
        updateToolbarTitle("My Address")
    }

    override fun initListeners() {
        viewModel?.addressList?.observe(this, Observer {
            addressListAdapter?.setDataList(it)
        })

        binding?.fbAddAddress?.setOnClickListener{
            replaceFragment(FragmentAddAddress(),R.id.fl_home,true)
        }
    }

    override fun bindDataWithViews() {
        viewModel?.getAddress()
    }

    override fun onClick(bean: MAddress, pos: Int, view: View) {
        Action.saveAddress(bean)
//        val intent = Intent(context, FragmentSavedAddress::class.java)
//        val addressJson = Gson().toJson(bean)
//        intent.putExtra("selectedAddress", addressJson)
//        targetFragment?.onActivityResult(targetRequestCode, RESULT_OK, intent)
        activity?.onBackPressed()
        showMessage("Address Changed Successfully",0)
    }
}