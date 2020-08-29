package com.dhaula.dhaulaa.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.dhaula.dhaulaa.R
import com.dhaula.dhaulaa.model.MNews
import com.dhaula.dhaulaa.navigator.OnClick
import com.dhaula.dhaulaa.utils.UIUtils

class AdapterNews (
    var context: Context?,
    var listener: OnClick<MNews>?,
    var data:ArrayList<MNews>?
): RecyclerView.Adapter<AdapterNews.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_news,parent,false))
    }

    override fun getItemCount(): Int {
        return data?.size?:0
    }

    fun setDataList(data : ArrayList<MNews>){
        this.data = data
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        UIUtils.loadImage(context, data?.get(holder.adapterPosition)?.image,
            holder.itemView.findViewById<AppCompatImageView>(R.id.iv_image),0)

        holder.itemView.findViewById<AppCompatImageView>(R.id.iv_image).setOnClickListener {
            data?.get(holder.adapterPosition)?.let { it1 -> listener?.onClick(it1,holder.adapterPosition,holder.itemView.findViewById<AppCompatImageView>(
                R.id.iv_image)) }
        }

    }


}