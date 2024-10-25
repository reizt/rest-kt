package com.example.api.service

import com.example.api.iservice.IMailer
import com.example.api.iservice.MailerSendInput
import com.sendgrid.Method
import com.sendgrid.Request
import com.sendgrid.SendGrid
import com.sendgrid.helpers.mail.Mail
import com.sendgrid.helpers.mail.objects.Content
import com.sendgrid.helpers.mail.objects.Email
import org.springframework.stereotype.Component

@Component
class SendgridMailer : IMailer {
	val sendgrid: SendGrid

	init {
		val apiKey = System.getenv("SENDGRID_API_KEY")
		if (apiKey == null) {
			throw IllegalArgumentException("SENDGRID_API_KEY is not set")
		}
		sendgrid = SendGrid(apiKey)
	}

	override fun send(input: MailerSendInput) {
		val from = Email("noreply@example.com", "Example")
		val subject = input.subject
		val to = Email(input.to)
		val content = Content("text/html", input.html)
		val mail = Mail(from, subject, to, content)

		val req = Request()
		req.method = Method.POST
		req.endpoint = "mail/send"
		req.body = mail.build()
		sendgrid.api(req)
	}
}
