package com.dhaula.dhaulaa.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.dhaula.dhaulaa.R
import com.dhaula.dhaulaa.model.MMenu
import com.dhaula.dhaulaa.navigator.NavigatorTab
import com.dhaula.dhaulaa.navigator.OnClick

class AdapterTab(
    var context: Context?,
    var listener: NavigatorTab?,
    var data: ArrayList<MMenu>?
) : RecyclerView.Adapter<AdapterTab.ViewHolder>() {

    private var highLightedView:View?=null
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.row_tab, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    fun setDataList(data: ArrayList<MMenu>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.findViewById<AppCompatTextView>(R.id.tv_title).text = data?.get(holder.adapterPosition)?.name

        holder.itemView.setOnClickListener {
            highLightedView?.isSelected = false
            highLightedView = holder.itemView
            highLightedView?.isSelected = true
            data?.get(holder.adapterPosition)?.let { it1 -> listener?.onTabClick(it1,holder.itemView) }
        }

        if(holder.adapterPosition==0){
            holder.itemView.performClick()
        }
    }


}