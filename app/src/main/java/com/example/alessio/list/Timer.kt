package com.example.alessio.list

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_timer.*


class Timer : AppCompatActivity() {

    val exBis = Data.exList

    var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)
//        val exList: ArrayList<Ex> = intent.extras.get(EX_LIST) as ArrayList<Ex>
//        Toast.makeText(this, "${exList[0]}", Toast.LENGTH_SHORT).show()
        exBis.addAll(Data.exList)
        exerciseOutput.text = "${exBis[count].name}\t\tsets:${exBis[count].sets}\t\treps:${exBis[count].reps}"
        continueBtn.setOnClickListener {
            if(count < exBis.size - 1) {

                st(exBis[count].rest * 1000L)
            }
        }
        continueBtn.performClick()
    }

    fun st(mi: Long){
        object : CountDownTimer(mi, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                timer_output.text = "seconds remaining: ${millisUntilFinished / 1000}"
            }

            override fun onFinish() {
                if (--exBis[count].sets == 0) {
                    exBis.removeAt(count)
                }
                if(count < exBis.size - 1)
                    exerciseOutput.text = "${exBis[count].name}\t\tsets:${exBis[count].sets}\t\treps:${exBis[count].reps}"
            }


        }.start()
    }

    fun cicleEx(){
//        exBis[count].sets--
        if(count < exBis.size - 1) {
            if (--exBis[count].sets == 0) {
                exBis.removeAt(count)
            }
            exerciseOutput.text = "${exBis[count].name}\t\tsets:${exBis[count].sets}\treps:${exBis[count].reps}"
            st(exBis[count].rest * 1000L)
        }
    }
}
