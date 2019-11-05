package com.anexus.list.adapters

import androidx.core.widget.TextViewCompat
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.anexus.list.Exercise
import com.anexus.list.R
import kotlinx.android.synthetic.main.ex_list_item.view.*

class ListAdapter(private val itemList: ArrayList<Exercise>): RecyclerView.Adapter<ListAdapter.itemVH>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): itemVH {

        return itemVH(LayoutInflater.from(p0.context).inflate(R.layout.ex_list_item, p0, false))
    }

    override fun getItemCount(): Int {
        return itemList.count()
    }

    override fun onBindViewHolder(p0: itemVH, p1: Int) {
        val name = p0.itemView.exercise_tv
        val rs = p0.itemView.rs_tv
        val rest = p0.itemView.restTv
        TextViewCompat.setAutoSizeTextTypeWithDefaults(name, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM)
        TextViewCompat.setAutoSizeTextTypeWithDefaults(rs, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM)
        TextViewCompat.setAutoSizeTextTypeWithDefaults(rest, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM)

        val (nameEx, sEx,rEx, restEx) = itemList[p1]
        name.text = nameEx
        rs.text = "${sEx}x$rEx"
        if(restEx % 60 == 0)
            rest.text = "${restEx / 60}:00"
        else
            rest.text = "${restEx / 60}:${restEx % 60}"
    }

    inner class itemVH(itemView: View) : RecyclerView.ViewHolder(itemView)


}