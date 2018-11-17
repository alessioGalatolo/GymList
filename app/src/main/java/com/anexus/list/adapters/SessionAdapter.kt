package com.anexus.list.adapters

import android.support.v4.widget.TextViewCompat
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.anexus.list.R
import kotlinx.android.synthetic.main.session_list_item.view.*
import java.util.*
import android.support.design.widget.CoordinatorLayout.Behavior.setTag



class SessionListAdapter(val sessionList: List<String>, val startAddEx: (View) -> Unit): RecyclerView.Adapter<SessionListAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, id: Int): Holder {
        return Holder(LayoutInflater.from(parent.context)
                .inflate(R.layout.session_list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return sessionList.count()
    }

    override fun onBindViewHolder(holder: Holder, pos: Int) {
        val image = holder.itemView.sessionIcon
        val name = holder.itemView.sessionName
        TextViewCompat.setAutoSizeTextTypeWithDefaults(name, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM)
        image.setOnClickListener {
            var integer = image.tag
            integer = integer ?: 0

            when (integer) {
                R.drawable.lower_body -> {
                    image.setImageResource(R.drawable.full_body)
                    image.tag = R.drawable.full_body
                }
                R.drawable.upper_body -> {
                    image.setImageResource(R.drawable.lower_body)
                    image.tag = R.drawable.lower_body
                }
                else -> {
                    image.setImageResource(R.drawable.upper_body)
                    image.tag = R.drawable.upper_body
                }
            }
        }
        name.setOnClickListener(startAddEx)
        name.text = sessionList[pos]

    }

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView){

    }
}