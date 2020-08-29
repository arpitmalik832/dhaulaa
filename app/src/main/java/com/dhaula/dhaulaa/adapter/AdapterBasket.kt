package com.dhaula.dhaulaa.adapter

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.dhaula.dhaulaa.BasketInterface
import com.dhaula.dhaulaa.R
import com.dhaula.dhaulaa.model.MBasketItem
import com.dhaula.dhaulaa.navigator.OnClick
import com.dhaula.dhaulaa.utils.AppConstant
import com.dhaula.dhaulaa.utils.PreferencesUtils
import com.dhaula.dhaulaa.utils.UIUtils
import com.dhaula.dhaulaa.viewModel.VMHome

class AdapterBasket (
    var context: Context?,
    var listener: OnClick<MBasketItem>?,
    var data:ArrayList<MBasketItem>?,
    var basketInterface : BasketInterface,
    var viewModel:VMHome
): RecyclerView.Adapter<AdapterBasket.ViewHolder>() {

    var priceValue = 0
    var price : String? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var tvPrice : AppCompatTextView = itemView.findViewById(R.id.tv_price)
        var tvMaxPrice : AppCompatTextView = itemView.findViewById(R.id.tv_max_price)
        var tvTitle : AppCompatTextView = itemView.findViewById(R.id.tv_title)
        var etQty : AppCompatTextView = itemView.findViewById(R.id.et_qty)
        var tvRemove : AppCompatTextView = itemView.findViewById(R.id.tv_remove)
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
        val tvPriceText = "Price Rs ${data?.get(holder.adapterPosition)?.price}"
        holder.tvPrice.text = tvPriceText
        val tvMaxPriceText = "Rs ${data?.get(holder.adapterPosition)?.price!!.toInt()*data?.get(holder.adapterPosition)?.quantity!!.removeSuffix(
                if(data?.get(holder.adapterPosition)?.quantity!!.endsWith(" kg"))
                    " kg"
                else
                    " Kg"
        ).toInt()}"
        holder.tvMaxPrice.text = tvMaxPriceText
        holder.tvMaxPrice.apply {
            text = tvMaxPriceText
            paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        }
        holder.tvRemove.setOnClickListener{
            PreferencesUtils.getString(AppConstant.USER_ID)?.let { it1 ->
                viewModel.removeFromBasket(it1,
                        data?.get(holder.adapterPosition)?.id.toString())
            }
            data?.removeAt(holder.adapterPosition)
            holder.tvMaxPrice.text = "Rs 0"
            if(data?.isEmpty()!!) {
                basketInterface.listIsEmpty()
            }
            notifyDataSetChanged()
        }

        if(position == 0) {
            priceValue = 0
        }
        price = holder.tvMaxPrice.text.substring(3)
        priceValue+=price!!.toInt()
        basketInterface.updateAmount(priceValue)
    }


}