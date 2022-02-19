package com.jasavast.kotlinspringboilerplate.core.security

import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.util.StringUtils
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

class JWTFilter(val tokenProvider: TokenProvider):WebFilter {
    val AUTHORIZATION_HEADER:String="Authorization"
    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val jwt:String?=resolveToken(exchange.request)
        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt!!)){
            val auth:Authentication=tokenProvider.getAuth(jwt!!)
            return chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth))
        }
        return chain.filter(exchange)
    }
    private fun resolveToken(request: ServerHttpRequest): String? {
        val bearerToken = request.headers.getFirst(AUTHORIZATION_HEADER)
        return if (StringUtils.hasText(bearerToken) && bearerToken!!.startsWith("Bearer ")) {
            bearerToken.substring(7)
        } else null
    }
}