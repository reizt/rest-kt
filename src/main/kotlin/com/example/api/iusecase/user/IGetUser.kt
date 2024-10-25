package com.example.api.iusecase.user

import com.example.api.domain.User

data class GetUserInput(
	val token: String,
)

data class GetUserOutput(
	val user: User,
)

interface IGetUser {
	fun run(input: GetUserInput): GetUserOutput
}
