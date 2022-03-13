package com.lianglliu.countdowntimer.utils

import android.content.Context
import android.content.Intent
import android.os.Build
import com.lianglliu.countdowntimer.service.INTENT_COMMAND
import com.lianglliu.countdowntimer.service.MakeItService


fun Context.foregroundStartService(command: String) {
    val intent = Intent(this, MakeItService::class.java)
    if (command == "Start") {
        intent.putExtra(INTENT_COMMAND, command)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.startForegroundService(intent)
        } else {
            this.startService(intent)
        }
    } else if (command == "Exit") {
        intent.putExtra(INTENT_COMMAND, command)

        this.stopService(intent)
    }
}
