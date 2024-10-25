package com.example.api.iservice

data class MailerSendInput(
	val to: String,
	val subject: String,
	val text: String,
	val html: String,
)

interface IMailer {
	fun send(input: MailerSendInput)
}
