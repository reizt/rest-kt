package com.example.api.iusecase.user

data class UpdateUserInputData(
	val name: String,
)

data class UpdateUserInput(
	val token: String,
	val data: UpdateUserInputData,
)

interface IUpdateUser {
	fun run(input: UpdateUserInput): Unit
}
