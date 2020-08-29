package com.dhaula.dhaulaa.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhaula.dhaulaa.BasketInterface
import com.dhaula.dhaulaa.R
import com.dhaula.dhaulaa.adapter.AdapterBasket
import com.dhaula.dhaulaa.base.BaseFragment
import com.dhaula.dhaulaa.databinding.FragmentBasketBinding
import com.dhaula.dhaulaa.model.MBasketItem
import com.dhaula.dhaulaa.navigator.OnClick
import com.dhaula.dhaulaa.utils.Action
import com.dhaula.dhaulaa.utils.Action.isLogin
import com.dhaula.dhaulaa.utils.Action.visible
import com.dhaula.dhaulaa.utils.AppConstant
import com.dhaula.dhaulaa.utils.PreferencesUtils
import com.dhaula.dhaulaa.utils.UIUtils.SELECT_SHIPPING_ADDRESS_CODE
import com.dhaula.dhaulaa.viewModel.VMHome
import org.w3c.dom.Text

class FragmentBasket : BaseFragment<FragmentBasketBinding>(), OnClick<MBasketItem>,
    BasketInterface {

    private var binding: FragmentBasketBinding? = null
    private var viewModel: VMHome? = null
    private var basketListAdapter: AdapterBasket? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_basket
    }

    override fun initViews() {
        binding = getViewDataBinding()
        viewModel = activity?.let { ViewModelProvider(it).get(VMHome::class.java) }
        binding?.viewModel = viewModel
        viewModel?.setNavigator(this)
        binding?.lifecycleOwner = this
        basketListAdapter = AdapterBasket(context, this, ArrayList(), this as BasketInterface, viewModel!!)
        binding?.rvBasket?.layoutManager = LinearLayoutManager(context)
        binding?.rvBasket?.adapter = basketListAdapter
        visible(binding?.nsvData, isLogin())
        visible(binding?.flEmpty, !isLogin())
    }

    override fun initListeners() {
        viewModel?.basketList?.observe(this, Observer {
            if ((it?.size ?: 0) > 0) {
                visible(binding?.flEmpty, false)
                visible(binding?.nsvData, true)
            } else {
                visible(binding?.nsvData, false)
                visible(binding?.flEmpty, true)
            }
            visible(binding?.pbLoader, false)
            basketListAdapter?.setDataList(it)
        })
        binding?.tvEdit?.setOnClickListener {
            val savedAddressFragment = FragmentSavedAddress()
            savedAddressFragment.setTargetFragment(
                this,
                SELECT_SHIPPING_ADDRESS_CODE
            )

            replaceFragment(
                savedAddressFragment,
                R.id.fl_home,
                true
            )
        }
        binding?.btPayment!!.setOnClickListener {

            if(TextUtils.isEmpty(Action.getAddressId()) || Action.getAddressId() == "0"){
                showMessage("Please select an Address",0)
                return@setOnClickListener
            }

            if (binding?.tvTtlPrice?.text == "Rs 0") {
                showMessage("Cart is Empty", 0)
            } else {
                val fragmentPayment = FragmentPayment()
                val args = Bundle()
                args.putString("grand_total", binding?.tvTtlPrice!!.text.toString())
                args.putString("address", binding?.cbAddress!!.text.toString())
                fragmentPayment.arguments = args
                replaceFragment(fragmentPayment, R.id.fl_home, true)
            }
        }
    }

    override fun showLoading() {
        visible(binding?.pbLoader, true)
        visible(binding?.noDataImg, false)
        visible(binding?.noData, false)
    }

    override fun hideLoading() {
        visible(binding?.pbLoader, false)
        visible(binding?.noDataImg, true)
        visible(binding?.noData, true)
    }

    override fun bindDataWithViews() {
        PreferencesUtils.getString(AppConstant.USER_ID)?.let { viewModel?.getBasket(it) }
    }

    override fun onResume() {
        super.onResume()
        updateToolbarTitle("Shopping Basket")
    }

    override fun onStart() {
        super.onStart()
        if(TextUtils.isEmpty(Action.getAddressId()) || Action.getAddressId() == "0"){
            binding?.cbAddress?.text = ("No Address Selected")
        }else{
            binding?.cbAddress?.text = Action.getFancyAddress()
        }
    }


    override fun updateAmount(amount: Int) {
        val tvQtyText = "Rs $amount"
        binding?.tvQty?.text = tvQtyText
        binding?.tvTtlPrice?.text = tvQtyText
    }

    override fun listIsEmpty() {
        val text0 = "Rs 0"
        binding?.tvQty?.text = text0
        binding?.tvTtlPrice?.text = text0

        viewModel?.basketList?.observe(this, Observer {
            if ((it?.size ?: 0) > 0) {
                visible(binding?.flEmpty, false)
                visible(binding?.nsvData, true)
            } else {
                visible(binding?.nsvData, false)
                visible(binding?.flEmpty, true)
            }
            visible(binding?.pbLoader, false)
            basketListAdapter?.setDataList(it)
        })
    }


}