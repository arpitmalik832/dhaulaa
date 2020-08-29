package com.dhaula.dhaulaa.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatRatingBar
import androidx.fragment.app.DialogFragment
import com.dhaula.dhaulaa.R
import com.dhaula.dhaulaa.ui.activity.ActivityHome
import com.dhaula.dhaulaa.utils.Action

class DialogRating : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layView: View = inflater.inflate(R.layout.dialog_rating, container, false)
        layView.findViewById<AppCompatButton>(R.id.btn_submit)?.setOnClickListener {
            val comment = layView.findViewById<AppCompatEditText>(R.id.et_comment)?.text?:""
            val rating:Float = layView.findViewById<AppCompatRatingBar>(R.id.rb_stars)?.rating?:0f
            if(rating==0f){
                Toast.makeText(dialog?.context,"Please give a rating",Toast.LENGTH_SHORT).show()
            }else{
                if (activity is ActivityHome) {
                    val map = HashMap<String,Any>()
                    map["rating"] = rating
                    map["user_id"] = Action.getUserId()
                    map["comment"] = comment
                    (activity as ActivityHome).addRating(map)
                    dialog?.dismiss()
                }
            }
        }

        return layView
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        isCancelable = true
        dialog?.setCanceledOnTouchOutside(true)
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val inset = InsetDrawable(ColorDrawable(Color.TRANSPARENT), 42)
        dialog?.window?.setBackgroundDrawable(inset)
    }

}