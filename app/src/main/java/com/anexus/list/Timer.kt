package com.anexus.list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.SystemClock
import android.util.Log
import android.widget.Chronometer
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_timer.*
import kotlin.concurrent.timer


class Timer : AppCompatActivity() {

    val exBis: ArrayList<Exercise> = ArrayList()

    var count = 0

    private var timer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)
    }
//        exBis.addAll(Data.exList)
//        val startAt = SystemClock.uptimeMillis() //used in summary as time passed
//
//
//        if(exBis.isNotEmpty())
//            exerciseOutput.text = getString(R.string.exercise_output_timer_activity,
//                    exBis[count].name, exBis[count].sets, exBis[count].reps)
//        else
//            Log.e("idk", "exBis is empty, this shouldn't happen")
//
//        testNext.setOnClickListener {
//            summaryStart(startAt)
//        }
//
//        continueBtn.setOnClickListener {
//            if(continueBtn.text.toString() == "pause") {
//                timer?.cancel()
//                continueBtn.text = "play"
//            }else {
//                continueBtn.text = "pause"
//                if(timer_output.text.toString() == "seconds remaining: 0" || timer_output.text.toString().isEmpty())
//                    if (count < exBis.size - 1) {
//
//                        st(exBis[count].rest * 1000L)
//                    }else{
//                        summaryStart(startAt)
//                    }
//                else
//                        st(timer_output.text.toString().substring(timer_output.text.toString().indexOf(':') + 1).toLong())
//
//            }
//        }
//        continueBtn.performClick()
//    }
//
//    private fun summaryStart(startAt: Long) {
//        val intent = Intent(this, Summary::class.java)
//        intent.putExtra(TIME_SPENT_EXTRA,SystemClock.uptimeMillis() - startAt)
//        intent.putExtra(NEGATIVE_EX_EXTRA, exBis.toString())
//        startActivity(intent)
//    }
//
//    fun st(mi: Long){
//
//        timer = object : CountDownTimer(mi, 1000) {
//
//            override fun onTick(millisUntilFinished: Long) {
//                timer_output.text = "seconds remaining: ${millisUntilFinished / 1000}"
//            }
//
//            override fun onFinish() {
//                if (--exBis[count].sets <= 0) {
//                    exBis.removeAt(count)
//                }
//                if(count < exBis.size - 1)
//                    exerciseOutput.text = getString(R.string.exercise_output_timer_activity,
//                            exBis[count].name, exBis[count].sets, exBis[count].reps)
////                            "${exBis[count].name}\t\tsets:${exBis[count].sets}\t\treps:${exBis[count].reps}"
//            }
//
//
//        }.start()
//    }
//
//    fun cicleEx(){
////        exBis[count].sets--
//        if(count < exBis.size - 1) {
//            if (--exBis[count].sets == 0) {
//                exBis.removeAt(count)
//            }
//            exerciseOutput.text = getString(R.string.exercise_output_timer_activity,
//                    exBis[count].name, exBis[count].sets, exBis[count].reps)
//            st(exBis[count].rest * 1000L)
//        }
//    }
}
