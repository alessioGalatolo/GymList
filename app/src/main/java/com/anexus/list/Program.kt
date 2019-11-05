package com.anexus.list

import androidx.room.*

@Entity
data class Program(@PrimaryKey(autoGenerate = true) var id: Int? = 0,
                   @ColumnInfo var name: String = "Untitled Program",
                   @ColumnInfo var sessions: ArrayList<Session> = ArrayList())

