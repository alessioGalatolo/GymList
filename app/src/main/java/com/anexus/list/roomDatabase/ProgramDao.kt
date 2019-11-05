package com.anexus.list.roomDatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.anexus.list.Program
import com.anexus.list.Session

@Dao
interface ProgramDao {

    @Query("SELECT * from Program")
    fun getAll(): List<Program>

    @Query("UPDATE Program SET sessions = :sessions WHERE id = :id")
    fun updateSessions(sessions: ArrayList<Session>, id: Int): Int

    @Insert(onConflict = REPLACE)
    fun insert(program: Program)

    @Query("DELETE from Program")
    fun deleteAll()
}