package com.lianglliu.countdowntimer.utils

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class NotificationWorker(
    context: Context,
    workerParameters: WorkerParameters
) : Worker(context, workerParameters) {
    override fun doWork(): Result {
        return try {
            for (i in 0..10) {
                Log.i("NotificationWorker", "Counted $i")
            }
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}