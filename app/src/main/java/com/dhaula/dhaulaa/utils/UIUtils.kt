package com.dhaula.dhaulaa.utils

import android.content.Context
import android.text.TextUtils
import android.util.Patterns
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.dhaula.dhaulaa.R
import com.dhaula.dhaulaa.model.MMenu
import java.util.regex.Matcher
import java.util.regex.Pattern

object UIUtils {

    const val SELECT_SHIPPING_ADDRESS_CODE = 100

    private fun isValidMail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidMobile(mobileNo: String?): Boolean {
        if (TextUtils.isEmpty(mobileNo)) return false
        val p: Pattern = Pattern.compile("[6-9][0-9]{9}")
        val m: Matcher = p.matcher(mobileNo)
        return m.find() && m.group() == mobileNo
    }

    fun isValidOTP(otp: String?): Boolean {
        if (TextUtils.isEmpty(otp)) return false
        return otp?.length == 6
    }

    fun loadImage(context: Context?, url: String?, view: ImageView?, default: Int) {
        if (view == null) return
        context?.let {
            Glide.with(it)
                .load(if (!TextUtils.isEmpty(url)) url else "Chandan Sharma's Unique invalid link.")
                .placeholder(if (default != 0) default else R.color.colorLightGray)
                .error(if (default != 0) default else R.color.colorLightGray)
                .into(view)
        }
    }

    fun loadImage(context: Context?, resource: Int, view: ImageView?, default: Int) {
        if (view == null) return
        context?.let {
            Glide.with(it).load(resource)
                .placeholder(if (default != 0) default else R.color.colorLightGray)
                .error(if (default != 0) default else R.color.colorLightGray)
                .into(view)
        }
    }

    var image =
        "https://images.unsplash.com/photo-1590165482129-1b8b27698780?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=282&q=80"
    var image2 =
        "https://images.unsplash.com/photo-1518977822534-7049a61ee0c2?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=750&q=80"

    var image5 = "https://st1.thehealthsite.com/wp-content/uploads/2013/09/brinjal.jpg"
    var image6 =
        "https://image.shutterstock.com/image-photo/cauliflower-green-coverage-leaves-closeup-260nw-1712974885.jpg"
    var image7 = "https://5.imimg.com/data5/PU/LG/MY-52406801/lady-finger-500x500.jpg"
    var image8 =
        "https://static.toiimg.com/thumb/73108379.cms?width=804&height=603&resizemode=4&imgsize=1532502"


    var image9 =
        "https://images.unsplash.com/photo-1560806887-1e4cd0b6cbd6?ixlib=rb-1.2.1&w=1000&q=80"
    var image10 =
        "https://media.nationalgeographic.org/assets/photos/218/954/a4b922dc-def3-4a5d-a6e0-ab5dce621fc2.jpg"
    var image11 = "https://cdn.mos.cms.futurecdn.net/UaBq5LGpJQd3DDo6ve2dFW.jpg"
    var image12 = "https://agfstorage.blob.core.windows.net/misc/FP_com/2020/01/24/Aman.jpg"


    var image13 = "https://www.liveeatlearn.com/wp-content/uploads/2020/03/foods-boost-immunity.jpg"
    var image15 =
        "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRXZmm3X6RiyORNDJjndIBYRwvUKe_uYtMVmg&usqp=CAU"
    var image14 =
        "https://i.guim.co.uk/img/static/sys-images/Guardian/Pix/pictures/2014/7/11/1405082931442/Organic-apples-and-pears-011.jpg?width=1200&height=630&quality=85&auto=format&fit=crop&overlay-align=bottom%2Cleft&overlay-width=100p&overlay-base64=L2ltZy9zdGF0aWMvb3ZlcmxheXMvdGctYWdlLTIwMTQucG5n&enable=upscale&s=504a8b20775b1e4c6628c4b83b6382f4"
    var image16 =
        "https://www.telegraph.co.uk/content/dam/news/2019/06/06/TELEMMGLPICT000158858165_trans_NvBQzQNjv4BqpVlberWd9EgFPZtcLiMQfyf2A9a6I9YchsjMeADBa08.jpeg?imwidth=450"
    var image17 =
        "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQi0Vi0vjzP98wUMpwMESobHUEpIBvEJTKB0w&usqp=CAU"


    var image3 =
        "https://images.unsplash.com/photo-1595080898559-95ca2c9bb0a5?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=751&q=80"
    var image4 = "https://cdn.pixabay.com/photo/2015/02/24/15/41/dog-647528__340.jpg"

    fun getOrderTabs(): ArrayList<MMenu> {
        val menu = ArrayList<MMenu>()
        menu.add(MMenu(0, "All"))
        menu.add(MMenu(1, "Processing"))
        menu.add(MMenu(2, "delivered"))
        menu.add(MMenu(3, "cancelled"))
        return menu
    }

    private fun isShow(value: String?): Int {
        return if (TextUtils.isEmpty(value)) View.VISIBLE else View.GONE
    }

    fun handleLoader(value: String?, view: View?) {
        view?.visibility = isShow(value)
    }

    fun handleLoginMenu(menuItem: MenuItem?) {
        menuItem?.isVisible = !Action.isLogin()
    }

    fun handleNotLoginMenu(menuItem: MenuItem?) {
        menuItem?.isVisible = Action.isLogin()
    }

}