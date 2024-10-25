package com.example.api.usecase

import java.util.UUID

fun generateId(): String = UUID.randomUUID().toString()

fun generateCode(): String = UUID.randomUUID().toString().substring(0, 6)
