package com.jasavast.kotlinspringboilerplate.core.security

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTCreationException
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.Claim
import com.auth0.jwt.interfaces.DecodedJWT
import com.jasavast.kotlinspringboilerplate.core.error.ClientStatusCode
import com.jasavast.kotlinspringboilerplate.core.error.ErrorResponseException
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
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
    lateinit var verifier: JWTVerifier;
    var tokenValidityInMilliseconds by Delegates.notNull<Long>()
    var tokenValidityRememberMe by Delegates.notNull<Long>()
    private val AUTHORITIES_KEY = "auth"
    private val ISSUER_KEY="system"

    init{
        val keyBytes:ByteArray=secretKey.toByteArray(Charset.defaultCharset())
        algorithm= Algorithm.HMAC256(keyBytes);
        verifier=JWT.require(algorithm).withIssuer(ISSUER_KEY)
            .acceptExpiresAt(5).acceptLeeway(1).build()
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
                .withIssuer(ISSUER_KEY)
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
        return try{
            val jwt:DecodedJWT=verifier.verify(token);
            jwt.expiresAt.after(Date())
        }catch (error: JWTVerificationException){
            false;
        }
    }
    fun getAuth(token:String):Authentication{
        return try{
            val jwt:DecodedJWT =verifier.verify(token);
            val claims:Map<String, Claim> =jwt.claims
            val authorities: Collection<GrantedAuthority?> =
                claims[AUTHORITIES_KEY].toString().split(",").stream()
                    .map { role:String->SimpleGrantedAuthority(role) }
                    .collect(Collectors.toList())
            var principal:User=User(jwt.subject,"",authorities)
            UsernamePasswordAuthenticationToken(principal,token,authorities);
        }catch (error:JWTVerificationException){
            throw ErrorResponseException(ClientStatusCode.INVALID_JWT_TOKEN,"Token invalid");
        }
    }
}