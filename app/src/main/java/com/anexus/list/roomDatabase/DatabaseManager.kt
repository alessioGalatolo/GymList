package com.anexus.list.roomDatabase

import android.content.Context
import com.anexus.list.Program
class DatabaseManager(context: Context) {

    companion object{
        private lateinit var programDatabase: ProgramDatabase
    }

    init {
        programDatabase = ProgramDatabase.getInstance(context)!!
    }



    fun insertProgram(program: Program){
        Runnable{
            programDatabase.programDao().insert(program)
        }.run()
    }
}