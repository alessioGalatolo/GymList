package com.anexus.list.roomDatabase

import android.os.Handler
import android.os.HandlerThread

class DbWorkerThread(threadName: String) : HandlerThread(threadName) {

    private lateinit var mWorkerHandler: Handler

    override fun onLooperPrepared() {
        mWorkerHandler = Handler(looper)
        super.onLooperPrepared()
    }

    fun postTask(task: Runnable) {
        onLooperPrepared()
        mWorkerHandler.post(task)
    }

}