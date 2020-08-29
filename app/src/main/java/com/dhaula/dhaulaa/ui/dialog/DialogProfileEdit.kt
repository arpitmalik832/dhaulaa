package com.dhaula.dhaulaa.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.DialogFragment
import com.dhaula.dhaulaa.R
import com.dhaula.dhaulaa.model.MUser
import com.dhaula.dhaulaa.navigator.NavigatorDialog
import com.dhaula.dhaulaa.utils.Action

class DialogProfileEdit(var listener : NavigatorDialog<MUser>)  : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.dialog_edit_profile, container, false)
        val data = Action.getUser()
        view.findViewById<AppCompatEditText>(R.id.et_username).setText(data.name)
        view.findViewById<AppCompatEditText>(R.id.et_email).setText(data.email)
        view.findViewById<AppCompatButton>(R.id.btn_save).setOnClickListener {
            updateProfile(view)
        }
        return view
    }

    private fun updateProfile(view: View) {
        val email = view.findViewById<AppCompatEditText>(R.id.et_email).text?:""
        val userName = view.findViewById<AppCompatEditText>(R.id.et_username).text?:""
        if(TextUtils.isEmpty(userName)){
            Toast.makeText(dialog?.context,"Username can't be empty",Toast.LENGTH_SHORT).show()
            return
        }
        val data = MUser()
        data.email = email.toString()
        data.name = userName.toString()
        listener.positiveClick(data,200)
        dismiss()
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