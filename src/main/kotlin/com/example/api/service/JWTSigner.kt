package com.example.api.service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.api.iservice.ISigner
import org.springframework.stereotype.Component
import java.security.KeyFactory
import java.security.interfaces.ECPrivateKey
import java.security.interfaces.ECPublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.Base64
import java.util.Date

@Component
class JWTSigner : ISigner {
	private val privateKey: ECPrivateKey
	private val publicKey: ECPublicKey
	private val algorithm: Algorithm

	init {
		privateKey = loadPrivateKey(System.getenv("JWT_PRIVATE_KEY"))
		publicKey = loadPublicKey(System.getenv("JWT_PUBLIC_KEY"))
		algorithm = Algorithm.ECDSA256(publicKey, privateKey)
	}

	override fun sign(
		payload: String,
		expiresAt: Long,
	): String {
		val jwt =
			JWT
				.create()
				.withClaim("payload", payload)
				.withExpiresAt(Date(expiresAt))
				.sign(algorithm)

		return jwt
	}

	override fun verify(signature: String): String {
		val verifier = JWT.require(algorithm).build()
		val decodedJWT = verifier.verify(signature)
		return decodedJWT.getClaim("payload").asString()
	}

	private fun loadPrivateKey(key: String): ECPrivateKey {
		val clearKey =
			key
				.replace("-----BEGIN PRIVATE KEY-----", "")
				.replace("-----END PRIVATE KEY-----", "")
				.replace("\\s".toRegex(), "")
		val encoded = Base64.getDecoder().decode(clearKey)
		val keySpec = PKCS8EncodedKeySpec(encoded)
		val keyFactory = KeyFactory.getInstance("EC")
		return keyFactory.generatePrivate(keySpec) as ECPrivateKey
	}

	private fun loadPublicKey(key: String): ECPublicKey {
		val clearKey =
			key
				.replace("-----BEGIN PUBLIC KEY-----", "")
				.replace("-----END PUBLIC KEY-----", "")
				.replace("\\s".toRegex(), "")
		val encoded = Base64.getDecoder().decode(clearKey)
		val keySpec = X509EncodedKeySpec(encoded)
		val keyFactory = KeyFactory.getInstance("EC")
		return keyFactory.generatePublic(keySpec) as ECPublicKey
	}
}
