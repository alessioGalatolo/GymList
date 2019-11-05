package com.anexus.list.roomDatabase

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import com.anexus.list.Converters
import com.anexus.list.Exercise
import com.anexus.list.Program
import com.anexus.list.Session

@Database(entities = [Program::class/*, Session::class, Exercise::class*/], version = 1)
@TypeConverters(Converters::class)
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