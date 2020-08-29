package com.dhaula.dhaulaa.ui.activity

import android.content.Intent
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dhaula.dhaulaa.R
import com.dhaula.dhaulaa.base.BaseActivity
import com.dhaula.dhaulaa.databinding.ActivityHomeBinding
import com.dhaula.dhaulaa.model.MUser
import com.dhaula.dhaulaa.ui.fragment.*
import com.dhaula.dhaulaa.utils.Action.isLogin
import com.dhaula.dhaulaa.utils.AppConstant
import com.dhaula.dhaulaa.utils.PreferencesUtils
import com.dhaula.dhaulaa.utils.UIUtils
import com.dhaula.dhaulaa.viewModel.VMHome
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson


class ActivityHome  : BaseActivity<ActivityHomeBinding>(), NavigationView.OnNavigationItemSelectedListener,
    BottomNavigationView.OnNavigationItemSelectedListener {

    private var t: ActionBarDrawerToggle? = null

    private var binding : ActivityHomeBinding? =null
    private var viewModel : VMHome? =null

    override val layoutId: Int
        get() = R.layout.activity_home

    override fun initViews() {
        binding = getViewDataBinding()
        viewModel = ViewModelProvider(this).get(VMHome::class.java)
        binding?.viewModel = viewModel
        viewModel?.setNavigator(this)
        binding?.lifecycleOwner = this
        initCustomToolbar()
        if (!isLogin()){
            binding?.bottomNavigation?.menu?.removeItem(R.id.navigation_basket)
            binding?.bottomNavigation?.menu?.removeItem(R.id.navigation_profile)
        }
    }

    override fun showNavigationIcon(): Boolean {
        return true
    }

    override fun setToolbarName(): String {
        return "Home"
    }

    override fun initListeners() {
        binding?.nvHome?.setNavigationItemSelectedListener(this)
        binding?.bottomNavigation?.setOnNavigationItemSelectedListener(this)
    }

    fun openDrawer(){
        binding?.dlHome?.openDrawer(Gravity.LEFT)

        if(isLogin()) {
            val userData: MUser? = Gson().fromJson(PreferencesUtils.getString(AppConstant.USER_DATA)?:"", MUser::class.java)
            findViewById<AppCompatTextView>(R.id.header_name).text = userData?.name
        }else {
            findViewById<AppCompatTextView>(R.id.header_name).text = "Login/Register"
        }

        val headerDrawer : ConstraintLayout = findViewById(R.id.header_layout)

        headerDrawer.setOnClickListener{
            if(isLogin()) {
                replaceFragment(FragmentProfile(), R.id.fl_home, true)
                binding?.dlHome?.closeDrawer(Gravity.LEFT)
            }
        }

    }

    override fun bindDataWithViews() {
        binding?.toolbar?.visibility = View.GONE
        UIUtils.handleLoginMenu(binding?.nvHome?.menu?.findItem(R.id.navigation_login))
        UIUtils.handleLoginMenu(binding?.nvHome?.menu?.findItem(R.id.navigation_become_member))
        UIUtils.handleNotLoginMenu(binding?.nvHome?.menu?.findItem(R.id.navigation_orders))
        replaceFragment(FragmentHome(),R.id.fl_home,false)
        if(isLogin()) {
            addNotificationBadge()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
       when(item.itemId){
           R.id.navigation_home ->{
               binding?.toolbar?.visibility = View.GONE
               replaceFragment(FragmentHome(),R.id.fl_home,false)
           }
           R.id.navigation_news,R.id.navigation_article ->{
               replaceFragment(FragmentNews(),R.id.fl_home,false)
               binding?.toolbar?.visibility = View.VISIBLE
           }
           R.id.navigation_basket ->{
               replaceFragment(FragmentBasket(),R.id.fl_home,false)
               binding?.toolbar?.visibility = View.VISIBLE
           }
           R.id.navigation_profile ->{
               binding?.toolbar?.visibility = View.GONE
               replaceFragment(FragmentProfile(),R.id.fl_home,false)
           }
           R.id.navigation_orders ->{
               replaceFragment(FragmentMyOrder(),R.id.fl_home,false)
               binding?.toolbar?.visibility = View.VISIBLE
           }
           R.id.navigation_terms ->{
               replaceFragment(FragmentTerm(),R.id.fl_home,false)
               binding?.toolbar?.visibility = View.VISIBLE
           }
           R.id.navigation_become_member -> {
               val intent = Intent(this,ActivityAuth::class.java).apply {
               }
               intent.putExtra("fragment","register")
               startActivity(intent)
               binding?.toolbar?.visibility = View.VISIBLE
           }
           R.id.navigation_login ->{
               val intent = Intent(this,ActivityAuth::class.java).apply {
               }
               intent.putExtra("fragment","login")
               startActivity(intent)
               binding?.toolbar?.visibility = View.VISIBLE
           }
       }
        binding?.dlHome?.closeDrawer(Gravity.LEFT, true)
        return true
    }



    override fun onBackPressed() {
        val frag = supportFragmentManager.findFragmentById(R.id.fl_home)
        if((frag is FragmentMyOrder) ||
            (frag is FragmentNews) ||
            (frag is FragmentTerm) ||
            (frag is FragmentBasket) ||
            (frag is FragmentProfile)){
            binding?.bottomNavigation?.selectedItemId = R.id.navigation_home
        }else{
            super.onBackPressed()
        }
        binding?.toolbar?.visibility = View.GONE
    }


    private fun addNotificationBadge(){
//        val v = (binding?.bottomNavigation?.getChildAt(0) as BottomNavigationMenuView).getChildAt(2)
//        val itemView = v as BottomNavigationItemView
//        val badge: View = LayoutInflater.from(this).inflate(R.layout.bottom_nav_badges, itemView, true)
    }

    override fun onResume() {
        super.onResume()
        val f: Fragment? = supportFragmentManager.findFragmentById(R.id.fl_home)
        if (f is FragmentHome || f is FragmentProfile) {
            hideCustomTb()
        }else {
            showCustomTb()
        }
    }

    fun addRating(map:HashMap<String,Any>){
        viewModel?.addRating(map)
        Toast.makeText(this,"Your review has been submitted successfully",Toast.LENGTH_SHORT).show()
    }

}