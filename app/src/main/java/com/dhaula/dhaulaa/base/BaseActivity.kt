package com.dhaula.dhaulaa.base

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.dhaula.dhaulaa.MApplication
import com.dhaula.dhaulaa.R
import com.dhaula.vegitable.navigator.NavigatorBase

abstract class BaseActivity<T : ViewDataBinding?> : AppCompatActivity(),
    NavigatorBase {
    private var toolbar: Toolbar? = null
    private var mViewDataBinding: T? = null

    private var mProgressDialog: ProgressDialog? = null

    @get:LayoutRes
    protected abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewDataBinding = DataBindingUtil.setContentView<T>(this, layoutId)
        init()
    }

    open fun getViewDataBinding(): T? {
        return mViewDataBinding
    }
    override fun onCreate(
        savedInstanceState: Bundle?,
        persistentState: PersistableBundle?
    ) {
        super.onCreate(savedInstanceState, persistentState)
    }

   override fun showLoading() {
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog(this, R.style.DialogTheme)
            mProgressDialog!!.isIndeterminate = false
            mProgressDialog!!.setCancelable(false)
            mProgressDialog!!.show()
            mProgressDialog?.setContentView(R.layout.dialog_progress)
        }
        if (!mProgressDialog!!.isShowing) {
            mProgressDialog!!.show()
        }
    }

    val isShowingProgress: Boolean
        get() = mProgressDialog != null && mProgressDialog!!.isShowing

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

    fun askForPermissions(
        permissions: Array<String>,
        permissionCode: Int
    ): Boolean {
        if (!hasPermissions(permissions)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ActivityCompat.requestPermissions(
                    this,
                    permissions,
                    permissionCode
                )
            }
        }
        return hasPermissions(permissions)
    }

    fun askForPermission(permission: String, permissionCode: Int): Boolean {
        if (!hasPermission(permission)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ActivityCompat.requestPermissions(
                    this, arrayOf(permission),
                    permissionCode
                )
            }
        }
        return hasPermission(permission)
    }

    private fun hasPermissions(permission: Array<String>): Boolean {
        for (s in permission) {
            if (ContextCompat.checkSelfPermission(
                    applicationContext,
                    s
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }

    fun hasPermission(permission: String): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(
            applicationContext,
            permission
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        onBasePermissionResult(requestCode, permissions, grantResults)
    }

    protected fun storagePermissionGrant() {}
    fun init() {
        initViews()
        initListeners()
        bindDataWithViews()
    }

    fun initCustomToolbar() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowTitleEnabled(true)
            supportActionBar?.title = setToolbarName()
            //(findViewById<View>(R.id.toolbar_title) as TextView).text = setToolbarName()
            toolbar?.setTitleTextColor(ContextCompat.getColor(this,R.color.colorPrimary))
            if (showNavigationIcon()) {
                toolbar?.navigationIcon = ContextCompat.getDrawable(this, setBackIcon())
            } else {
                toolbar?.navigationIcon = null
            }
        }
    }


    fun updateToolbarTitle(name: String?) {
        if (supportActionBar != null) {
            (findViewById<View>(R.id.toolbar) as View).visibility = View.VISIBLE
            supportActionBar?.title = name
            //(findViewById<View>(R.id.toolbar_title) as TextView).text = name
        }
    }

    fun hideCustomTb(){
        if (supportActionBar != null) {
            (findViewById<View>(R.id.toolbar) as View).visibility = View.GONE
        }
    }

    fun showCustomTb(){
        if (supportActionBar != null) {
            (findViewById<View>(R.id.toolbar) as View).visibility = View.VISIBLE
        }
    }

    open fun setToolbarName(): String {
        return "Dhauala"
    }

    open fun showNavigationIcon(): Boolean {
        return true
    }

    fun setBackIcon(): Int {
        return R.drawable.ic_arrow_back_black_24dp
    }

    abstract fun initViews()
    abstract fun initListeners()
    abstract fun bindDataWithViews()

    fun showToast(msg: String?) {
        val toast = Toast(MApplication.getContext())
        toast.duration = Toast.LENGTH_SHORT
        val inflater = MApplication.getContext()?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(
            R.layout.chandan_makes_toast,
            findViewById<View>(R.id.custom_toast_root) as ViewGroup
        )
        (view.findViewById<View>(R.id.custom_toast_tv) as TextView).text = msg
        toast.setView(view)
        toast.show()
    }

    fun hideSoftInputKeyboard() {
        val view = this.currentFocus
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (view != null && imm != null) {
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    val isNetworkAvailable: Boolean
        get() {
            val connectivityManager =
                this@BaseActivity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (connectivityManager != null) {
                val activeNetworkInfo = connectivityManager.activeNetworkInfo
                return activeNetworkInfo != null && activeNetworkInfo.isConnected
            }
            return false
        }

    fun addFragment(fragment: Fragment?, containerId: Int) {
        supportFragmentManager
            .beginTransaction()
            .add(containerId, fragment!!)
            .commitAllowingStateLoss()
    }

    fun replaceFragment(
        fragment: Fragment,
        containerId: Int,
        addToBackStack: Boolean
    ) {
        val replace =
            supportFragmentManager.beginTransaction()
                //.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
                .replace(containerId, fragment)
        if (addToBackStack) {
            replace.addToBackStack(fragment.javaClass.name)
        }
        replace.commitAllowingStateLoss()
    }

    private fun onBasePermissionResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
    }

    fun replaceFragment(
        fragment: Fragment,
        containerId: Int,
        addToBackStack: Boolean,
        clearStack: Boolean
    ) {
        if (clearStack) supportFragmentManager.popBackStack(
            null,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
        replaceFragment(fragment, containerId, addToBackStack)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.home) {
            return true
        }
        onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    fun changeTitleColor(title: String): SpannableStringBuilder {
        val foregroundColorSpan =
            ForegroundColorSpan(Color.BLACK)
        val ssBuilder = SpannableStringBuilder(title)
        ssBuilder.setSpan(foregroundColorSpan, 0, title.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        return ssBuilder
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        onBaseActivityResult(requestCode, resultCode, data)
    }

    private fun onBaseActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {}

    companion object {
        private val TAG = BaseActivity::class.java.simpleName
    }

}