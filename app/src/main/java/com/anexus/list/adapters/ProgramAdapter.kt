package com.anexus.list.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.anexus.list.Program
import com.anexus.list.R
import kotlinx.android.synthetic.main.list_program.view.*

class ProgramAdapter(private val programs: ArrayList<Program>, private val viewProgramMethod: (Program) -> Unit): RecyclerView.Adapter<ProgramAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, id: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.list_program, parent, false))
    }

    override fun getItemCount(): Int {
        return programs.count()
    }

    override fun onBindViewHolder(holder: Holder, pos: Int) {
        holder.itemView.programNameTv.text = programs[pos].name
        holder.itemView.setOnClickListener {
            viewProgramMethod(programs[pos])
        }
    }


    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView)
}