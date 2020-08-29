package com.dhaula.dhaulaa.ui.fragment

import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dhaula.dhaulaa.R
import com.dhaula.dhaulaa.base.BaseFragment
import com.dhaula.dhaulaa.databinding.FragmentVerifyBinding
import com.dhaula.dhaulaa.model.MOtp
import com.dhaula.dhaulaa.model.MUser
import com.dhaula.dhaulaa.ui.activity.ActivityHome
import com.dhaula.dhaulaa.utils.AppConstant
import com.dhaula.dhaulaa.utils.PreferencesUtils
import com.dhaula.dhaulaa.utils.Status
import com.dhaula.dhaulaa.utils.UIUtils
import com.dhaula.dhaulaa.viewModel.VMAuth

class FragmentVerify : BaseFragment<FragmentVerifyBinding>() {

    private var binding: FragmentVerifyBinding? = null
    private var viewModel: VMAuth? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_verify
    }

    override fun initViews() {
        binding = getViewDataBinding()
        viewModel = activity?.let { ViewModelProvider(it).get(VMAuth::class.java) }
        binding?.viewModel = viewModel
        viewModel?.setNavigator(this)
        binding?.lifecycleOwner = this
    }

    override fun initListeners() {
        viewModel?.otpProgressText?.observe(this, Observer {
            UIUtils.handleLoader(it,binding?.pbNetwork)
        })
    }

    override fun bindDataWithViews() {
    }

    override fun onAction(code: Int, param: Any?) {
        if (code == Status.SUCCESS) {
            startActivity(Intent(activity, ActivityHome::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP))
        }
    }

    override fun onDetach() {
        super.onDetach()
        viewModel?.otp?.value = ""
        viewModel?.serverData?.value = MUser()
    }
}