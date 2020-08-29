package com.dhaula.dhaulaa.base

import android.app.ProgressDialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.dhaula.dhaulaa.MApplication
import com.dhaula.dhaulaa.R
import com.dhaula.vegitable.navigator.NavigatorBase

abstract class BaseFragment<T : ViewDataBinding?> : Fragment(),
    NavigatorBase {
    @LayoutRes
    protected abstract fun getLayoutId(): Int

    private var mViewDataBinding: T? = null
    private var mRootView: View? = null
    private var mProgressDialog: ProgressDialog? = null


    open fun getViewDataBinding(): T? {
        return mViewDataBinding
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewDataBinding = DataBindingUtil.inflate<T>(inflater, getLayoutId(), container, false)
        mRootView = mViewDataBinding?.root
        init()
        return mRootView
    }

    private fun init() {
        initViews()
        initListeners()
        bindDataWithViews()
    }

    abstract fun initViews()
    abstract fun initListeners()
    abstract fun bindDataWithViews()



    override fun showLoading() {
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog(activity, R.style.DialogTheme)
            mProgressDialog!!.isIndeterminate = false
            mProgressDialog!!.setCancelable(false)
            mProgressDialog!!.show()
            mProgressDialog?.setContentView(R.layout.dialog_progress)
        }
        if (!mProgressDialog!!.isShowing) {
            mProgressDialog!!.show()
        }
    }

    open fun isShowingProgress(): Boolean {
        return mProgressDialog != null && mProgressDialog!!.isShowing
    }

    override fun hideLoading() {
        if (mProgressDialog != null && mProgressDialog!!.isShowing) {
            mProgressDialog!!.dismiss()
            mProgressDialog = null
        }
    }

    override fun onAction(code: Int, param: Any?) {}

    override fun showMessage(msg: String?, code: Int) {
        showToast(msg)
    }


    open fun showToast(msg: String?) {
        val toast = Toast(MApplication.getContext())
        toast.duration = Toast.LENGTH_SHORT
        val inflater =
            MApplication.getContext()?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(
            R.layout.chandan_makes_toast,
            mRootView?.findViewById(R.id.custom_toast_root)
        )
        (view.findViewById(R.id.custom_toast_tv) as TextView).text = msg
        toast.view = view
        toast.show()
    }

    open fun hideSoftInputKeyboard() {
        if (activity == null) return
        val view = activity!!.currentFocus
        val imm =
            activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (view != null && imm != null) {
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    open fun updateToolbarTitle(title: String?) {
        if (activity == null) return
        (activity as BaseActivity<*>?)!!.updateToolbarTitle(title)
    }

    open fun addFragment(fragment: Fragment?, containerId: Int) {
        fragmentManager?.beginTransaction()?.add(containerId, fragment!!)?.commitAllowingStateLoss()
    }

    open fun replaceFragment(
        fragment: Fragment,
        containerId: Int,
        addToBackStack: Boolean
    ) {
        if (activity != null) {
            val replace =
                activity!!.supportFragmentManager.beginTransaction()
                    .replace(containerId, fragment)
            if (addToBackStack) {
                replace.addToBackStack(fragment.javaClass.name)
            }
            replace.commitAllowingStateLoss()
        }
    }


    open fun askForPermissions(
        permissions: Array<String?>,
        permissionCode: Int
    ): Boolean {
        if (!hasPermissions(permissions)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ActivityCompat.requestPermissions(
                    activity!!,
                    permissions,
                    permissionCode
                )
            }
        }
        return hasPermissions(permissions)
    }

    open fun askForPermission(permission: String, permissionCode: Int): Boolean {
        if (!hasPermission(permission)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ActivityCompat.requestPermissions(
                    activity!!, arrayOf(permission),
                    permissionCode
                )
            }
        }
        return hasPermission(permission)
    }

    open fun hasPermissions(permission: Array<String?>): Boolean {
        for (s in permission) {
            if (ContextCompat.checkSelfPermission(
                    activity!!,
                    s!!
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }

    open fun hasPermission(permission: String): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(
            activity!!,
            permission
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
