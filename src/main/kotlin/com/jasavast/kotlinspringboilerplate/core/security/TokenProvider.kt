package com.jasavast.kotlinspringboilerplate.core.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTCreationException
import com.jasavast.kotlinspringboilerplate.core.error.ClientStatusCode
import com.jasavast.kotlinspringboilerplate.core.error.ErrorResponseException
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.stereotype.Component
import java.nio.charset.Charset
import java.util.*
import java.util.stream.Collectors
import kotlin.properties.Delegates

@Component
class TokenProvider(
    @Value("\${application.token.secret}")
    var secretKey: String,
    @Value("\${application.token.rememberMe}")
    var rememberMeValidateToken: Long,
    @Value("\${application.token.validation}")
    val validateToken: Long
) {
    lateinit var algorithm: Algorithm;
    var tokenValidityInMilliseconds by Delegates.notNull<Long>()
    var tokenValidityRememberMe by Delegates.notNull<Long>()
    private val AUTHORITIES_KEY = "auth"

    init{
        val keyBytes:ByteArray=secretKey.toByteArray(Charset.defaultCharset())
        algorithm= Algorithm.HMAC256(keyBytes);
        tokenValidityInMilliseconds=1000*validateToken
        tokenValidityRememberMe=1000*rememberMeValidateToken
    }

    fun createToken(auth: Authentication,rememberMe:Boolean):String{
        try{
            val now = Date().time
            val validity: Date = if (rememberMe) {
                Date(now + this.tokenValidityRememberMe)
            } else {
                Date(now + this.tokenValidityInMilliseconds)
            }
            val authorities: String = auth.authorities.stream()
                .map { obj: GrantedAuthority -> obj.authority }
                .collect(Collectors.joining(","))
            val jwt:String=JWT.create()
                .withIssuer(auth.principal.toString())
                .withExpiresAt(validity)
                .withSubject(auth.principal.toString())
                .withPayload(mapOf(Pair(AUTHORITIES_KEY,authorities)))
                .sign(algorithm);
            return jwt;
        }catch (e: JWTCreationException){
            e.printStackTrace()
            throw ErrorResponseException(ClientStatusCode.CREATE_JWT_ERROR,"error create jwt token")
        }
        return "";
    }
    fun validateToken(token:String):Boolean{
        throw NotImplementedError()
    }
    fun getAuth(token:String):Authentication{
        throw NotImplementedError()
    }
}