package com.lianglliu.countdowntimer.data.repository

import com.lianglliu.countdowntimer.data.entity.TimerData
import com.lianglliu.countdowntimer.data.room.TimerDataDao

class TimerDataRepository(
    private val timerDataDao: TimerDataDao
) {
    fun findByName(name: String): TimerData? = timerDataDao.findByName(name)

    suspend fun insert(timerData: TimerData) = timerDataDao.insert(timerData)

    suspend fun update(timerData: TimerData) = timerDataDao.update(timerData)
}