package com.anexus.list

import androidx.room.TypeConverter
import com.google.gson.Gson

//class used to convert type to store and retrieve from room database
class Converters {

    @TypeConverter
    fun sessionsToJson(value: ArrayList<Session>?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToSessions(value: String): ArrayList<Session>? {

        val objects = Gson().fromJson(value, Array<Session>::class.java) as Array<Session>
        return objects.toCollection(ArrayList())
    }
}