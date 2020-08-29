package com.dhaula.dhaulaa.ui.fragment

import android.content.Intent
import android.content.res.ColorStateList
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.dhaula.dhaulaa.R
import com.dhaula.dhaulaa.base.BaseFragment
import com.dhaula.dhaulaa.databinding.FragmentProductDetailBinding
import com.dhaula.dhaulaa.ui.activity.ActivityAuth
import com.dhaula.dhaulaa.utils.Action.isLogin
import com.dhaula.dhaulaa.utils.AppConstant
import com.dhaula.dhaulaa.utils.PreferencesUtils
import com.dhaula.dhaulaa.utils.Status
import com.dhaula.dhaulaa.utils.UIUtils
import com.dhaula.dhaulaa.viewModel.VMHome

class FragmentProductDetail : BaseFragment<FragmentProductDetailBinding>() {

    private var binding: FragmentProductDetailBinding? = null
    private var viewModel: VMHome? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_product_detail
    }

    override fun initViews() {
        binding = getViewDataBinding()
        viewModel = activity?.let { ViewModelProvider(it).get(VMHome::class.java) }
        binding?.viewModel = viewModel
        viewModel?.setNavigator(this)
        binding?.lifecycleOwner = this
        updateToolbarTitle(viewModel?.productDetail?.value?.name?:"Product")

    }

    override fun initListeners() {
        binding?.btAddBsk?.setOnClickListener {
            if(isLogin()) {
                if(TextUtils.isEmpty(binding?.etQty?.text)){
                    showMessage("Please enter Qty",0)
                    return@setOnClickListener
                }else if(binding?.etQty?.text?.toString().equals("0")){
                    showMessage("Qty Can't 0 or less",0)
                    return@setOnClickListener
                }
                PreferencesUtils.getString(AppConstant.USER_ID)?.let { it1 ->
                    viewModel?.addToBasket(it1,
                        viewModel?.productDetail?.value?.id.toString(),
                        viewModel?.productDetail?.value?.price.toString(),
                        if(binding?.etQty!!.text!!.isNotEmpty())
                            binding?.etQty!!.text.toString()
                        else
                            "3 Kg"
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

        binding?.etQty?.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val qty = binding?.etQty?.text
                if(!TextUtils.isEmpty(qty)) {
                   val amt =  viewModel?.productDetail?.value?.offerPrice?:0
                    binding?.tvTtlPrice?.text = ("Rs. ${(amt*(qty.toString().toInt()))}")
                }else{
                    binding?.tvTtlPrice?.text = ("Rs. 0")
                }
            }
        })
    }

    override fun bindDataWithViews() {
        binding?.ivProduct?.let { UIUtils.loadImage(context,viewModel?.productDetail?.value?.image, it,0) }
        binding?.tvPrice?.text = ("Price Rs. ${viewModel?.productDetail?.value?.offerPrice?:0}")
        binding?.tvName?.text = "${viewModel?.productDetail?.value?.name}"
        binding?.tvDesc?.text = "${viewModel?.productDetail?.value?.description}"
        binding?.btAddBsk?.isEnabled = viewModel?.productDetail?.value?.in_stock != 0
        binding?.btAddBsk?.text = if(viewModel?.productDetail?.value?.in_stock != 0){"Add To Basket"}else{"Out Of Stock"}
        binding?.etQty?.isEnabled = viewModel?.productDetail?.value?.in_stock != 0
        if (viewModel?.productDetail?.value?.in_stock == 0) {
            context?.let { ContextCompat.getColor(it,R.color.colorDarkGray) }?.let {
                binding?.btAddBsk?.backgroundTintList = ColorStateList.valueOf(it)
            }
        }else{
            context?.let { ContextCompat.getColor(it,R.color.colorPrimary) }?.let {
                binding?.btAddBsk?.backgroundTintList = ColorStateList.valueOf(it)
            }
        }
    }

    override fun onAction(code: Int, param: Any?) {
        if(code==Status.SUCCESS){
            activity?.onBackPressed()
        }
    }



}