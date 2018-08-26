package com.anexus.list

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.ColumnInfo



@Entity
data class Program(@PrimaryKey(autoGenerate = true) var id: Int?,
                   @ColumnInfo val name: String)