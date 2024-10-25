package com.example.api.service

import com.example.api.iservice.IMailer
import com.example.api.iservice.MailerSendInput
import org.springframework.stereotype.Component

@Component
class StubMailer : IMailer {
	override fun send(input: MailerSendInput) {
	}
}
