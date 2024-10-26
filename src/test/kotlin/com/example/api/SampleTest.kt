@file:Suppress("ktlint:standard:no-wildcard-imports")

package com.example.api

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class SampleTest {
	@Test
	fun `sample test`() {
		val sum = 1 + 1
		assertEquals(2, sum)
	}
}
