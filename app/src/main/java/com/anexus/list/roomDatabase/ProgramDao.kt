package com.anexus.list.roomDatabase

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.anexus.list.Program

@Dao
interface ProgramDao {
    @Query("SELECT * from Program")
    fun getAll(): List<Program>

    @Insert(onConflict = REPLACE)
    fun insert(program: Program)

    @Query("DELETE from Program")
    fun deleteAll()
}