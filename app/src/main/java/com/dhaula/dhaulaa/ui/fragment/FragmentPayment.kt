package com.dhaula.dhaulaa.ui.fragment

import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.dhaula.dhaulaa.R
import com.dhaula.dhaulaa.base.BaseFragment
import com.dhaula.dhaulaa.databinding.FragmentPaymentBinding
import com.dhaula.dhaulaa.ui.activity.ActivityAuth
import com.dhaula.dhaulaa.utils.Action.isLogin
import com.dhaula.dhaulaa.utils.AppConstant
import com.dhaula.dhaulaa.utils.PreferencesUtils
import com.dhaula.dhaulaa.viewModel.VMHome

class FragmentPayment : BaseFragment<FragmentPaymentBinding>() {

        private var binding: FragmentPaymentBinding? = null
        private var viewModel: VMHome? = null

        override fun getLayoutId(): Int {
            return R.layout.fragment_payment
        }

        override fun initViews() {
            binding = getViewDataBinding()
            viewModel = activity?.let { ViewModelProvider(it).get(VMHome::class.java) }
            binding?.viewModel = viewModel
            viewModel?.setNavigator(this)
            binding?.lifecycleOwner = this
        }

        override fun initListeners() {
            binding?.btPlaceOrder!!.setOnClickListener {
                if(isLogin()) {
                    when {
                        binding?.payCod!!.isChecked -> {
                            PreferencesUtils.getString(AppConstant.USER_ID)?.let { it1 ->
                                viewModel!!.addOrder(
                                        it1,
                                        arguments!!.getString("grand_total")?: "",
                                        "COD",
                                        arguments!!.getString("address")?:"",
                                        this
                                )
                            }
                        }
                        binding?.payPayU!!.isChecked -> {
                            Toast.makeText(context, "PayU Money Under Progress", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            Toast.makeText(context, "Please choose any one", Toast.LENGTH_SHORT).show()
                        }
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

        override fun bindDataWithViews() {

        }

    }