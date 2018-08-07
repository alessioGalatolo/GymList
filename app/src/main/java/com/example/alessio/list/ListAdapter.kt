package com.example.alessio.list

import android.support.v4.widget.TextViewCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class ListAdapter(private val itemList: ArrayList<Ex>): RecyclerView.Adapter<ListAdapter.itemVH>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): itemVH{

        return itemVH(LayoutInflater.from(p0.context).inflate(R.layout.list_item, p0, false))
    }

    override fun getItemCount(): Int {
        return itemList.count()
    }

    override fun onBindViewHolder(p0:itemVH, p1: Int) {
        val name = p0.itemView.findViewById<TextView>(R.id.exercise_tv)
        val rs = p0.itemView.findViewById<TextView>(R.id.rs_tv)
        val rest = p0.itemView.findViewById<TextView>(R.id.restTv)
        TextViewCompat.setAutoSizeTextTypeWithDefaults(name, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM)
        TextViewCompat.setAutoSizeTextTypeWithDefaults(rs, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM)
        TextViewCompat.setAutoSizeTextTypeWithDefaults(rest, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM)

        val (nameEx, sEx,rEx, restEx) = itemList[p1]
        name.text = nameEx
        rs.text = "${sEx}x$rEx"
        rest.text = "${restEx / 60}:${restEx % 60}"
    }

    inner class itemVH(itemView: View?) : RecyclerView.ViewHolder(itemView)


}