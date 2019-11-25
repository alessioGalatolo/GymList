package com.anexus.list

import com.anexus.list.roomDatabase.DatabaseManager
import com.anexus.list.roomDatabase.ProgramDao

object Data {
    val exercises: ArrayList<Exercise> = ArrayList()
    val exName: ArrayList<String> = ArrayList()
    val programs = ArrayList<Program>()
    var currentProgram: Program? = null
    val sessions = ArrayList<Session>()
    var currentSession: Session? = null
    val sessionList = listOf("Upper Body", "Lower Body", "Full Body")
    lateinit var programDb: ProgramDao
    const val CHANNEL_ID = "1000"
}