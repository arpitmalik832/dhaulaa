package com.dhaula.dhaulaa.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhaula.dhaulaa.R
import com.dhaula.dhaulaa.adapter.AdapterOrder
import com.dhaula.dhaulaa.adapter.AdapterTab
import com.dhaula.dhaulaa.base.BaseFragment
import com.dhaula.dhaulaa.databinding.FragmentMyOrderBinding
import com.dhaula.dhaulaa.model.MMenu
import com.dhaula.dhaulaa.model.MOrder
import com.dhaula.dhaulaa.navigator.NavigatorTab
import com.dhaula.dhaulaa.navigator.OnClick
import com.dhaula.dhaulaa.utils.UIUtils
import com.dhaula.dhaulaa.viewModel.VMHome

class FragmentMyOrder : BaseFragment<FragmentMyOrderBinding>(), OnClick<MOrder>,NavigatorTab {

    private var binding: FragmentMyOrderBinding? = null
    private var viewModel: VMHome? = null
    private var orderListAdapter: AdapterOrder? = null
    private var tabAdapter: AdapterTab? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_my_order
    }

    override fun initViews() {
        binding = getViewDataBinding()
        viewModel = activity?.let { ViewModelProvider(it).get(VMHome::class.java) }
        binding?.viewModel = viewModel
        viewModel?.setNavigator(this)
        binding?.lifecycleOwner = this

        tabAdapter = AdapterTab(context,this,UIUtils.getOrderTabs())
        binding?.rvTab?.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        binding?.rvTab?.adapter = tabAdapter

        orderListAdapter = AdapterOrder(context,this,ArrayList())
        binding?.rvOrderList?.layoutManager = LinearLayoutManager(context)
        binding?.rvOrderList?.adapter = orderListAdapter

        updateToolbarTitle("My Orders")

    }

    override fun initListeners() {
        viewModel?.orderList?.observe(this, Observer {
            orderListAdapter?.setDataList(it)
        })
    }

    override fun bindDataWithViews() {
        viewModel?.fetchOrders()
    }

    override fun onClick(bean: MOrder, pos: Int, view: View) {
        if (pos==-4){
            viewModel?.cancelOrder(bean)
            return
        }
        val fragmentOrderDetail = FragmentOrderDetail()

        val args = Bundle()
        args.putString("orderId",bean.order_id)
        fragmentOrderDetail.arguments = args
        replaceFragment(fragmentOrderDetail,R.id.fl_home,true)
    }

    override fun onTabClick(bean: MMenu, view: View) {
        bean.id?.let { refreshList(it) }
    }

    private fun refreshList(id:Int) {
        val data =  ArrayList<MOrder>()
        when (id) {
            0 -> {
                data.addAll(viewModel?.orderList?.value?:ArrayList())
            }
            else -> {
                for(filteredData in  viewModel?.orderList?.value?:ArrayList()){
                    if(filteredData.status == id.toString()){
                        data.add(filteredData)
                    }
                }
            }
        }
        orderListAdapter?.setDataList(data)
    }

    override fun onAction(code: Int, param: Any?) {
        if(code==200){
            tabAdapter?.notifyDataSetChanged()
        }
    }

}