package com.dhaula.dhaulaa.ui.fragment

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import com.dhaula.dhaulaa.R
import com.dhaula.dhaulaa.base.BaseFragment
import com.dhaula.dhaulaa.databinding.FragmentProfileBinding
import com.dhaula.dhaulaa.model.MUser
import com.dhaula.dhaulaa.navigator.NavigatorDialog
import com.dhaula.dhaulaa.ui.activity.ActivitySplash
import com.dhaula.dhaulaa.ui.dialog.DialogProfileEdit
import com.dhaula.dhaulaa.utils.Action
import com.dhaula.dhaulaa.utils.Action.isLogin
import com.dhaula.dhaulaa.utils.Action.visible
import com.dhaula.dhaulaa.utils.AppConstant
import com.dhaula.dhaulaa.utils.PreferencesUtils
import com.dhaula.dhaulaa.viewModel.VMHome
import com.google.gson.Gson

class FragmentProfile : BaseFragment<FragmentProfileBinding>() {

    private var binding: FragmentProfileBinding? = null
    private var viewModel: VMHome? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_profile
    }

    override fun initViews() {
        binding = getViewDataBinding()
        viewModel = activity?.let { ViewModelProvider(it).get(VMHome::class.java) }
        binding?.viewModel = viewModel
        viewModel?.setNavigator(this)
        binding?.lifecycleOwner = this
    }

    override fun initListeners() {
        binding?.tvAddress?.setOnClickListener {
            replaceFragment(FragmentSavedAddress(), R.id.fl_home, true)
        }

        binding?.tvEdit?.setOnClickListener {
            val dialog = DialogProfileEdit(object : NavigatorDialog<MUser>{
                override fun positiveClick(data: MUser, code: Int) {
                    viewModel?.updateUser?.value = data
                    viewModel?.updateProfile()

                    val updatedData = MUser()
                    updatedData.email = data.email
                    updatedData.name = data.name

                    val userData:MUser? = Gson().fromJson(PreferencesUtils.getString(AppConstant.USER_DATA)?:"",MUser::class.java)
                    updatedData.mobile = userData?.mobile
                    updatedData.id = userData?.id
                    updatedData.pincode = userData?.pincode
                    updatedData.address = userData?.address
                    updatedData.city = userData?.city
                    updatedData.partner = userData?.partner
                    PreferencesUtils.putString(AppConstant.USER_DATA,Gson().toJson(updatedData))

                    binding?.etUsername?.text = updatedData.name
                    binding?.tvMobile?.text = updatedData.mobile
                    binding?.etEmail?.text = updatedData.email
                }
                override fun cancelClick(code: Int) {
                }
            })
            parentFragmentManager.let {
                dialog.show(it,"edit_profile")
            }
        }

        binding?.btLogout?.setOnClickListener {
            PreferencesUtils.clearSharedPrefs()
            startActivity(Intent(context, ActivitySplash::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP))
            showMessage("Logout Successfully",0)
            activity?.finish()
        }
    }

    override fun bindDataWithViews() {
        visible(binding?.btLogout, isLogin())
        val userData:MUser? = Gson().fromJson(PreferencesUtils.getString(AppConstant.USER_DATA)?:"",MUser::class.java)
        binding?.etUsername?.text = userData?.name?:""
        binding?.tvMobile?.text = userData?.mobile?:""
        binding?.etEmail?.text = userData?.email?:""
    }

    override fun onResume() {
        super.onResume()
        binding?.tvAddress?.text = Action.getFancyAddress()
    }

}