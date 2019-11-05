package com.anexus.list.roomDatabase

import android.content.Context
import android.os.Handler
import android.os.HandlerThread

class DbWorkerThread(threadName: String) : HandlerThread(threadName) {

    private lateinit var mDatabase: ProgramDatabase

    constructor(threadName: String, context: Context) : this(threadName) {
        mDatabase = ProgramDatabase.getInstance(context)!!
    }

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