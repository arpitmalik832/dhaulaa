package com.dhaula.dhaulaa.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.dhaula.dhaulaa.R
import com.dhaula.dhaulaa.model.MOrder
import com.dhaula.dhaulaa.navigator.OnClick

class AdapterOrder (
    var context: Context?,
    var listener: OnClick<MOrder>?,
    var data:ArrayList<MOrder>?
): RecyclerView.Adapter<AdapterOrder.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_order,parent,false))
    }

    override fun getItemCount(): Int {
        return data?.size?:0
    }

    fun setDataList(data : ArrayList<MOrder>){
        this.data = data
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.findViewById<AppCompatTextView>(R.id.tv_order).text = "Order No: ${data?.get(position)?.order_id}"
        holder.itemView.findViewById<AppCompatTextView>(R.id.tv_date).text = data?.get(position)?.order_date
        holder.itemView.findViewById<AppCompatTextView>(R.id.tv_total_amount).text = data?.get(position)?.grand_price
        holder.itemView.findViewById<AppCompatTextView>(R.id.tv_status).text = data?.get(position)?.status

        holder.itemView.findViewById<AppCompatButton>(R.id.bt_details).setOnClickListener {
            data?.get(holder.adapterPosition)?.let { it1 -> listener?.onClick(it1,holder.adapterPosition,holder.itemView.findViewById<AppCompatButton>(
                R.id.bt_details)) }
        }

        holder.itemView.findViewById<AppCompatButton>(R.id.bt_cancel).visibility = if(data?.get(holder.adapterPosition)?.status?.equals("1",true) == true){
            holder.itemView.findViewById<AppCompatButton>(R.id.bt_cancel).setOnClickListener {
                data?.get(holder.adapterPosition)?.let { it1 -> listener?.onClick(it1,-4,holder.itemView.findViewById<AppCompatButton>(
                    R.id.bt_cancel)) }
            }
           View.VISIBLE
        }else{
            View.GONE
        }

    }


}