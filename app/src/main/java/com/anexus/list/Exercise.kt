package com.anexus.list

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Exercise(@PrimaryKey val name: String,
                    @ColumnInfo val sets: Int,
                    @ColumnInfo val reps: Int,
                    @ColumnInfo val rest: Int)