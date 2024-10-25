package com.example.api.controller

import com.example.api.domain.User
import com.example.api.iusecase.user.CreateUserInput
import com.example.api.iusecase.user.CreateUserInputData
import com.example.api.iusecase.user.GetUserInput
import com.example.api.iusecase.user.ICreateUser
import com.example.api.iusecase.user.IGetUser
import com.example.api.iusecase.user.ILogin
import com.example.api.iusecase.user.IUpdateUser
import com.example.api.iusecase.user.IVerifyUser
import com.example.api.iusecase.user.LoginInput
import com.example.api.iusecase.user.UpdateUserInput
import com.example.api.iusecase.user.UpdateUserInputData
import com.example.api.iusecase.user.VerifyUserInput
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseCookie
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

data class CreateUserRequestBody(
	val name: String,
	val email: String,
	val password: String,
)

data class CreateUserResponseBody(
	val codeId: String,
)

data class VerifyUserRequestBody(
	val codeId: String,
	val code: String,
)

data class LoginRequestBody(
	val email: String,
	val password: String,
)

data class GetUserResponseBody(
	val user: User,
)

data class UpdateUserRequestBody(
	val name: String,
)

@RestController
class UserController(
	private val createUserU: ICreateUser,
	private val verifyCodeU: IVerifyUser,
	private val loginU: ILogin,
	private val getUserU: IGetUser,
	private val updateUserU: IUpdateUser,
) {
	@PostMapping("/users")
	fun create(
		@RequestBody body: CreateUserRequestBody,
	): CreateUserResponseBody {
		val inputData =
			CreateUserInputData(
				name = body.name,
				email = body.email,
				password = body.password,
			)
		val input = CreateUserInput(data = inputData)
		val output = createUserU.run(input)
		return CreateUserResponseBody(codeId = output.codeId)
	}

	@PostMapping("/verify-code")
	fun verifyCode(
		@RequestBody body: VerifyUserRequestBody,
		resp: HttpServletResponse,
	) {
		val input = VerifyUserInput(codeId = body.codeId, code = body.code)
		val output = verifyCodeU.run(input)
		resp.addCookie(Cookie(COOKIE_NAME, output.token))
	}

	@PostMapping("/login")
	fun createAdvice(
		@RequestBody body: LoginRequestBody,
		resp: HttpServletResponse,
	) {
		val input =
			LoginInput(
				email = body.email,
				password = body.password,
			)
		val output = loginU.run(input)
		resp.addCookie(Cookie(COOKIE_NAME, output.token))
	}

	@GetMapping("/logout")
	fun logout(resp: HttpServletResponse) {
		val setCookie = ResponseCookie.from(COOKIE_NAME, "").maxAge(0).build()
		resp.addHeader("Set-Cookie", setCookie.toString())
	}

	@GetMapping("/users/current")
	fun get(
		@CookieValue(COOKIE_NAME) token: String,
	): GetUserResponseBody {
		val input = GetUserInput(token = token)
		val output = getUserU.run(input)
		return GetUserResponseBody(output.user)
	}

	@PatchMapping("/users/current")
	fun update(
		@CookieValue(COOKIE_NAME) token: String,
		@RequestBody body: UpdateUserRequestBody,
	) {
		val inputData = UpdateUserInputData(name = body.name)
		val input = UpdateUserInput(data = inputData, token = token)
		updateUserU.run(input)
	}
}
