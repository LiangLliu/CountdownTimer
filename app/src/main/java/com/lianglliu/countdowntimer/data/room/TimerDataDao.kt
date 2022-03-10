package com.lianglliu.countdowntimer.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.lianglliu.countdowntimer.data.entity.TimerData

@Dao
abstract class TimerDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(timerData: TimerData): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun update(timerData: TimerData)

    @Query(
        """
        SELECT * FROM timerData WHERE name = :name
    """
    )
    abstract fun findByName(name: String): TimerData?
}