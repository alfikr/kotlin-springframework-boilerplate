package com.jasavast.kotlinspringboilerplate.core

import com.jasavast.kotlinspringboilerplate.core.error.ClientStatusCode
import com.jasavast.kotlinspringboilerplate.core.error.ErrorResponseException
import com.jasavast.kotlinspringboilerplate.service.ApiAbstract
import org.json.JSONObject
import org.springframework.context.ApplicationContext
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.full.isSuperclassOf


@RestController
@RequestMapping("/api/request")
class CoreResources(private final val context: ApplicationContext){
    @GetMapping("")
    fun getResponse(request: ServerHttpRequest): Mono<JSONObject> {
        val map =request.queryParams.toSingleValueMap();
        val req=JSONObject(map);
        if (!req.has("method")){
            throw ErrorResponseException(ClientStatusCode.METHOD_NOT_FOUND,"parameter method tidak ditemukan");
        }
        val method=req.getString("method").split("\\.");
        if (method.size!=2){
            throw ErrorResponseException(ClientStatusCode.METHOD_NOT_FOUND,"invalid value method parameter")
        }
        var bean:Object = context.getBean(method[0]) as Object;
        if(ApiAbstract::class.isSuperclassOf(bean::class)){
            (bean as ApiAbstract).init(req);
            bean::class.declaredFunctions
        }

        return Mono.empty();
    }
    @PostMapping("")
    fun postResponse(): Mono<JSONObject>{

        return Mono.empty();
    }
    @DeleteMapping("")
    fun deleteResponse():Mono<JSONObject>{
        return Mono.empty();
    }
    @PutMapping("")
    fun updateResponse():Mono<JSONObject>{
        return Mono.empty();
    }

}