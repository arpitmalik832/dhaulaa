package com.dhaula.dhaulaa.ui.fragment

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhaula.dhaulaa.R
import com.dhaula.dhaulaa.adapter.AdapterOrderItem
import com.dhaula.dhaulaa.base.BaseFragment
import com.dhaula.dhaulaa.databinding.FragmentOrderDetailBinding
import com.dhaula.dhaulaa.model.MBasketItem
import com.dhaula.dhaulaa.navigator.OnClick
import com.dhaula.dhaulaa.ui.dialog.DialogRating
import com.dhaula.dhaulaa.utils.Action
import com.dhaula.dhaulaa.utils.UIUtils
import com.dhaula.dhaulaa.viewModel.VMHome

class FragmentOrderDetail : BaseFragment<FragmentOrderDetailBinding>(), OnClick<MBasketItem> {

    private var binding: FragmentOrderDetailBinding? = null
    private var viewModel: VMHome? = null
    private var orderDetailAdapter: AdapterOrderItem? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_order_detail
    }

    override fun initViews() {
        binding = getViewDataBinding()
        viewModel = activity?.let { ViewModelProvider(it).get(VMHome::class.java) }
        binding?.viewModel = viewModel
        viewModel?.setNavigator(this)
        binding?.lifecycleOwner = this

        orderDetailAdapter = viewModel?.let { AdapterOrderItem(context, this, ArrayList(), it) }
        binding?.rvOd?.layoutManager = LinearLayoutManager(context)
        binding?.rvOd?.adapter = orderDetailAdapter

        updateToolbarTitle("Order Details")

    }

    override fun initListeners() {
        viewModel?.orderItemsList?.observe(this, Observer {
            orderDetailAdapter?.setDataList(it)
        })

        binding?.btReviewOrder?.setOnClickListener {
            val dialog = DialogRating()
            dialog.show(parentFragmentManager,"dialog")
        }
    }

    override fun bindDataWithViews() {
        viewModel?.fetchOrderDetail(arguments?.getString("orderId")?:"")

        viewModel?.orderDetail?.observe(this, Observer {
            binding?.orderId?.text = "Order No: ${it.order_id?:""}"
            binding?.orderDate?.text = it.order_date?:""
            binding?.status?.text = it.status?:""
            binding?.quantity?.text = "Quantity: ${if(viewModel?.orderItemsList?.value?.size == null) 1 else viewModel?.orderItemsList?.value?.size}"

            binding?.address?.text = it.shipping_address?:"No Address"
            binding?.tvTotalAmount?.text = it.grand_price?:""
            binding?.paymentMethod?.text = it.order_type?:""
        })

        binding?.btOrderAgain?.setOnClickListener{
            for(i in viewModel?.orderItemsList?.value?.indices!!) {
                viewModel?.addToBasket(
                        Action.getUserId(),
                        viewModel?.orderItemsList?.value?.get(i)?.id.toString(),
                        viewModel?.orderItemsList?.value?.get(i)?.price.toString(),
                        viewModel?.orderItemsList?.value?.get(i)?.quantity.toString()
                )
            }
            replaceFragment(FragmentBasket(),R.id.fl_home,false)
        }

    }
}