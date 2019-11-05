package com.anexus.list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class Summary : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)
        val intent = intent
        val exNeg = createAL(intent.extras!!.get(NEGATIVE_EX_EXTRA).toString())
        Log.i("idk", exNeg.toString())
    }

    private fun createAL(dataString: String): ArrayList<Exercise> {
        val tmp: ArrayList<Exercise> = ArrayList()
        var i = 0
        while (i < dataString.length - 1) {
            if ((dataString[i]) == '=') {
                var x = dataString.indexOf(',', i)
                val name = dataString.substring(i + 1, x)
                i = x + 7
                x = dataString.indexOf(',', i)
                val sets = dataString.substring(i, x)
                i = x + 7
                x = dataString.indexOf(',', i)
                val reps = dataString.substring(i, x)
                i = x + 7
                x = dataString.indexOf(')', i)
                val rest = dataString.substring(i, x)
                tmp.add(Exercise(name, sets.toInt(), reps.toInt(), rest.toInt()))

            }
            i++
        }
        return tmp
    }
}
