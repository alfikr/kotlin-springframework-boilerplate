package com.jasavast.kotlinspringboilerplate.core

import com.jasavast.kotlinspringboilerplate.controller.vm.ReqVM
import org.json.JSONObject
import org.springframework.http.server.reactive.ServerHttpRequest
import reactor.core.publisher.Mono
import javax.validation.Valid

interface CoreApi {
    fun getResponse(request: ServerHttpRequest): Mono<JSONObject>
    fun postResponse(@Valid request: ReqVM): Mono<JSONObject>
    fun deleteResponse(request: ServerHttpRequest):Mono<JSONObject>
    fun updateResponse(@Valid request: ReqVM):Mono<JSONObject>
}