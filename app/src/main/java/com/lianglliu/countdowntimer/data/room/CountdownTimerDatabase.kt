package com.lianglliu.countdowntimer.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lianglliu.countdowntimer.data.entity.TimerData

@Database(
    entities = [TimerData::class],
    version = 1,
    exportSchema = false
)
abstract class CountdownTimerDatabase : RoomDatabase() {
    abstract fun timerDataDao(): TimerDataDao
}