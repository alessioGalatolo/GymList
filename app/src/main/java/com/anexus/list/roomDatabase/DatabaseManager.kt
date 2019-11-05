package com.anexus.list.roomDatabase

import android.content.Context
import com.anexus.list.Program
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DatabaseManager(context: Context) {

    private val programDatabase: ProgramDatabase = ProgramDatabase.getInstance(context)!!


    fun insertProgram(program: Program){
        GlobalScope.launch{
            programDatabase.programDao().insert(program)
        }
    }

    fun getPrograms(callbackFun: (ArrayList<Program>) -> Unit){
        GlobalScope.launch{
            callbackFun(programDatabase.programDao().getAll() as ArrayList<Program>)
        }
    }

    fun blockingGetPrograms(): List<Program> {
        return programDatabase.programDao().getAll()
    }
}