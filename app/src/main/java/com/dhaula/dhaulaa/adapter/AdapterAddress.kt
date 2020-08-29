package com.dhaula.dhaulaa.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.dhaula.dhaulaa.R
import com.dhaula.dhaulaa.model.MAddress
import com.dhaula.dhaulaa.navigator.OnClick
import com.dhaula.dhaulaa.utils.Action
import com.dhaula.dhaulaa.utils.UIUtils

class AdapterAddress  (
    var context: Context?,
    var listener: OnClick<MAddress>?,
    var data:ArrayList<MAddress>?
): RecyclerView.Adapter<AdapterAddress.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_address,parent,false))
    }

    override fun getItemCount(): Int {
        return data?.size?:0
    }

    fun setDataList(data : ArrayList<MAddress>){
        this.data = data
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.findViewById<AppCompatTextView>(R.id.tv_name).text = data?.get(holder.adapterPosition)?.name?:""
        holder.itemView.findViewById<AppCompatTextView>(R.id.tv_phone).text = data?.get(holder.adapterPosition)?.number?:""
        holder.itemView.findViewById<AppCompatTextView>(R.id.tv_address).text = data?.get(holder.adapterPosition)?.address ?:""
        holder.itemView.findViewById<AppCompatCheckBox>(R.id.cb_selection).isChecked = Action.getAddressId() == data?.get(holder.adapterPosition)?.id

        holder.itemView.setOnClickListener {
            data?.get(holder.adapterPosition)?.let { it1 -> listener?.onClick(it1,holder.adapterPosition,holder.itemView) }
        }
        holder.itemView.findViewById<AppCompatTextView>(R.id.tv_edit).setOnClickListener {
            data?.get(holder.adapterPosition)?.let { it1 -> listener?.onClick(it1,-1,holder.itemView) }
        }
    }


}