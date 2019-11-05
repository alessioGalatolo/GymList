package com.anexus.list

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey


data class Session(val name: String,
                   val muscleTarget: Muscle?,
                   val exercises: ArrayList<Exercise>)