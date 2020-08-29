package com.dhaula.dhaulaa.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.dhaula.dhaulaa.R
import com.dhaula.dhaulaa.model.MCategory
import com.dhaula.dhaulaa.navigator.OnClick
import com.dhaula.dhaulaa.utils.UIUtils

class AdapterCategory (
    var context: Context?,
    var listener: OnClick<MCategory>?,
    var data:ArrayList<MCategory>?
): RecyclerView.Adapter<AdapterCategory.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_home_product,parent,false))
    }

    override fun getItemCount(): Int {
        return data?.size?:0
    }

    fun setDataList(data : ArrayList<MCategory>){
        this.data = data
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        UIUtils.loadImage(context, data?.get(holder.adapterPosition)?.image,
            holder.itemView.findViewById<AppCompatImageView>(R.id.iv_product),0)

        holder.itemView.findViewById<AppCompatTextView>(R.id.tv_cat_name).text = data?.get(holder.adapterPosition)?.name ?:""

        holder.itemView.findViewById<AppCompatImageView>(R.id.iv_product).setOnClickListener {
            data?.get(holder.adapterPosition)?.let { it1 -> listener?.onClick(it1,holder.adapterPosition,holder.itemView.findViewById<AppCompatImageView>(
                R.id.iv_product)) }
        }

    }


}