package com.dhaula.dhaulaa.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.dhaula.dhaulaa.R
import com.dhaula.dhaulaa.model.MBasketItem
import com.dhaula.dhaulaa.navigator.OnClick
import com.dhaula.dhaulaa.utils.UIUtils
import com.dhaula.dhaulaa.viewModel.VMHome

class AdapterOrderItem (
        var context: Context?,
        var listener: OnClick<MBasketItem>?,
        var data:ArrayList<MBasketItem>?,
        var viewModel:VMHome
): RecyclerView.Adapter<AdapterOrderItem.ViewHolder>() {

    var price : String? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var tvPrice : AppCompatTextView = itemView.findViewById(R.id.tv_price)
        var tvMaxPrice : AppCompatTextView = itemView.findViewById(R.id.tv_max_price)
        var tvTitle : AppCompatTextView = itemView.findViewById(R.id.tv_title)
        var etQty : AppCompatTextView = itemView.findViewById(R.id.et_qty)
        val tvRemove: AppCompatTextView = itemView.findViewById(R.id.tv_remove)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_basket_data,parent,false))
    }

    override fun getItemCount(): Int {
        return data?.size?:0
    }

    fun setDataList(data: ArrayList<MBasketItem>){
        this.data = data
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        UIUtils.loadImage(context, data?.get(holder.adapterPosition)?.image,
                holder.itemView.findViewById<AppCompatImageView>(R.id.iv_image),0)

        holder.tvTitle.text = data?.get(holder.adapterPosition)?.name
        holder.etQty.text = data?.get(holder.adapterPosition)?.quantity
        holder.tvPrice.visibility = View.GONE
        val tvMaxPriceText = "Rs ${data?.get(holder.adapterPosition)?.price!!.toInt()*data?.get(holder.adapterPosition)?.quantity!!.removeSuffix(
                if(data?.get(holder.adapterPosition)?.quantity!!.endsWith(" kg"))
                    " kg"
                else
                    " Kg"
        ).toInt()}"
        holder.tvMaxPrice.text = tvMaxPriceText
        holder.tvRemove.visibility = View.GONE
    }


}