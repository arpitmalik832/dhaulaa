package com.dhaula.dhaulaa.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Paint
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.dhaula.dhaulaa.R
import com.dhaula.dhaulaa.model.MProduct
import com.dhaula.dhaulaa.navigator.OnClick
import com.dhaula.dhaulaa.utils.UIUtils
import com.dhaula.dhaulaa.viewModel.VMHome

class AdapterPackages(
    var context: Context?,
    var listener: OnClick<MProduct>?,
    var data: ArrayList<MProduct>?,
    var viewModel: VMHome
) : RecyclerView.Adapter<AdapterPackages.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_package, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    fun setDataList(data: ArrayList<MProduct>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        UIUtils.loadImage(
            context, data?.get(holder.adapterPosition)?.image,
            holder.itemView.findViewById<AppCompatImageView>(R.id.iv_image), 0
        )

        holder.itemView.findViewById<AppCompatTextView>(R.id.tv_pck_name).text =
            data?.get(holder.adapterPosition)?.name
        holder.itemView.findViewById<AppCompatTextView>(R.id.tv_old_price).apply {
            text = ("Price Rs. " + (data?.get(holder.adapterPosition)?.offerPrice ?: 0).toString())
        }

        holder.itemView.findViewById<AppCompatTextView>(R.id.tv_price).apply {
            text = ("Rs. " + (data?.get(holder.adapterPosition)?.price ?: 0).toString())
            paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        }

        holder.itemView.findViewById<AppCompatTextView>(R.id.tv_price_off).text =
            ("${(data?.get(holder.adapterPosition)?.discount ?: 0)} % off")

        holder.itemView.findViewById<AppCompatButton>(R.id.btn_buy).isEnabled =
            data?.get(holder.adapterPosition)?.in_stock != 0

        holder.itemView.findViewById<AppCompatEditText>(R.id.et_qty).isEnabled =
            data?.get(holder.adapterPosition)?.in_stock != 0

        holder.itemView.findViewById<AppCompatButton>(R.id.btn_buy).text =
            if (data?.get(holder.adapterPosition)?.in_stock != 0) {
                "Add To Basket"
            } else {
                "Out of Stock"
            }

        if (data?.get(holder.adapterPosition)?.in_stock == 0) {
            context?.let { ContextCompat.getColor(it, R.color.colorDarkGray) }?.let {
                holder.itemView.findViewById<AppCompatButton>(R.id.btn_buy).backgroundTintList =
                    ColorStateList.valueOf(it)
            }
        } else {
            context?.let { ContextCompat.getColor(it, R.color.colorPrimary) }?.let {
                holder.itemView.findViewById<AppCompatButton>(R.id.btn_buy).backgroundTintList =
                    ColorStateList.valueOf(it)
            }
        }

        holder.itemView.findViewById<AppCompatButton>(R.id.btn_buy).setOnClickListener {
            data?.get(holder.adapterPosition)?.let { it1 ->
                val data = holder.itemView.findViewById<AppCompatEditText>(R.id.et_qty).text
                if (TextUtils.isEmpty(data)) {
                    Toast.makeText(context,"Please enter Qty", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                } else if (data?.equals("0")==true) {
                    Toast.makeText(context,"Qty Can't 0 or less", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                it1.qty =
                    if (holder.itemView.findViewById<AppCompatEditText>(R.id.et_qty).text!!.isNotEmpty()) {
                        holder.itemView.findViewById<AppCompatEditText>(R.id.et_qty).text.toString()
                    } else {
                        "3 Kg"
                    }
                listener?.onClick(
                    it1, holder.adapterPosition, holder.itemView.findViewById<AppCompatButton>(
                        R.id.btn_buy
                    )
                )
            }
        }

        holder.itemView.findViewById<AppCompatButton>(R.id.btn_view).setOnClickListener {
            data?.get(holder.adapterPosition)?.let { it1 ->
                listener?.onClick(
                    it1, -1, holder.itemView.findViewById<AppCompatButton>(
                        R.id.btn_view
                    )
                )
            }
        }

    }


}