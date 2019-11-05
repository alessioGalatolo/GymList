package com.anexus.list.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.anexus.list.R
import com.anexus.list.Song

class SongAdapter(private val songs: ArrayList<Song>): RecyclerView.Adapter<SongAdapter.SongHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongHolder {
        return SongHolder(LayoutInflater.from(parent.context).inflate(R.layout.song_list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return songs.count()
    }

    override fun onBindViewHolder(holder: SongHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.listSongName).text = songs[position].title
    }


    inner class SongHolder(itemView: View): RecyclerView.ViewHolder(itemView)

}