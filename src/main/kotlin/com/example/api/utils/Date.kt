package com.example.api.utils

import java.time.Instant
import java.time.format.DateTimeFormatter

fun timeStamp(): String = DateTimeFormatter.ISO_INSTANT.format(Instant.now())
