package com.anexus.list.roomDatabase

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.anexus.list.Program

@Database(entities = [Program::class], version = 1)
abstract class ProgramDatabase: RoomDatabase() {
    abstract fun programDao(): ProgramDao

    companion object {
        private var INSTANCE: ProgramDatabase? = null

        fun getInstance(context: Context): ProgramDatabase? {
            if (INSTANCE == null) {
                synchronized(ProgramDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            ProgramDatabase::class.java, "program.db")
                            .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }

}