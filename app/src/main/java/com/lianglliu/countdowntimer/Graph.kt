package com.lianglliu.countdowntimer

import android.content.Context
import androidx.room.Room
import com.lianglliu.countdowntimer.data.repository.TimerDataRepository
import com.lianglliu.countdowntimer.data.room.CountdownTimerDatabase

object Graph {
    lateinit var database: CountdownTimerDatabase
    lateinit var appContext: Context

    val timerDataRepository by lazy {
        TimerDataRepository(
            timerDataDao = database.timerDataDao()
        )
    }

    fun provide(context: Context) {
        appContext = context
        database = Room.databaseBuilder(context, CountdownTimerDatabase::class.java, "mcData.db")
            .fallbackToDestructiveMigration() // don't use this in production app
            .build()
    }
}