package com.example.coroutineflowadvance.ui

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun logWithTimestamp(): String? {
    return DateTimeFormatter
        .ofPattern("HH:mm:ss")
        .withZone(ZoneOffset.UTC)
        .format(Instant.now())
}